package org.app.auth_service.dto;

import java.util.List;
import java.util.Map;

public class UserInfoResponse {
  private String id;
  private String name;
  private String username;
  private String email;
  private Boolean enabled;
  private Map<String, List<String>> attributes;


  public UserInfoResponse() {}
  public UserInfoResponse(String id, String name, String username, String email, Boolean enabled,
      Map<String, List<String>> attributes) {
    this.id = id;
    this.name = name;
    this.username = username;
    this.email = email;
    this.enabled = enabled;
    this.attributes = attributes;
  }

  public String getId() {
    return id;
  }

  public String getUsername() {
    return username;
  }

  public String getEmail() {
    return email;
  }

  public Boolean getEnabled() {
    return enabled;
  }

  public Map<String, List<String>> getAttributes() {
    return attributes;
  }

  public void setId(String id) {
    this.id = id;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public void setEnabled(Boolean enabled) {
    this.enabled = enabled;
  }

  public void setAttributes(Map<String, List<String>> attributes) {
    this.attributes = attributes;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }
}
