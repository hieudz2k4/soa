package org.app.redis_tutorial.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import java.io.Serializable;
import java.util.UUID;


@Entity
public class User implements Serializable {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID user_id;
  private String name;
  private String email;
  private String password;

  @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  @JsonIgnore
  private Account account;

  public User() {
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public UUID getUser_id() {
    return user_id;
  }

  public String getName() {
    return name;
  }

  public String getEmail() {
    return email;
  }

  public Account getAccount() {
    return account;
  }

  public void setUser_id(UUID user_id) {
    this.user_id = user_id;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public void setAccount(Account account) {
    this.account = account;
  }
}
