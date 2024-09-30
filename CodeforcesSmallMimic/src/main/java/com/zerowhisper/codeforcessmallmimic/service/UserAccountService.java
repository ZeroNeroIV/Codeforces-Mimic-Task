package com.zerowhisper.codeforcessmallmimic.service;

import com.zerowhisper.codeforcessmallmimic.entity.Submission;
import com.zerowhisper.codeforcessmallmimic.entity.UserAccount;
import com.zerowhisper.codeforcessmallmimic.repository.UserAccountRepository;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserAccountService implements UserDetailsService {
    private final UserAccountRepository userAccountRepository;

    @Override
    public UserAccount loadUserByUsername(@NotBlank String username) throws UsernameNotFoundException {
        return userAccountRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Didn't find user :("));
    }

    public List<Submission> getUserSubmissions(@NotNull Long userId) {
        return userAccountRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found, Bro :(")).getSubmissions();
    }
}