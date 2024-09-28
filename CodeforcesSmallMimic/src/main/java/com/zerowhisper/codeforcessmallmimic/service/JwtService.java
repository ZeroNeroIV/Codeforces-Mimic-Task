package com.zerowhisper.codeforcessmallmimic.service;

import com.zerowhisper.codeforcessmallmimic.entity.UserAccount;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;

@Service
public class JwtService {

    @Value("${security.jwt.access-token.secret-key}")
    private String accessTokenSecretKey;

    @Value("${security.jwt.refresh-token.secret-key}")
    private String refreshTokenSecretKey;

    @Value("${security.jwt.access-token.expiration-time}")
    private Long accessTokenExpiresAfter;

    @Value("${security.jwt.refresh-token.expiration-time}")
    private Long refreshTokenExpiresAfter;

    /// ======================================================================================
    //! Secret Signing Keys
    // PRIVATE

    //# Access Token
    private SecretKey accessTokenSecretSigningKey() {
        return secretSigningKey(accessTokenSecretKey);
    }

    private SecretKey refreshTokenSecretSigningKey() {
        return secretSigningKey(refreshTokenSecretKey);
    }


    private SecretKey secretSigningKey(String secretKey) {
        return Keys.hmacShaKeyFor(secretKey.getBytes());
    }

    /// /// ======================================================================================
    //! Generate Tokens
    // PUBLIC

    //# Access Token
    public String generateAccessToken(UserAccount userAccount) {
        return generateToken(userAccount, "access");
    }

    //# Refresh Token

    public String generateRefreshToken(UserAccount userAccount) {
        return generateToken(userAccount, "refresh");
    }

    // PRIVATE

    private String generateToken(UserAccount userAccount, String type) {
        return Jwts.builder()
                .signWith(type.equals("access")
                        ? accessTokenSecretSigningKey()
                        : refreshTokenSecretSigningKey())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + (type.equals("access")
                        ? accessTokenExpiresAfter
                        : refreshTokenExpiresAfter)))
                .subject(userAccount.getUsername())
                .id(userAccount.getUserAccountId().toString())
                .compact();
    }

    /// ======================================================================================
    //! Extract Claims
    // PUBLIC

    //# Access Token
    public Date getIssuedAtFromAccessToken(String token) {
        return extractIssuedAt(token, "access");
    }

    public Date getExpirationFromAccessToken(String token) {
        return extractExpiration(token, "access");
    }

    public Long getUserAccountIdFromAccessToken(String token) {
        return extractUserAccountId(token, "access");
    }

    public String getUsernameFromAccessToken(String token) {
        return extractUsername(token, "access");
    }

    //# Refresh Token

    public Date getIssuedAtFromRefreshToken(String token) {
        return extractIssuedAt(token, "refresh");
    }

    public Date getExpirationFromRefreshToken(String token) {
        return extractExpiration(token, "refresh");
    }

    public Long getUserAccountIdFromRefreshToken(String token) {
        return extractUserAccountId(token, "refresh");
    }

    public String getUsernameFromRefreshToken(String token) {
        return extractUsername(token, "refresh");
    }

    // PRIVATE

    private Date extractIssuedAt(String token, String type) {
        return extractClaims(token, type).getIssuedAt();
    }

    private Date extractExpiration(String token, String type) {
        return extractClaims(token, type).getExpiration();
    }

    private Long extractUserAccountId(String token, String type) {
        return Long.valueOf(extractClaims(token, type).getId());
    }

    private String extractUsername(String token, String type) {
        return extractClaims(token, type).getSubject();
    }

    private Claims extractClaims(String token, String type) {
        return Jwts.parser()
                .verifyWith(type.equals("access")
                        ? accessTokenSecretSigningKey()
                        : refreshTokenSecretSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    /// ======================================================================================
    //! Validate Token
    // PUBLIC

    //# Access Token
    public Boolean isAccessTokenValid(String token, UserAccount userAccount) {
        return isTokenValid(token, userAccount, "access");
    }

    //# Refresh Token

    public Boolean isRefreshTokenValid(String token, UserAccount userAccount) {
        return isTokenValid(token, userAccount, "refresh");
    }

    // PRIVATE

    private Boolean isTokenValid(String token, UserAccount userAccount, String type) {
        String username = extractUsername(token, type);
        return !isTokenExpired(token, type) && username.equals(userAccount.getUsername());
    }

    /// ======================================================================================
    //! Check Token Expiration
    // PUBLIC

    //# Access Token
    public Boolean isAccessTokenExpired(String token) {
        return isTokenExpired(token, "access");
    }

    //# Refresh Token

    public Boolean isRefreshTokenExpired(String token) {
        return isTokenExpired(token, "refresh");
    }

    // PRIVATE

    private Boolean isTokenExpired(String token, String type) {
        return extractExpiration(token, type).before(new Date());
    }
}
