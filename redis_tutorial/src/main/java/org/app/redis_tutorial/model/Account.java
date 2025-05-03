package org.app.redis_tutorial.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.util.UUID;

@Entity
public class Account {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID account_id;
  private double balance;

  public Account() {
    this.account_id = UUID.randomUUID();
    this.balance = 0.0;
  }

  public UUID getAccount_id() {
    return account_id;
  }

  public double getBalance() {
    return balance;
  }

  public void setAccount_id(UUID account_id) {
    this.account_id = account_id;
  }

  public void setBalance(double balance) {
    this.balance = balance;
  }
}
