package com.zerowhisper.codeforcessmallmimic.service;

import com.zerowhisper.codeforcessmallmimic.dto.SignUpDto;
import com.zerowhisper.codeforcessmallmimic.entity.UserAccount;
import com.zerowhisper.codeforcessmallmimic.repository.UserAccountRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class SignUpService {
    private final UserAccountRepository userAccountRepository;
    private final PasswordEncoder passwordEncoder;
    private final AccountVerificationService accountVerificationService;

    public void saveUser(SignUpDto newUser) {
        if ((newUser.getPassword() == null || newUser.getPassword().isEmpty()) ||
                (newUser.getEmail() == null || newUser.getEmail().isEmpty()) ||
                (newUser.getUsername() == null) || newUser.getUsername().isEmpty()
        ) {
            throw new RuntimeException("Invalid input.");
        }
        UserAccount user = new UserAccount();
        user.setEmail(newUser.getEmail());
        user.setUsername(newUser.getUsername());
        user.setPassword(passwordEncoder.encode(newUser.getPassword()));
        user.setIsEnabled(false);

        userAccountRepository.save(user);
        accountVerificationService.sendVerificationEmail(user);
    }

}
