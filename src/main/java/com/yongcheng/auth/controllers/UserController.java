package com.yongcheng.auth.controllers;

import javax.validation.Valid;

import com.yongcheng.auth.events.OnRegistrationCompleteEvent;
import com.yongcheng.auth.models.User;
import com.yongcheng.auth.payloads.ApiResponse;
import com.yongcheng.auth.payloads.SignupRequest;
import com.yongcheng.auth.sevices.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
public class UserController {
  
  @Autowired
  private UserService userService;

  @Autowired
  private ApplicationEventPublisher eventPublisher;

  @PostMapping("/signup")
  @ResponseStatus(HttpStatus.CREATED)
  public ApiResponse signup(@RequestBody @Valid SignupRequest signupRequest) {
    final String email    = signupRequest.getEmail();
    final String password = signupRequest.getPassword();
    final User   user     = new User(email, password);

    userService.save(user);

    eventPublisher.publishEvent(new OnRegistrationCompleteEvent(user));

    return new ApiResponse("User signup successful");
  }
}