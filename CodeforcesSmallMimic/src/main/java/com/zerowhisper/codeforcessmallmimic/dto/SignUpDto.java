package com.zerowhisper.codeforcessmallmimic.dto;

import jakarta.validation.constraints.Email;
import lombok.Data;

import java.util.List;

@Data
public class SignUpDto {
    private String username;
    @Email(message = "Invalid email address")
    private String email;
    private String password;
    private List<String> roles;
}
