package com.yongcheng.auth.controllers;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import com.yongcheng.auth.events.OnRegistrationCompleteEvent;
import com.yongcheng.auth.models.User;
import com.yongcheng.auth.payloads.SignupRequest;
import com.yongcheng.auth.sevices.UserService;
import com.yongcheng.auth.sevices.VerificationTokenService;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/api/user")
public class UserController {
  
  @Autowired
  private UserService userService;

  @Autowired
  private VerificationTokenService tokenService;

  @Autowired
  private ApplicationEventPublisher eventPublisher;

  Logger logger = LoggerFactory.getLogger(UserController.class);

  @PostMapping("/signup")
  @ResponseStatus(HttpStatus.CREATED)
  public void signup(@RequestBody @Valid SignupRequest signupRequest, HttpServletResponse res, UriComponentsBuilder uriBuilder) {
    final String email    = signupRequest.getEmail();
    final String password = signupRequest.getPassword();
    final User   user     = new User(email, password);
    final String baseURL  = uriBuilder.path("").toUriString();

    final OnRegistrationCompleteEvent event = 
      new OnRegistrationCompleteEvent(user, baseURL.toString());

    userService.save(user);

    eventPublisher.publishEvent(event);

    res.addHeader("Location", baseURL + "/" + user.getId());
  }

  @PutMapping("/confirm")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void confirmSignup(@RequestParam("token") String token) {
    tokenService.confirm(token);
  }
}