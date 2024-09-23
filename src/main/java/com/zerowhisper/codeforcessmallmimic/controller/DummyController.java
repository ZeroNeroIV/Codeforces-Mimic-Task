package com.zerowhisper.codeforcessmallmimic.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/dummy")
public class DummyController {

    @GetMapping("/test")
    public ResponseEntity<?> getUserInfo() {
        var s = SecurityContextHolder.getContext().getAuthentication();
        return ResponseEntity.ok("Success");
    }
}
