package org.app.redis_tutorial.service;

import jakarta.transaction.Transactional;
import org.app.redis_tutorial.model.Account;
import org.app.redis_tutorial.model.Transaction;
import org.app.redis_tutorial.repository.AccountRepository;
import org.app.redis_tutorial.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountService {
  @Autowired
  private AccountRepository accountRepository;
  @Autowired
  private TransactionRepository transactionRepository;

  @Transactional
  public void transferMoney(Account fromAccount, Account toAccount, double amount) {
    if (fromAccount.getBalance() < amount) {
      throw new RuntimeException("Insufficient balance");
    }

    fromAccount.setBalance(fromAccount.getBalance() - amount);
    toAccount.setBalance(toAccount.getBalance() + amount);

    Transaction transaction = new Transaction();
    transaction.setSender(fromAccount);
    transaction.setReceiver(toAccount);
    transaction.setAmount(amount);


    accountRepository.save(fromAccount);
    accountRepository.save(toAccount);
    transactionRepository.save(transaction);
  }
}
