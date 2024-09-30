package com.zerowhisper.codeforcessmallmimic.dto;

import jakarta.validation.constraints.Email;
import lombok.Data;

@Data
public class LogInRequestDto {
    @Email(message = "Invalid email address")
    private String email;
    private String password;
}
