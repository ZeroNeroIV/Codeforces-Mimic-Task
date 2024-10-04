package com.zerowhisper.codeforcessmallmimic.repository;

import com.zerowhisper.codeforcessmallmimic.entity.Submission;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubmissionRepository extends JpaRepository<Submission, Long> {
    @Query("SELECT s FROM Submission s WHERE s.userAccount.userAccountId = ?1")
    List<Submission> findAllByUserAccountId(@NotNull Long userId);
}
