package com.yongcheng.auth.sevices;

import java.util.ArrayList;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class JwtUserDetailsService implements UserDetailsService {

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    // Temporarily hardcode userdetail. Will update code later.
    if (username.equals("yzhu002@citymail.cuny.edu")) {
      return new User(
        "yong", 
        "$2y$12$bO5l8jCzJsYDRqJqJwDRHOEd3cg0TACcS7aHQrZcppyb6e6gA4vhu", 
        new ArrayList<>()
      );
    } else {
      throw new UsernameNotFoundException("User not found with email: " + username);
    }
  }
  
}