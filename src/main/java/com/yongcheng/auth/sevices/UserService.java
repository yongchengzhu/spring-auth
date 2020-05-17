package com.yongcheng.auth.sevices;

import javax.transaction.Transactional;

import com.yongcheng.auth.exceptions.UserAlreadyExistException;
import com.yongcheng.auth.models.User;
import com.yongcheng.auth.repositories.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class UserService {

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private PasswordEncoder passwordEncoder;

  public void save(User user) {
    if (emailExists(user.getEmail())) {
      throw new UserAlreadyExistException(
        "There is already an account registered with that email: " +
        user.getEmail());
    }

    user.setPassword(passwordEncoder.encode(user.getPassword()));
    
    userRepository.save(user);
  }

  private boolean emailExists(String email) {
    return userRepository.findByEmail(email) != null;
  }
}