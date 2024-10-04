package com.zerowhisper.codeforcessmallmimic.dto;

import lombok.Data;

@Data
public class LogInResponseDto {
    private String accessToken;
    private String refreshToken;
}
