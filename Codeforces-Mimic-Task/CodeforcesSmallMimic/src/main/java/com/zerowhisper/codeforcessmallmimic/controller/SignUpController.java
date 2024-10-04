package com.zerowhisper.codeforcessmallmimic.controller;

import com.zerowhisper.codeforcessmallmimic.dto.SignUpDto;
import com.zerowhisper.codeforcessmallmimic.service.SignUpService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/sign-up")
public class SignUpController {
    private final SignUpService signUpService;

    @PostMapping
    public ResponseEntity<?> addNewUser(@RequestBody SignUpDto signUpDto) {
        signUpService.saveUser(signUpDto);
        return new ResponseEntity<>("Account created successfully.", HttpStatus.CREATED);
    }
}