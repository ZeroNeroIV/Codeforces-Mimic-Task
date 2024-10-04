package com.zerowhisper.submissionconsumerserver.repository;

import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Repository
@RequiredArgsConstructor
public class SubmissionRepository {
    private final JdbcTemplate jdbcTemplate;

    @Modifying
    @Transactional
    public void updateSubmissionStatus(
            @NotNull Long submissionId,
            @NotNull String submissionStatus,
            @NotNull Double timeTaken,
            @NotNull Double memoryTaken,
            @NotNull String userOutput) {
        String sql = ("UPDATE submission " +
                "SET submission_status = ? " +
                ", time_taken = ? " +
                ", memory_taken = ? " +
                ", user_output = ? " +
                ", is_finished = %s " +
                "WHERE submission_id = ?")
                .formatted(isPendingSubmission(submissionStatus)
                        ? "FALSE"
                        : "TRUE");
        jdbcTemplate.update(
                sql,
                submissionStatus,
                timeTaken,
                memoryTaken,
                userOutput,
                submissionId);
    }

    private boolean isPendingSubmission(String submissionStatus) {
        return submissionStatus.equals("...") || submissionStatus.equals("In Queue") ||
                submissionStatus.equals("Internal Error") || submissionStatus.equals("Processing");
    }

    //? Get submission by ID as JSON
    public Map<String, Object> getSubmissionById(Long submissionId) {
        String sql = "SELECT * FROM submission WHERE submission_id = ?";
        return jdbcTemplate.queryForMap(sql, submissionId);
    }

    public String getUserAccountIdBySubmissionId(Long submissionId) {
        String sql = "SELECT user_account_id FROM submission WHERE submission_id = ?";
        return jdbcTemplate.queryForObject(sql, String.class, submissionId);
    }
}
