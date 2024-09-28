package com.zerowhisper.codeforcessmallmimic.repository;

import com.zerowhisper.codeforcessmallmimic.entity.Verification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface VerificationRepository extends JpaRepository<Verification, Long> {

    @Query("SELECT u FROM Verification u WHERE u.userAccount.userAccountId = ?1")
    Optional<Verification> findByUserAccountId(Long id);

    // Find verification code by username
    @Query("SELECT u FROM Verification u WHERE u.userAccount.username = ?1")
    Optional<Verification> findByUserAccountUsername(String username);

    @Transactional
    @Modifying(flushAutomatically = true)
    @Query("DELETE FROM Verification u WHERE u.userAccount.userAccountId = ?1")
    void deleteByUserAccountId(Long id);
}
