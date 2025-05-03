package org.app.auth_service.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Builder
public class UserInfo {
  private String userId;
  private String firstName;
  private String lastName;
  private String username;
  private String email;

  public UserInfo() {}
  public UserInfo(String username, String email) {
    this.username = username;
    this.email = email;
  }

  public UserInfo(String userId, String firstName, String lastName, String username, String email) {
    this.userId = userId;
    this.firstName = firstName;
    this.lastName = lastName;
    this.username = username;
    this.email = email;
  }

  public String getFirstName() { return firstName; }
  public void setFirstName(String firstName) { this.firstName = firstName; }
  public String getLastName() { return lastName; }
  public void setLastName(String lastName) { this.lastName = lastName; }

  public String getUsername() { return username; }
  public void setUsername(String username) { this.username = username; }

  public String getEmail() { return email; }
  public void setEmail(String email) { this.email = email; }


  public String getUserId() {
    return userId;
  }

  public void setUserId(String userId) {
    this.userId = userId;
  }
}
