package com.yongcheng.auth.events;

import com.yongcheng.auth.models.User;

import org.springframework.context.ApplicationEvent;

@SuppressWarnings("serial")
public class OnRegistrationCompleteEvent extends ApplicationEvent {
  private User user;
  private String requestURL;

  public OnRegistrationCompleteEvent(User user, String requestURL) {
    super(user);

    this.user = user;
    this.requestURL = requestURL;
  }

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }

  public String getRequestURL() {
    return requestURL;
  }

  public void setRequestURL(String requestURL) {
    this.requestURL = requestURL;
  }
}