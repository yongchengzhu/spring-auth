package com.yongcheng.auth.controllers;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import com.yongcheng.auth.events.OnRegistrationCompleteEvent;
import com.yongcheng.auth.exceptions.UserAlreadyExistException;
import com.yongcheng.auth.models.User;
import com.yongcheng.auth.payloads.ApiResponse;
import com.yongcheng.auth.payloads.SignupRequest;
import com.yongcheng.auth.sevices.UserService;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
public class UserController {
  
  @Autowired
  private UserService userService;

  @Autowired
  private ApplicationEventPublisher eventPublisher;

  @PostMapping("/signup")
  public ResponseEntity<?> signup(@RequestBody @Valid SignupRequest signupRequest) {
    final String email    = signupRequest.getEmail();
    final String password = signupRequest.getPassword();
    final User   user     = new User(email, password);

    try {
      userService.save(user);
      eventPublisher.publishEvent(new OnRegistrationCompleteEvent(user));
    } catch (UserAlreadyExistException e) {
      return new ResponseEntity<>(
        new ApiResponse(e.getMessage(), e), HttpStatus.BAD_REQUEST
      );
    } catch (RuntimeException e) {
      return new ResponseEntity<>(
        new ApiResponse(e.getMessage(), e), HttpStatus.INTERNAL_SERVER_ERROR
      );
    }

    return new ResponseEntity<>(
      new ApiResponse("User signup successful."), HttpStatus.CREATED
    );
  }
}