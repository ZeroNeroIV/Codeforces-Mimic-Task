package com.zerowhisper.codeforcessmallmimic.service;

import com.zerowhisper.codeforcessmallmimic.dto.ProblemDto;
import com.zerowhisper.codeforcessmallmimic.entity.Problem;
import com.zerowhisper.codeforcessmallmimic.repository.ProblemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProblemService {
    private final ProblemRepository problemRepository;

    public List<Problem> list() {
        return problemRepository.findAll();
    }

    public Problem getProblemById(Long problemId) {
        return problemRepository.findProblemByProblemId(problemId)
                .orElseThrow(() -> new IllegalArgumentException("Problem doesn't exist."));
    }

    public Problem add(ProblemDto problem) {
        if (problem.getDescription() == null ||
                problem.getTitle() == null ||
                problem.getExpectedOutput() == null ||
                problem.getTestCases() == null ||
                problem.getTimeLimit() == null ||
                problem.getMemoryLimit() == null ||
                problem.getDescription().isEmpty() ||
                problem.getTitle().isEmpty() ||
                problem.getExpectedOutput().isEmpty() ||
                problem.getTimeLimit().describeConstable().isEmpty() ||
                problem.getTestCases().isEmpty() ||
                problem.getMemoryLimit().describeConstable().isEmpty()) {
            throw new IllegalArgumentException("Some information is missing.");
        }

        Problem newProblem = new Problem();

        newProblem.setProblemTitle(problem.getTitle());
        newProblem.setProblemStatement(problem.getDescription());
        newProblem.setTestCaseOutput(problem.getExpectedOutput());
        newProblem.setTestCaseInput(problem.getTestCases());
        newProblem.setMemoryLimit(problem.getMemoryLimit());
        newProblem.setTimeLimit(problem.getTimeLimit());

        problemRepository.save(newProblem);

        return newProblem;
    }

    public void updateProblemById(Long problemId, ProblemDto problem) {
        // Find the Problem
        Optional<Problem> optionalWantedProblem = problemRepository.findProblemByProblemId(problemId);

        if (optionalWantedProblem.isEmpty()) {
            throw new IllegalArgumentException("The Problem That you want to Update is not exist");
        }

        if (problem.getTitle() == null || problem.getTitle().isEmpty())
            problem.setTitle(optionalWantedProblem.get().getProblemTitle());

        if (problem.getDescription() == null || problem.getDescription().isEmpty())
            problem.setDescription(optionalWantedProblem.get().getProblemStatement());

        if (problem.getExpectedOutput() == null || problem.getExpectedOutput().isEmpty())
            problem.setExpectedOutput(optionalWantedProblem.get().getTestCaseOutput());

        if (problem.getTestCases() == null || problem.getTestCases().isEmpty())
            problem.setTestCases(optionalWantedProblem.get().getTestCaseInput());

        if (problem.getTimeLimit() == null || problem.getTimeLimit().describeConstable().isEmpty())
            problem.setTimeLimit(optionalWantedProblem.get().getTimeLimit());

        if (problem.getMemoryLimit() == null || problem.getMemoryLimit().describeConstable().isEmpty())
            problem.setMemoryLimit(optionalWantedProblem.get().getMemoryLimit());

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

    public void deleteProblemById(Long problemId) {
        // Now Delete The Problem
        try {
            problemRepository.deleteProblemById(problemId);
        } catch (Exception e) {
            throw new IllegalArgumentException("Problem doesn't exist.");
        }
    }
}
