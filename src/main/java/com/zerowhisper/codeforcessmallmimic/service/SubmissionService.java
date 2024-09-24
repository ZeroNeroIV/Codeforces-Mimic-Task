package com.zerowhisper.codeforcessmallmimic.service;

import lombok.AllArgsConstructor;

import java.io.IOException;
import java.util.Optional;

import javax.management.RuntimeErrorException;

import org.springframework.stereotype.Service;

import com.zerowhisper.codeforcessmallmimic.entity.Problem;
import com.zerowhisper.codeforcessmallmimic.entity.Submission;
import com.zerowhisper.codeforcessmallmimic.entity.UserAccount;
import com.zerowhisper.codeforcessmallmimic.repository.ProblemRepository;
import com.zerowhisper.codeforcessmallmimic.repository.SubmissionRepository;
import com.zerowhisper.codeforcessmallmimic.repository.UserAccountRepository;

@Service
@AllArgsConstructor
public class SubmissionService {

    private final SubmissionRepository submissionRepository;
    private final UserAccountRepository userAccountRepository;
    private final ProblemRepository problemRepository;
    private final Judge0_Service judge0_Service;

    public String submitCode(Long userId, Long problemId, String code, short languageId)
            throws IOException, InterruptedException {

        Submission submission = new Submission();

        submission.setSubmissionStatus(0);
        UserAccount userAccount = userAccountRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User Not found Bro :) "));

        submission.setUserAccount(userAccount);

        Problem problem = problemRepository.findById(problemId)
                .orElseThrow(() -> new RuntimeException("Problem Not found Bro :> "));

        submission.setProblem(problem);
        if (code == null || code.isEmpty()) {
            throw new RuntimeException("Source code can't be empty!");
        }
        submission.setSourceCode(code); // check if errors
        // language ID
        if (languageId <= 0 || languageId > 98) {
            throw new RuntimeException("Invalid language ID!");
        }
        submission.setLanguageId(languageId);
        submission.setIsFinished(false); // Not finished yet
        submission.setIsSubmitted(true);

        submissionRepository.save(submission);

        return judge0_Service.sendCodeToJudge0(submission);

    }

}
