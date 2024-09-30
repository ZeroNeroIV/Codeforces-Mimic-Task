package com.zerowhisper.codeforcessmallmimic.dto;

import lombok.Data;

@Data
public class SubmissionDto {
    private Long userId;
    private Long problemId;
    private String code;
    private Integer languageId; // --> convert it to based on the id ?
}
