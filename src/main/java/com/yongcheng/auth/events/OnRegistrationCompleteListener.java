package com.yongcheng.auth.events;

import com.yongcheng.auth.models.VerificationToken;
import com.yongcheng.auth.sevices.VerificationTokenService;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class OnRegistrationCompleteListener implements ApplicationListener<OnRegistrationCompleteEvent> {

  @Autowired
  private VerificationTokenService verificationTokenService;

	@Override
	public void onApplicationEvent(OnRegistrationCompleteEvent event) {
    // 1. Generate a UUID as verification token.
    // 2. Send verification email to user's email.

    String token = UUID.randomUUID().toString();
    VerificationToken verificationToken = new VerificationToken(token, event.getUser());
    
    verificationTokenService.save(verificationToken);
	}
}