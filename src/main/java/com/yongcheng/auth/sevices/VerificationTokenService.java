package com.yongcheng.auth.sevices;

import javax.transaction.Transactional;

import com.yongcheng.auth.models.VerificationToken;
import com.yongcheng.auth.repositories.VerificationTokenRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class VerificationTokenService {
  
  @Autowired
  private VerificationTokenRepository verificationTokenRepository;

  public void save(VerificationToken verificationToken) {
    verificationTokenRepository.save(verificationToken);
  }
}