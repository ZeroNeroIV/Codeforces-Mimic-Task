package com.zerowhisper.codeforcessmallmimic.service;

import com.zerowhisper.codeforcessmallmimic.dto.ProblemDto;
import com.zerowhisper.codeforcessmallmimic.entity.Problem;
import com.zerowhisper.codeforcessmallmimic.repository.ProblemRepository;
import com.zerowhisper.codeforcessmallmimic.repository.UserAccountRepository;
import io.micrometer.common.util.StringUtils;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProblemService {
    private final JwtService jwtService;
    private final ProblemRepository problemRepository;
    private final UserAccountRepository userAccountRepository;

    public List<Problem> list() {
        return problemRepository.findAll();
    }

    public Problem getProblemById(@NotNull Long problemId) {
        return problemRepository.findById(problemId)
                .orElseThrow(() -> new IllegalArgumentException("Problem doesn't exist."));
    }

    public Problem add(@NotNull ProblemDto problem, @NotNull String token) {
        String accessToken = token.replace("Bearer ", "");
        Long creatorId = jwtService.getUserAccountIdFromAccessToken(accessToken);

        Problem newProblem = new Problem();

        newProblem.setUserAccount(userAccountRepository.findById(creatorId)
                .orElseThrow(() -> new IllegalArgumentException("User doesn't exist")));
        newProblem.setProblemTitle(problem.getTitle());
        newProblem.setProblemStatement(problem.getDescription());
        newProblem.setTestCaseOutput(problem.getExpectedOutput());
        newProblem.setTestCaseInput(problem.getTestCases());
        newProblem.setMemoryLimit(problem.getMemoryLimit());
        newProblem.setTimeLimit(problem.getTimeLimit());

        problemRepository.save(newProblem);

        return newProblem;
    }

    public void updateProblemById(@NotNull Long problemId, @NotNull ProblemDto problem) {
        // Find the Problem
        Problem wantedProblem = problemRepository.findById(problemId)
                .orElseThrow(() -> new RuntimeException("The Problem that you want to update does not exist"));

        if (StringUtils.isBlank(problem.getTitle()))
            problem.setTitle(wantedProblem.getProblemTitle());

        if (StringUtils.isBlank(problem.getDescription()))
            problem.setDescription(wantedProblem.getProblemStatement());

        if (StringUtils.isBlank(problem.getExpectedOutput()))
            problem.setExpectedOutput(wantedProblem.getTestCaseOutput());

        if (StringUtils.isBlank(problem.getTestCases()))
            problem.setTestCases(wantedProblem.getTestCaseInput());

        if (problem.getTimeLimit() == null || problem.getTimeLimit() < 0.1)
            problem.setTimeLimit(wantedProblem.getTimeLimit());

        if (problem.getMemoryLimit() == null || problem.getMemoryLimit().describeConstable().isEmpty())
            problem.setMemoryLimit(wantedProblem.getMemoryLimit());

        // Update The Problem
        problemRepository.updateProblemById(
                problem.getTitle(),
                problem.getDescription(),
                problem.getTestCases(),
                problem.getExpectedOutput(),
                problem.getMemoryLimit(),
                problem.getTimeLimit(),
                problemId
        );
    }

    public void deleteProblemById(@NotNull Long problemId) {
        // Now Delete The Problem
        try {
            problemRepository.deleteProblemById(problemId);
        } catch (Exception e) {
            throw new IllegalArgumentException("Problem doesn't exist.");
        }
    }
}