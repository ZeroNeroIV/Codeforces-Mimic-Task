package com.zerowhisper.codeforcessmallmimic.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Optional;

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

    private Timestamp submitted_at;

    // The Relation with problem Table
    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "problem_id", nullable = false)
    private Problem problem;

    @PrePersist
    protected void submitted_Code() {
        submitted_at = Timestamp.valueOf(LocalDateTime.now());
    }

    @Column(nullable = false, name = "is_finished")
    private Boolean isFinished;

    @Column(nullable = false, name = "is_Submitted")
    private Boolean isSubmitted;

    @Column(nullable = false, name = "source_code")
    private String sourceCode;

    @Column(nullable = false, name = "language_id")
    private Short languageId;

    @Column(name = "user_output")
    private String userOutput;

    @Column(nullable = false, name = "submission_status")
    private Integer submissionStatus;

    @Column(name = "time_taken")
    private Integer timeTaken;

    @Column(name = "memory_taken")
    private Integer memoryTaken;

}
