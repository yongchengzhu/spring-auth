package com.yongcheng.auth.events;

import com.yongcheng.auth.models.User;

import org.springframework.context.ApplicationEvent;

@SuppressWarnings("serial")
public class OnRegistrationCompleteEvent extends ApplicationEvent {
  private String appUrl;
  private User user;

  public OnRegistrationCompleteEvent(String appUrl, User user) {
    super(user);

    this.appUrl = appUrl;
    this.user = user;
  }

  public String getAppUrl() {
    return appUrl;
  }

  public void setAppUrl(String appUrl) {
    this.appUrl = appUrl;
  }

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }
}