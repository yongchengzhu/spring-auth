package com.yongcheng.auth.events;

import com.yongcheng.auth.models.User;
import com.yongcheng.auth.models.VerificationToken;
import com.yongcheng.auth.sevices.VerificationTokenService;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
public class OnRegistrationCompleteListener implements ApplicationListener<OnRegistrationCompleteEvent> {

  @Autowired
  private VerificationTokenService verificationTokenService;

  @Autowired
  private JavaMailSender mailSender;

	@Override
	public void onApplicationEvent(OnRegistrationCompleteEvent event) {
    // 1. Generate a UUID as verification token.
    // 2. Send verification email to user's email.
    String token = UUID.randomUUID().toString();
    User user    = event.getUser();
    VerificationToken verificationToken = new VerificationToken(token, user);
    
    verificationTokenService.save(verificationToken);

    SimpleMailMessage email = new SimpleMailMessage();
    email.setTo(user.getEmail());
    email.setSubject("Registration Confirmation");
    email.setText(
      "http://localhost:8080" + 
      "/api/user/confirm?token" +
      token
    );

    mailSender.send(email);
	}
}