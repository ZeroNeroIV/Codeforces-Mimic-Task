package com.zerowhisper.codeforcessmallmimic.service;

import com.zerowhisper.codeforcessmallmimic.dto.VerificationDto;
import com.zerowhisper.codeforcessmallmimic.entity.UserAccount;
import com.zerowhisper.codeforcessmallmimic.entity.Verification;
import com.zerowhisper.codeforcessmallmimic.repository.UserAccountRepository;
import com.zerowhisper.codeforcessmallmimic.repository.VerificationRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Objects;
import java.util.Optional;
import java.util.Random;

@Service
@AllArgsConstructor
public class AccountVerificationService {

    private final VerificationRepository verificationRepository;
    private final EmailService emailService;
    private final UserAccountRepository userAccountRepository;

    public void sendVerificationEmail(UserAccount user) {
        String verificationCode = generateVerificationCode();

        Verification newVerification = new Verification();

        Optional<UserAccount> optionalUserAccount =
                userAccountRepository.findByUsername(user.getUsername());

        if (optionalUserAccount.isEmpty()) {
            throw new IllegalArgumentException("No user such found!");
        }

        UserAccount userAccount = optionalUserAccount.get();

        newVerification.setUserAccount(userAccount);
        newVerification.setVerificationCode(verificationCode);

        // 10 minutes
        long expirationTime = 10L * 60L * 1000L;

        newVerification.setExpiresAt(new Date(System.currentTimeMillis() + expirationTime));

        verificationRepository.save(newVerification);

        emailService.verificationEmailSender(
                user.getEmail()
                , "Verification Code"
                , verificationCode);
    }

    public Boolean isCodeCorrect(VerificationDto verificationDto) {
        try {
            Optional<Verification> userVerificationCode =
                    verificationRepository
                            .findByUserAccountUsername(verificationDto.getUsername());
            if (userVerificationCode.isPresent() &&
                    Objects.equals(
                            userVerificationCode
                                    .get()
                                    .getVerificationCode(),
                            verificationDto.getCode())
            ) {
                verificationDto
                        .setUserAccountID(userVerificationCode
                                .get()
                                .getUserAccount()
                                .getUserAccountId());
                userAccountRepository
                        .updateIsEnabled(true,
                                verificationDto.getUserAccountID());
                verificationRepository
                        .deleteByUserAccountId(verificationDto.getUserAccountID());
                return true;
            }
        } catch (Exception e) {
            return false;
        }
        return false;
    }

    private String generateVerificationCode() {
        Random rand = new Random();
        Integer randomInteger = rand.nextInt(90000000) + 10000000;
        return String.valueOf(randomInteger);
    }
}
