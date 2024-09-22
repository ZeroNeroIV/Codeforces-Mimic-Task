package com.zerowhisper.codeforcessmallmimic.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.Date;

@Entity
@Data
@Table
@RequiredArgsConstructor
public class RefreshToken {
    public static final Long EXPIRATION_TIME = 1000L * 60L * 60L * 24L * 7L; // 7 days

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "refreshToken_id")
    private Long refreshTokenId;

    //The Relationship With UserAccount Table
    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "user_account_id", nullable = false)
    private UserAccount userAccount;

    @Column(name = "refresh_token")
    private String refreshToken;

    @Column(name = "expires_at")
    private Date expiresAt;

}
