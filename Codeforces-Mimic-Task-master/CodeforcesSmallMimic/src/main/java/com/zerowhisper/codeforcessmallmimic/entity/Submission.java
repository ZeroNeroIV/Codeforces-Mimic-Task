package com.zerowhisper.codeforcessmallmimic.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Table
@Entity
@Data
@RequiredArgsConstructor
public class Submission {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "submission_id")
    private Long submissionId;

    // The Relation with userAccount table
    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "user_account_id", nullable = false)
    private UserAccount userAccount;

    // The Relation with problem Table
    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "problem_id", nullable = false)
    private Problem problem;

    @Column(name = "submitted_at", nullable = false)
    private LocalDateTime submitted_at;

    @Column(name = "is_finished", nullable = false)
    private Boolean isFinished;

    @Column(name = "source_code", nullable = false)
    private String sourceCode;

    @Column(name = "language_id", nullable = false)
    private Integer languageId;

    @Column(name = "submission_status", nullable = false)
    private String submissionStatus;

    @Column(name = "user_output")
    private String userOutput;

    @Column(name = "time_taken")
    private Double timeTaken;

    @Column(name = "memory_taken")
    private Double memoryTaken;

    @PrePersist
    protected void OnCreate() {
        submitted_at = LocalDateTime.now();
        isFinished = false;
        timeTaken = 0.0;
        memoryTaken = 0.0;
    }
}