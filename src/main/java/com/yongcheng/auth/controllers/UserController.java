package com.yongcheng.auth.controllers;

import javax.validation.Valid;

import com.yongcheng.auth.models.User;
import com.yongcheng.auth.payloads.ApiResponse;
import com.yongcheng.auth.payloads.SignupRequest;
import com.yongcheng.auth.sevices.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
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
  UserService userService;

  @PostMapping("/signup")
  public ResponseEntity<?> signup(@RequestBody @Valid SignupRequest signupRequest) {
    final String email       = signupRequest.getEmail();
    final String password    = signupRequest.getPassword();
    final Boolean isVerified = false;
    
    final User user = new User(email, password, isVerified);

    userService.save(user);
    
    HttpHeaders responseHeaders = new HttpHeaders();
    responseHeaders.setLocation(null); // Set the location of the resource later.
    
    return new ResponseEntity<ApiResponse>(
      new ApiResponse(true, "User signup successful."), 
      responseHeaders, 
      HttpStatus.CREATED
    );
  }
}