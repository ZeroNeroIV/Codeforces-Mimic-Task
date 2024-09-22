package com.zerowhisper.codeforcessmallmimic.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VerificationDto {
    private String username;
    private Long userAccountID;
    private String code;
}
