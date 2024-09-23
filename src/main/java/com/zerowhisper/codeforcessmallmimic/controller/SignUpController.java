package com.zerowhisper.codeforcessmallmimic.controller;

import com.zerowhisper.codeforcessmallmimic.dto.SignUpDto;
import com.zerowhisper.codeforcessmallmimic.service.SignUpService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/auth/sign-up")
public class SignUpController {
    private final SignUpService signUpService;

    @PostMapping
    public ResponseEntity<?> addNewUser(@RequestBody SignUpDto signUpDto) {
        if ((signUpDto.getPassword() == null || signUpDto.getPassword().isEmpty()) ||
                (signUpDto.getEmail() == null || signUpDto.getEmail().isEmpty()) ||
                (signUpDto.getUsername() == null) || signUpDto.getUsername().isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        try {
            signUpService.saveUser(signUpDto);
        } catch (Exception e) {
            return ResponseEntity.ok(e.getMessage());
        }

        return ResponseEntity.ok("Account Created Successfully");
    }
}
