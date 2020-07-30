package com.yongcheng.auth.sevices;

import javax.transaction.Transactional;

import com.yongcheng.auth.exceptions.UserAlreadyExistException;
import com.yongcheng.auth.models.User;
import com.yongcheng.auth.repositories.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class UserService {

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private PasswordEncoder passwordEncoder;

  /**
   * Used by AuthenticationManager after invoking JwtUserDetailsService.
   * Hence, should throw BadCredential exception upon failure.
   * 
   * @param email
   * @return
   * @throws UsernameNotFoundException
   */
  public User getUser(String email) throws UsernameNotFoundException {
    User user = userRepository.findByEmail(email);
    
    if (user == null) {
      throw new UsernameNotFoundException("Bad credentials: wrong username/email or password.");
    }

    return user;
  }

  public void save(User user) throws UserAlreadyExistException {
    if (emailExists(user.getEmail())) {
      throw new UserAlreadyExistException("Email already exist: " + user.getEmail());
    }

    user.setPassword(passwordEncoder.encode(user.getPassword()));
    
    userRepository.save(user);
  }

  private boolean emailExists(String email) {
    return userRepository.findByEmail(email) != null;
  }

}