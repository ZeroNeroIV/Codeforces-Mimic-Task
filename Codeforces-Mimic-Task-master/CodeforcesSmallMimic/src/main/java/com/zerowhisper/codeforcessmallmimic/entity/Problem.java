package com.zerowhisper.codeforcessmallmimic.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Entity
@Data
@Table
@RequiredArgsConstructor
public class Problem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "problem_id")
    private Long problemId;

    @ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER, optional = false, targetEntity = UserAccount.class)
    @JoinColumn(name = "creator_id", nullable = false)
    private UserAccount userAccount;

    @Column(nullable = false, name = "problem_title")
    private String problemTitle;

    @Column(nullable = false, name = "problem_statement")
    private String problemStatement;

    @Column(nullable = false, name = "time_limit")
    private Double timeLimit;

    @Column(nullable = false, name = "memory_limit")
    private Double memoryLimit;

    @Column(nullable = false, name = "test_case_input")
    private String testCaseInput;

    @Column(nullable = false, name = "test_case_output")
    private String testCaseOutput;

}