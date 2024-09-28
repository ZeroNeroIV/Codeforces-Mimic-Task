package com.zerowhisper.codeforcessmallmimic.controller;

import com.zerowhisper.codeforcessmallmimic.dto.LogInDto;
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
    public ResponseEntity<?> logIn(@RequestBody LogInDto log) {
        try {
            String accessToken = logInService.makeLogin(log.getEmail(), log.getPassword());
            return ResponseEntity.ok(accessToken);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}
