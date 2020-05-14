package com.yongcheng.auth.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class User {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long userId;

  @Column(unique = true)
  private String email;

  private String password;

  private Boolean isVerified;

  // Constructor
  public User(String email, String password, Boolean isVerified) {
    this.email = email;
    this.password = password;
    this.isVerified = isVerified;
  }

  // Getters and Setters
  public Long getUserId() {
    return userId;
  }

  public void setUserId(Long userId) {
    this.userId = userId;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public Boolean isVerified() {
    return isVerified;
  }

  public void setVerified(Boolean isVerified) {
    this.isVerified = isVerified;
  }
}