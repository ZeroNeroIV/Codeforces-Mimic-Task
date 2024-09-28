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

    @Column(nullable = false, name = "problem_title")
    private String problemTitle;

    @Column(nullable = false, name = "problem_statement")
    private String problemStatement;

    @Column(nullable = false, name = "time_limit")
    private Integer timeLimit;

    @Column(nullable = false, name = "memory_limit")
    private Integer memoryLimit;

    @Column(nullable = false, name = "test_case_input")
    private String testCaseInput;

    @Column(nullable = false, name = "test_case_output")
    private String testCaseOutput;

}
