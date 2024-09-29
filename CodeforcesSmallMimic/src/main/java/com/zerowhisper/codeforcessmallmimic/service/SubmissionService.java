<<<<<<< HEAD
package com.zerowhisper.codeforcessmallmimic.service;


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
import java.util.Map;

@Service
@RequiredArgsConstructor
public class SubmissionService {
    private final SubmissionRepository submissionRepository;
    private final UserAccountRepository userAccountRepository;
    private final ProblemRepository problemRepository;
    private final Judge0Service judge0_Service;
    private final KafkaSubmissionProducerService kafkaProducer;

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

        //? Send message to Kafka
        kafkaProducer.sendSubmission(message);

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
}
=======
package com.zerowhisper.codeforcessmallmimic.service;


import com.zerowhisper.codeforcessmallmimic.entity.Problem;
import com.zerowhisper.codeforcessmallmimic.entity.Submission;
import com.zerowhisper.codeforcessmallmimic.entity.UserAccount;
import com.zerowhisper.codeforcessmallmimic.repository.ProblemRepository;
import com.zerowhisper.codeforcessmallmimic.repository.SubmissionRepository;
import com.zerowhisper.codeforcessmallmimic.repository.UserAccountRepository;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class SubmissionService {
    private final SubmissionRepository submissionRepository;
    private final UserAccountRepository userAccountRepository;
    private final ProblemRepository problemRepository;
    private final Judge0Service judge0_Service;
    private final KafkaSubmissionProducerService kafkaProducer;

    public Map<String, String> submitCode(@NotNull @NotEmpty Long userId,
                                          @NotNull @NotEmpty Long problemId,
                                          @NotNull @NotEmpty String code,
                                          @NotNull @NotEmpty Integer languageId)
            throws IOException, InterruptedException {

        UserAccount userAccount = userAccountRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found, Bro:("));

        Problem problem = problemRepository.findById(problemId)
                .orElseThrow(() -> new RuntimeException("Problem not found, Bro :> "));

        if (languageId <= 0 || languageId > 98) {
            throw new RuntimeException("Invalid language ID!");
        }

        //? Create a new submission entity
        Submission submission = new Submission();

        submission.setUserAccount(userAccount);
        submission.setProblem(problem);
        submission.setSourceCode(code);
        submission.setSubmissionStatus("...");
        submission.setLanguageId(languageId);

        //? Save the submission entity
        Submission savedSubmission = submissionRepository.save(submission);

        String token = judge0_Service.sendCodeToJudge0(submission);

        Map<String, Object> message = new HashMap<>();
        message.put("token", token);
        message.put("submissionId", submission.getSubmissionId());

        //? Send message to Kafka
        kafkaProducer.sendSubmission(message);

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

    private void resubmitCode(Submission submission) {
        try {
            submitCode(submission.getUserAccount().getUserAccountId(),
                    submission.getProblem().getProblemId(),
                    submission.getSourceCode(),
                    submission.getLanguageId());
        } catch (Exception e) {
            throw new RuntimeException("Resubmission failed for submission " + submission.getSubmissionId());
        }
    }

    private boolean isPendingStatus(Submission submission) {
        String status = submission.getSubmissionStatus();
        return status.equals("...") || status.equals("In Queue") ||
                status.equals("Internal Error") || status.equals("Processing");
    }

    public Submission updateSubmission(Long submissionId,
                                       String statusDescription,
                                       String output,
                                       Double memory,
                                       Double executionTime) {
        Submission submission = submissionRepository.findById(submissionId)
                .orElseThrow(() -> new RuntimeException("Submission not found for id " + submissionId));
        submission.setSubmissionStatus(statusDescription);
        submission.setUserOutput(output);
        submission.setMemoryTaken(memory);
        submission.setTimeTaken(executionTime);
        submissionRepository.save(submission);
        return submission;
    }
}
>>>>>>> da0b5cedeaa004954e14e690979c717bc7891ad9
