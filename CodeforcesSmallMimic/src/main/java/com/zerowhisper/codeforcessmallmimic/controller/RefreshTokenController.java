package com.zerowhisper.codeforcessmallmimic.controller;

import com.zerowhisper.codeforcessmallmimic.dto.RefreshTokenRequestDto;
import com.zerowhisper.codeforcessmallmimic.service.RefreshTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/refresh-token")
public class RefreshTokenController {
    private final RefreshTokenService refreshTokenService;

    @PostMapping
    public ResponseEntity<?> refreshToken(@RequestBody RefreshTokenRequestDto refreshTokenRequestDto) {
        return ResponseEntity.ok(refreshTokenService
                .createNewAccessTokenFromRefreshToken(refreshTokenRequestDto));
    }
}
