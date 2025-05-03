package org.app.redis_tutorial.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import java.util.UUID;

@Entity
public class Transaction {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID transaction_id;

  @OneToOne
  private Account sender;

  @OneToOne
  private Account receiver;

  private double amount;

  public Transaction() {
  }

  public Transaction(UUID transaction_id, Account sender, Account receiver, double amount) {
    this.transaction_id = transaction_id;
    this.sender = sender;
    this.receiver = receiver;
    this.amount = amount;
  }

  public Account getReceiver() {
    return receiver;
  }

  public Account getSender() {
    return sender;
  }

  public void setSender(Account sender) {
    this.sender = sender;
  }

  public void setReceiver(Account receiver) {
    this.receiver = receiver;
  }

  public UUID getTransaction_id() {
    return transaction_id;
  }

  public double getAmount() {
    return amount;
  }

  public void setTransaction_id(UUID transaction_id) {
    this.transaction_id = transaction_id;
  }

  public void setAmount(double amount) {
    this.amount = amount;
  }
}
