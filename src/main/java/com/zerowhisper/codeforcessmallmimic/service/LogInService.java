package com.zerowhisper.codeforcessmallmimic.service;

import com.zerowhisper.codeforcessmallmimic.entity.RefreshToken;
import com.zerowhisper.codeforcessmallmimic.entity.UserAccount;
import com.zerowhisper.codeforcessmallmimic.repository.RefreshTokenRepository;
import com.zerowhisper.codeforcessmallmimic.repository.UserAccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LogInService {
    //already constructed
    private final UserAccountRepository userAccountService;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final RefreshTokenRepository refreshTokenRepository;

    public String makeLogin(String email, String password) {
        //Check if email or password is null
        if (email == null) {
            throw new IllegalArgumentException("Email can't be empty!");
        } else if (password == null) {
            throw new IllegalArgumentException("Password can't be empty!");
        }

        // Get userAccount from email
        Optional<UserAccount> optionalUserAccount = userAccountService.findByEmail(email);

        if (optionalUserAccount.isEmpty()) {
            throw new IllegalArgumentException("User not found!");
        }

        // Extract user from optionalUserAccount
        UserAccount userAccount = optionalUserAccount.get();

        // Authenticate the user
        try {
            //Here it will compare the password matches
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(userAccount.getUsername(), password));

            SecurityContextHolder.getContext().setAuthentication(authentication);

            // User is not enabled
            if (!userAccount.isEnabled()) {
                throw new IllegalStateException("User is not enabled.");
            }

        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid credentials!");
        }

        RefreshToken refreshToken = new RefreshToken();

        refreshToken.setRefreshToken(jwtService.generateRefreshToken(userAccount));
        refreshToken.setExpiresAt(new Date(System.currentTimeMillis() + RefreshToken.EXPIRATION_TIME));
        refreshToken.setUserAccount(userAccount);

        String accessToken = jwtService.generateAccessToken(userAccount);

        userAccount.setLastLoggedInAt(LocalDateTime.now());
        refreshTokenRepository.save(refreshToken);

        return accessToken;
    }
}
