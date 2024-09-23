package com.zerowhisper.codeforcessmallmimic.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "user_account")
@RequiredArgsConstructor
public class UserAccount implements UserDetails {

    @Getter
    @Column(nullable = false, name = "created_at")
    private final LocalDateTime createdAt = LocalDateTime.now();
    @Id
    @Getter
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_account_id")
    private Long userAccountId;
    @Setter
    @Getter
    @Column(nullable = false, unique = true)
    private String email;
    @Setter
    @Column(nullable = false, unique = true)
    private String username;
    @Setter
    @Column(nullable = false)
    private String password;
    @Setter
    @Getter
    @Column(nullable = false, name = "is_enabled")
    private Boolean isEnabled;
    @Setter
    @Getter
    @Column(name = "last_logged_in_at")
    private LocalDateTime lastLoggedInAt;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return UserDetails.super.isAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return UserDetails.super.isAccountNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return UserDetails.super.isCredentialsNonExpired();
    }

    @Override
    public boolean isEnabled() {
        return UserDetails.super.isEnabled();
    }
}
