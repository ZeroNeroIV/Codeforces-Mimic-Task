package com.zerowhisper.codeforcessmallmimic.repository;

import com.zerowhisper.codeforcessmallmimic.entity.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {

    @Query("SELECT r FROM RefreshToken r WHERE r.userAccount.userAccountId = ?1")
    List<RefreshToken> findAllByUserId(Long userId);

    @Transactional
    @Modifying(flushAutomatically = true)
    @Query("DELETE FROM RefreshToken r WHERE r.userAccount.userAccountId = ?1")
    void deleteRefreshTokenByRefreshTokenId(Long tokenId);

    @Transactional
    @Modifying(flushAutomatically = true)
    @Query("DELETE FROM RefreshToken r WHERE r.refreshTokenId = ?1")
    void deleteAllRefreshTokensByUserAccountId(Long id);

}