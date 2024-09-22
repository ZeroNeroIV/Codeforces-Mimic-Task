package com.zerowhisper.codeforcessmallmimic.controller;

import com.zerowhisper.codeforcessmallmimic.service.LogOutService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/auth/log-out")
public class LogOutController {
    private final LogOutService logOutService;

    @GetMapping
    public void  logOutUser(@RequestHeader("token") String token){

       logOutService.logout(token);
    }
}
