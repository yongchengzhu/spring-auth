package com.yongcheng.auth.events;

import com.yongcheng.auth.sevices.VerificationTokenService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

@Component
public class MyContextRefreshListener implements ApplicationListener<ContextRefreshedEvent> {

  @Autowired
  VerificationTokenService verificationTokenService;

  @Override
  public void onApplicationEvent(ContextRefreshedEvent event) {
    verificationTokenService.purgeExpired();
  }
  
}