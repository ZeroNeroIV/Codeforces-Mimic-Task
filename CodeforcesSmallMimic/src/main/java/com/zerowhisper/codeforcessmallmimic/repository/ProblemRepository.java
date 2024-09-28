package com.zerowhisper.codeforcessmallmimic.repository;

import com.zerowhisper.codeforcessmallmimic.entity.Problem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface ProblemRepository extends JpaRepository<Problem, Long> {

    // Find By ID
    @Query("SELECT p FROM Problem p where p.problemId = ?1")
    Optional<Problem> findProblemByProblemId(Long ID);

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
    void updateProblemById(String title,
                           String description,
                           String testCases,
                           String expectedOutput,
                           Integer memoryLimit,
                           Integer timeLimit,
                           Long problemId);

    // Delete By ID
    @Transactional
    @Modifying(flushAutomatically = true)
    @Query("DELETE FROM Problem p WHERE p.problemId = ?1")
    void deleteProblemById(Long id);
}
