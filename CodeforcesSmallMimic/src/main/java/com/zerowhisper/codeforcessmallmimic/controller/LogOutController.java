package com.zerowhisper.codeforcessmallmimic.controller;

import com.zerowhisper.codeforcessmallmimic.service.LogOutService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/log-out")
public class LogOutController {
    private final LogOutService logOutService;

    @GetMapping
    public ResponseEntity<String> logOutUser(@RequestHeader("Authorization") String token) {
        logOutService.logOut(token);
        return ResponseEntity.ok("Logged out successfully.");
    }
}
