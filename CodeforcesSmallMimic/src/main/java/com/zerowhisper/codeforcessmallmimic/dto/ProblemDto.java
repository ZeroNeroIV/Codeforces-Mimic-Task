package com.zerowhisper.codeforcessmallmimic.dto;

import com.zerowhisper.codeforcessmallmimic.entity.UserAccount;
import lombok.Data;

@Data
public class ProblemDto {
    // DONE
    private String title;
    private String description;
    private String testCases;
    private String expectedOutput;
    private Double memoryLimit;
    private Double timeLimit;
}
