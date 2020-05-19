package com.yongcheng.auth.sevices;

import java.util.Calendar;

import javax.transaction.Transactional;

import com.yongcheng.auth.exceptions.TokenInvalidException;
import com.yongcheng.auth.exceptions.TokenNotFoundException;
import com.yongcheng.auth.models.User;
import com.yongcheng.auth.models.VerificationToken;
import com.yongcheng.auth.repositories.UserRepository;
import com.yongcheng.auth.repositories.VerificationTokenRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class VerificationTokenService {
  
  @Autowired
  private VerificationTokenRepository verificationTokenRepository;

  @Autowired
  private UserRepository userRepository;

  public void save(VerificationToken token) {
    verificationTokenRepository.save(token);
  }

  public void confirm(String candidateToken) {
    VerificationToken token = verificationTokenRepository.findByToken(candidateToken);

    if (!tokenExists(token)) {
      throw new TokenNotFoundException("Verification token is not valid: " + candidateToken);
    } else if (tokenExpired(token)) {
      throw new TokenInvalidException("Verification token is expired: " + candidateToken);
    }
    else {
      User user = token.getUser();

      if (tokenUsed(user)) {
        throw new TokenInvalidException("Verification token already used: " + candidateToken);
      }

      user.setEnabled(true);

      userRepository.save(user);
    }
  }

  public boolean tokenExists(VerificationToken token) {
    return token != null;
  }

  public boolean tokenExpired(VerificationToken token) {
    return token.getExpiraryDate().getTime() - Calendar.getInstance().getTime().getTime() <= 0;
  }

  public boolean tokenUsed(User user) {
    return user.getEnabled();
  }
}