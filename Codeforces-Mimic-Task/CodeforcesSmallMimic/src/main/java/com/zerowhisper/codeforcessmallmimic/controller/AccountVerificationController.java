package com.zerowhisper.codeforcessmallmimic.controller;

import com.zerowhisper.codeforcessmallmimic.dto.VerificationDto;
import com.zerowhisper.codeforcessmallmimic.service.AccountVerificationService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/account-verification")
public class AccountVerificationController {
    private final AccountVerificationService accountVerificationService;

    @PostMapping
    public ResponseEntity<String> verify(@RequestBody VerificationDto verificationDto) {
        Boolean isCodeValid = accountVerificationService.isCodeCorrect(verificationDto);
        if (isCodeValid) {
            return ResponseEntity.ok().body("Account verified successfully");
        } else {
            return ResponseEntity.badRequest().body("Wrong code Or username");
        }
    }
}
