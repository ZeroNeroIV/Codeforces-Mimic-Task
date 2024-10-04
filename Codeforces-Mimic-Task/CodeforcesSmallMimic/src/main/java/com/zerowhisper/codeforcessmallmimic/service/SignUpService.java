package com.zerowhisper.codeforcessmallmimic.service;

import com.zerowhisper.codeforcessmallmimic.dto.SignUpDto;
import com.zerowhisper.codeforcessmallmimic.entity.Roles;
import com.zerowhisper.codeforcessmallmimic.entity.UserAccount;
import com.zerowhisper.codeforcessmallmimic.repository.RolesRepository;
import com.zerowhisper.codeforcessmallmimic.repository.UserAccountRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class SignUpService {
    private final PasswordEncoder passwordEncoder;
    private final RolesRepository rolesRepository;
    private final UserAccountRepository userAccountRepository;
    private final AccountVerificationService accountVerificationService;

    public void saveUser(SignUpDto newUser) {
        UserAccount user = new UserAccount();
        user.setEmail(newUser.getEmail());
        user.setUsername(newUser.getUsername());
        user.setPassword(passwordEncoder.encode(newUser.getPassword()));
        user.setIsEnabled(false);

        if (newUser.getRoles().isEmpty()) {
            user.getRoles().add(rolesRepository.findByRoleName("USER")
                    .orElseThrow(() -> new RuntimeException("Role not found.")));
        } else {
            for (String roles : newUser.getRoles()) {
                Roles userRoles = rolesRepository.findByRoleName(roles.toUpperCase()).
                        orElseThrow(() -> new RuntimeException("Role not found."));

                user.getRoles().add(userRoles);
            }
        }

        userAccountRepository.save(user);
        accountVerificationService.sendVerificationEmail(user);
    }

}