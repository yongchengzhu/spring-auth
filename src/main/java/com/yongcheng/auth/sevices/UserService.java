package com.yongcheng.auth.sevices;

import com.yongcheng.auth.models.User;
import com.yongcheng.auth.repositories.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

  @Autowired
  UserRepository userRepository;

  public void save(User user) {
    userRepository.save(user);
  }
}