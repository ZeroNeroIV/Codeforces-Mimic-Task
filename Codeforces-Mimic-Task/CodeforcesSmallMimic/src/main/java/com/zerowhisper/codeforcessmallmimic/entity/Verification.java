package com.zerowhisper.codeforcessmallmimic.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@Table
@RequiredArgsConstructor
public class Verification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "verification_id")
    private Long verificationId;

    //The relation with userAccount table
    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "user_account_id", nullable = false, unique = true)
    private UserAccount userAccount;

    @Getter
    @Column(nullable = false, name = "verification_code")
    private String verificationCode;

    @Column(nullable = false, name = "expires_at")
    private LocalDateTime expiresAt;

}