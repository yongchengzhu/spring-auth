package com.yongcheng.auth.events;

import com.yongcheng.auth.models.User;

import org.springframework.context.ApplicationEvent;

@SuppressWarnings("serial")
public class OnRegistrationCompleteEvent extends ApplicationEvent {
  private User user;

  public OnRegistrationCompleteEvent(User user) {
    super(user);

    this.user = user;
  }

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }
}