package com.yongcheng.auth.repositories;

import java.util.Date;

import com.yongcheng.auth.models.VerificationToken;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface VerificationTokenRepository extends JpaRepository<VerificationToken, Long> {
  
  VerificationToken findByToken(String token);

  @Modifying
  @Query("delete from VerificationToken t where t.expiraryDate <= ?1")
  void deleteAllExpiredSince(Date now);
  
}