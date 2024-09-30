package com.zerowhisper.codeforcessmallmimic.service;

import com.zerowhisper.codeforcessmallmimic.dto.RefreshTokenRequestDto;
import com.zerowhisper.codeforcessmallmimic.dto.RefreshTokenResponseDto;
import com.zerowhisper.codeforcessmallmimic.repository.RefreshTokenRepository;
import com.zerowhisper.codeforcessmallmimic.repository.UserAccountRepository;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {
    private final JwtService jwtService;
    private final UserAccountRepository userAccountRepository;
    private final RefreshTokenRepository refreshTokenRepository;

    public RefreshTokenResponseDto createNewAccessTokenFromRefreshToken(@NotNull RefreshTokenRequestDto refreshTokenRequestDto) {
        String refreshToken = refreshTokenRequestDto.getRefreshToken();
        Long userAccountId = jwtService.getUserAccountIdFromRefreshToken(refreshToken);

        if (jwtService.isRefreshTokenExpired(refreshToken)) {
            refreshTokenRepository.deleteAllRefreshTokensByUserAccountId(userAccountId);
            throw new IllegalArgumentException("Refresh token is expired");
        }

        String newAccessToken = jwtService
                .generateAccessToken(userAccountRepository
                        .findById(userAccountId)
                        .orElseThrow(() -> new IllegalArgumentException("User not found")));

        RefreshTokenResponseDto refreshTokenResponseDto = new RefreshTokenResponseDto();
        refreshTokenResponseDto.setNewAccessToken(newAccessToken);

        return refreshTokenResponseDto;
    }

}