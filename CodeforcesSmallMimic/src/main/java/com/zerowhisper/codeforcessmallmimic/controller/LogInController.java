package com.zerowhisper.codeforcessmallmimic.controller;

import com.zerowhisper.codeforcessmallmimic.dto.LogInRequestDto;
import com.zerowhisper.codeforcessmallmimic.service.LogInService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/log-in")
public class LogInController {
    private final LogInService logInService;

    @PostMapping
    public ResponseEntity<?> logIn(@RequestBody LogInRequestDto log) {
        return ResponseEntity.ok(logInService
                .makeLogin(log.getEmail(), log.getPassword()));
    }

}
