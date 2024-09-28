package com.zerowhisper.codeforcessmallmimic.dto;

import lombok.Data;

@Data
public class ProblemDto {
    // DONE

    private String title;
    private String description;
    private String testCases;
    private String expectedOutput;
    private Integer memoryLimit;
    private Integer timeLimit;
}
