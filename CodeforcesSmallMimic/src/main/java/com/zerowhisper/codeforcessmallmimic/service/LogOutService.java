package com.zerowhisper.codeforcessmallmimic.service;

import com.zerowhisper.codeforcessmallmimic.entity.UserAccount;
import com.zerowhisper.codeforcessmallmimic.repository.RefreshTokenRepository;
import com.zerowhisper.codeforcessmallmimic.repository.UserAccountRepository;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class LogOutService {
    private final JwtService jwtService;
    private final UserAccountRepository userAccountRepository;
    private final RefreshTokenRepository refreshTokenRepository;

    public void logOut(@NotBlank String token) {
        String accessToken = token.replace("Bearer ", "");

        Long userId = jwtService.getUserAccountIdFromAccessToken(accessToken);

        Optional<UserAccount> user = userAccountRepository.findById(userId);

        if (user.isEmpty()) {
            throw new RuntimeException("Invalid Token.\nNo such user.");
        }

        UserAccount userAccount = user.get();

        if (!jwtService.isAccessTokenValid(accessToken, userAccount)) {
            throw new RuntimeException("Invalid token.\nToken is not valid.");
        }

        if (refreshTokenRepository.findAllByUserId(userId).isEmpty()) {
            throw new RuntimeException("No refresh tokens found for this user.");
        }

        SecurityContextHolder.clearContext();
        refreshTokenRepository.deleteAllRefreshTokensByUserAccountId(userId);
    }


}