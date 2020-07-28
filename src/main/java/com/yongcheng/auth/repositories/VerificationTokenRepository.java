package com.yongcheng.auth.repositories;

import com.yongcheng.auth.models.VerificationToken;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VerificationTokenRepository extends JpaRepository<VerificationToken, Long> {
  
  VerificationToken findByToken(String token);
  
}