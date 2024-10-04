package com.zerowhisper.codeforcessmallmimic.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zerowhisper.codeforcessmallmimic.dto.SubmissionDto;
import com.zerowhisper.codeforcessmallmimic.entity.Problem;
import com.zerowhisper.codeforcessmallmimic.entity.Submission;
import com.zerowhisper.codeforcessmallmimic.entity.UserAccount;
import com.zerowhisper.codeforcessmallmimic.repository.ProblemRepository;
import com.zerowhisper.codeforcessmallmimic.repository.SubmissionRepository;
import com.zerowhisper.codeforcessmallmimic.repository.UserAccountRepository;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class SubmissionService {
    private final SubmissionRepository submissionRepository;
    private final UserAccountRepository userAccountRepository;
    private final ProblemRepository problemRepository;
    private final Judge0Service judge0_Service;
    private final KafkaSubmissionProducerService kafkaProducer;
    private final ObjectMapper objectMapper;

    public Map<String, String> submitCode(@NotNull SubmissionDto submissionDto)
            throws IOException, InterruptedException {

        UserAccount userAccount = userAccountRepository.findById(submissionDto.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found, Bro:("));

        Problem problem = problemRepository.findById(submissionDto.getProblemId())
                .orElseThrow(() -> new RuntimeException("Problem not found, Bro :> "));

        if (submissionDto.getLanguageId() <= 0 || submissionDto.getLanguageId() > 98) {
            throw new RuntimeException("Invalid language ID!");
        }

        //? Create a new submission entity
        Submission submission = new Submission();

        submission.setUserAccount(userAccount);
        submission.setProblem(problem);
        submission.setSourceCode(submissionDto.getCode());
        submission.setSubmissionStatus("...");
        submission.setLanguageId(submissionDto.getLanguageId());

        //? Save the submission entity
        Submission savedSubmission = submissionRepository.save(submission);

        String token = judge0_Service.sendCodeToJudge0(submission);

        Map<String, Object> message = new HashMap<>();
        message.put("token", token);
        message.put("submissionId", submission.getSubmissionId());

        String messageString = objectMapper.writeValueAsString(message);

        //? Send message to Kafka
        kafkaProducer.sendSubmission(messageString);

        return Map.of("Submission ID", savedSubmission.getSubmissionId().toString(),
                "Token", token,
                "Description", "Use your submission ID to check your submission on the following route: /submission/get-submission/{submissionId}");
    }

    public Map<String, Object> getSubmission(@NotNull Long submissionId) {
        Submission submission = submissionRepository.findById(submissionId)
                .orElseThrow(() ->
                        new RuntimeException("Submission with id " + submissionId + " does not exist."));

        if (isPendingStatus(submission)) {
            resubmitCode(submission);
        }

        return Map.of("Submission ID", submission.getSubmissionId(),
                "Source Code", submission.getSourceCode(),
                "Time Taken", submission.getTimeTaken(),
                "Memory Taken", submission.getMemoryTaken(),
                "Status", submission.getSubmissionStatus());
    }

    private void resubmitCode(@NotNull Submission submission) {
        try {
            SubmissionDto submissionDto = new SubmissionDto();
            submissionDto.setUserId(submission.getUserAccount().getUserAccountId());
            submissionDto.setProblemId(submission.getProblem().getProblemId());
            submissionDto.setCode(submission.getSourceCode());
            submissionDto.setLanguageId(submission.getLanguageId());

            submitCode(submissionDto);
        } catch (Exception e) {
            throw new RuntimeException("Resubmission failed for submission " + submission.getSubmissionId());
        }
    }

    private boolean isPendingStatus(@NotNull Submission submission) {
        String status = submission.getSubmissionStatus();
        return status.equals("...") || status.equals("In Queue") ||
                status.equals("Internal Error") || status.equals("Processing");
    }

    public List<Submission> getAllSubmission() {
        return submissionRepository.findAll();
    }
}