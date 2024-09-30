package com.zerowhisper.codeforcessmallmimic.repository;

import com.zerowhisper.codeforcessmallmimic.entity.Problem;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface ProblemRepository extends JpaRepository<Problem, Long> {
    // Update By ID
    @Transactional
    @Modifying(flushAutomatically = true, clearAutomatically = true)
    @Query("UPDATE Problem p SET" +
            " p.problemTitle = ?1," +
            " p.problemStatement = ?2," +
            " p.testCaseInput = ?3," +
            " p.testCaseOutput = ?4," +
            " p.memoryLimit = ?5," +
            " p.timeLimit = ?6" +
            " WHERE p.problemId = ?7")
    void updateProblemById(@NotBlank String title,
                           @NotBlank String description,
                           @NotBlank String testCases,
                           @NotBlank String expectedOutput,
                           @NotNull Double memoryLimit,
                           @NotNull Double timeLimit,
                           @NotNull Long problemId);

    // Delete By ID
    @Transactional
    @Modifying(flushAutomatically = true)
    @Query("DELETE FROM Problem p WHERE p.problemId = ?1")
    void deleteProblemById(@NotNull Long id);
}