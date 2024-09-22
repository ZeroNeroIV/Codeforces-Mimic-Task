package com.zerowhisper.codeforcessmallmimic.repository;

import com.zerowhisper.codeforcessmallmimic.entity.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface UserAccountRepository extends JpaRepository<UserAccount, Long> {
    // ?1 = first parameter

    //optional --> use to handle potential absent values
    // Avoid NullPointerExceptions

    @Query("SELECT u FROM UserAccount u WHERE u.username = ?1")
    Optional<UserAccount> findByUsername(String username);

    @Query("SELECT u FROM UserAccount u WHERE u.email = ?1")
    Optional<UserAccount> findByEmail(String email);

    // Update isEnabled
    @Modifying
    @Transactional
    @Query("UPDATE UserAccount u SET u.isEnabled = ?1 WHERE u.userAccountId = ?2")
    void updateIsEnabled(Boolean isEnabled, Long userId);

}
