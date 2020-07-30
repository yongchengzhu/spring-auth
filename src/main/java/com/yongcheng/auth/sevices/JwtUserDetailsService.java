package com.yongcheng.auth.sevices;

import com.yongcheng.auth.models.User;
import com.yongcheng.auth.models.UserPrincipal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class JwtUserDetailsService implements UserDetailsService {

  @Autowired
  UserService userService;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    try {
      User user = userService.getUser(username);
      return new UserPrincipal(user.getEmail(), user.getPassword(), user.getEnabled());
    } catch (UsernameNotFoundException ex) {
      // The AuthenticationManager overwrites this message anyway.
      throw new UsernameNotFoundException(ex.getMessage(), ex);
    }
  }
  
}