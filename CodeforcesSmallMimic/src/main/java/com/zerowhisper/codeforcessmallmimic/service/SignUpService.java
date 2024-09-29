<<<<<<< HEAD
package com.zerowhisper.codeforcessmallmimic.service;

import com.zerowhisper.codeforcessmallmimic.dto.SignUpDto;
import com.zerowhisper.codeforcessmallmimic.entity.Roles;
import com.zerowhisper.codeforcessmallmimic.entity.UserAccount;
import com.zerowhisper.codeforcessmallmimic.repository.RolesRepository;
import com.zerowhisper.codeforcessmallmimic.repository.UserAccountRepository;
import jakarta.validation.constraints.NotNull;
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

    public void saveUser(@NotNull SignUpDto newUser) {
        UserAccount user = new UserAccount();
        user.setEmail(newUser.getEmail());
        user.setUsername(newUser.getUsername());
        user.setPassword(passwordEncoder.encode(newUser.getPassword()));
        user.setIsEnabled(false);

        if (newUser.getRoles().isEmpty()) {
            user.getRoles().add(rolesRepository.findByRole("USER").get());
        } else {
            for (String roles : newUser.getRoles()) {
                Roles userRoles = rolesRepository.findByRole(roles.toUpperCase()).
                        orElseThrow(() -> new RuntimeException("Role not found."));

                user.getRoles().add(userRoles);
            }
        }

        userAccountRepository.save(user);
        accountVerificationService.sendVerificationEmail(user);
    }

}
=======
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
>>>>>>> da0b5cedeaa004954e14e690979c717bc7891ad9
