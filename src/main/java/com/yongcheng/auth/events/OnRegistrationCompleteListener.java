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
    String token = UUID.randomUUID().toString();
    User   user  = event.getUser();

    generateVerificationToken(token, user);
    sendConfirmationEmail(token, user);
  }
  
  public void generateVerificationToken(String token, User user) {
    VerificationToken verificationToken = new VerificationToken(token, user);
    
    verificationTokenService.save(verificationToken);
  }

  public void sendConfirmationEmail(String token, User user) {
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