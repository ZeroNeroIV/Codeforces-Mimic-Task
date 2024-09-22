package com.zerowhisper.codeforcessmallmimic.service;

import com.zerowhisper.codeforcessmallmimic.entity.UserAccount;
import com.zerowhisper.codeforcessmallmimic.repository.RefreshTokenRepository;
import com.zerowhisper.codeforcessmallmimic.repository.UserAccountRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class LogOutService {

    private final JwtService jwtService;
    private final RefreshTokenRepository refreshTokenRepository;
    private final UserAccountRepository userAccountRepository;

    public void logout(String token) {

        String jwtToken = token.replace("Bearer ", "");
        Optional<UserAccount> user = userAccountRepository.findByUsername(jwtService.getUsernameFromAccessToken(token));

        if (user.isEmpty()) {
            throw new RuntimeException("Invalid Token");
        }

        if (!jwtService.isAccessTokenValid(token, user.get())) {
            throw new RuntimeException("Invalid token. Please login again.");
        }

        Long id = jwtService.getUserAccountIdFromAccessToken(jwtToken);

        if (refreshTokenRepository.findAllByUserId(id).isEmpty()) {
            throw new RuntimeException("No refresh tokens found for this user.");
        }
        SecurityContextHolder.clearContext();
        refreshTokenRepository.deleteAllTokensByUserId(id);
    }


}
