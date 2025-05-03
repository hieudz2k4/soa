package org.app.auth_service.dto;

public class RequestEmailVerifi {
  private String email;

  public RequestEmailVerifi() {}

  public RequestEmailVerifi(String email) {
    this.email = email;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

}
