package com.zerowhisper.codeforcessmallmimic.service;

import com.zerowhisper.codeforcessmallmimic.dto.LogInResponseDto;
import com.zerowhisper.codeforcessmallmimic.entity.RefreshToken;
import com.zerowhisper.codeforcessmallmimic.entity.UserAccount;
import com.zerowhisper.codeforcessmallmimic.repository.RefreshTokenRepository;
import com.zerowhisper.codeforcessmallmimic.repository.UserAccountRepository;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class LogInService {
    private final JwtService jwtService;
    private final UserAccountRepository userAccountService;
    private final RefreshTokenRepository refreshTokenRepository;

    public LogInResponseDto makeLogin(@NotBlank String email, @NotBlank String password) {
        // Check if email or password is null
        if (email == null) {
            throw new IllegalArgumentException("Email can't be empty!");
        } else if (password == null) {
            throw new IllegalArgumentException("Password can't be empty!");
        }

        // Get userAccount from email
        UserAccount userAccount = userAccountService.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("User not found!"));

        // Authenticate the user
        // User is not enabled
        if (!userAccount.isEnabled()) {
            throw new IllegalStateException("User is not enabled.");
        }

        RefreshToken refreshToken = new RefreshToken();

        refreshToken.setRefreshToken(jwtService.generateRefreshToken(userAccount));
        refreshToken.setExpiresAt(new Date(System.currentTimeMillis() + RefreshToken.EXPIRATION_TIME));
        refreshToken.setUserAccount(userAccount);

        userAccount.setLastLoggedInAt(LocalDateTime.now());
        refreshTokenRepository.save(refreshToken);

        String accessToken = jwtService.generateAccessToken(userAccount);

        LogInResponseDto logInResponseDto = new LogInResponseDto();
        logInResponseDto.setAccessToken(accessToken);
        logInResponseDto.setRefreshToken(refreshToken.getRefreshToken());

        return logInResponseDto;
    }
}