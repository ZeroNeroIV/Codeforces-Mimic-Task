package com.zerowhisper.codeforcessmallmimic.service;

import com.zerowhisper.codeforcessmallmimic.dto.VerificationDto;
import com.zerowhisper.codeforcessmallmimic.entity.UserAccount;
import com.zerowhisper.codeforcessmallmimic.entity.Verification;
import com.zerowhisper.codeforcessmallmimic.repository.UserAccountRepository;
import com.zerowhisper.codeforcessmallmimic.repository.VerificationRepository;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Random;

@Service
@AllArgsConstructor
public class AccountVerificationService {

    private final VerificationRepository verificationRepository;
    private final EmailService emailService;
    private final UserAccountRepository userAccountRepository;

    public void sendVerificationEmail(@NotNull UserAccount user) {
        String verificationCode = generateVerificationCode();

        Verification newVerification = new Verification();

        UserAccount userAccount =
                userAccountRepository.findByUsername(user.getUsername())
                        .orElseThrow(() -> new IllegalArgumentException("No user such found!"));

        newVerification.setUserAccount(userAccount);
        newVerification.setVerificationCode(verificationCode);

        // 10 minutes
        newVerification.setExpiresAt(LocalDateTime.now().plusMinutes(10));

        verificationRepository.save(newVerification);

        emailService.verificationEmailSender(
                user.getEmail(),
                "Verification Code",
                verificationCode);
    }

    public Boolean isCodeCorrect(@NotNull VerificationDto verificationDto) {
        Verification userVerificationCode = verificationRepository
                .findByUserAccountUsername(verificationDto.getUsername())
                .orElseThrow(() -> new RuntimeException("No user found!"));
        if (Objects.equals(
                userVerificationCode.getVerificationCode(),
                verificationDto.getCode()) &&
                userVerificationCode.getExpiresAt().isAfter(LocalDateTime.now())
        ) {
            userAccountRepository.updateIsEnabled(true,
                    userVerificationCode.getUserAccount().getUserAccountId());
            verificationRepository.deleteByUserAccountId
                    (userVerificationCode.getUserAccount().getUserAccountId());
            return true;
        }
        else if (userVerificationCode.getExpiresAt().isBefore(LocalDateTime.now())) {
            verificationRepository.delete(userVerificationCode);
        }
        return false;
    }

    private String generateVerificationCode() {
        Random rand = new Random();
        Integer randomInteger = rand.nextInt(90000000) + 10000000;
        return String.valueOf(randomInteger);
    }
}