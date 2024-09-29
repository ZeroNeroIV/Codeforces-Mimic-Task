<<<<<<< HEAD
package com.zerowhisper.codeforcessmallmimic.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;

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
=======
package com.zerowhisper.codeforcessmallmimic.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;

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
>>>>>>> da0b5cedeaa004954e14e690979c717bc7891ad9
