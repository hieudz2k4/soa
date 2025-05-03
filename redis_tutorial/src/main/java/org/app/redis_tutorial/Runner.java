/**
package org.app.redis_tutorial;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.security.Security;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;
import net.datafaker.Faker;
import org.app.redis_tutorial.model.Account;
import org.app.redis_tutorial.model.User;
import org.app.redis_tutorial.repository.AccountRepository;
import org.app.redis_tutorial.repository.TransactionRepository;
import org.app.redis_tutorial.repository.UserRepository;
import org.app.redis_tutorial.service.AccountService;
import org.app.redis_tutorial.service.UtilService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;

@Service
public class Runner implements CommandLineRunner {

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private AccountRepository accountRepository;

  @Autowired
  private TransactionRepository transactionRepository;

  @Autowired
  private AccountService accountService;

  @Autowired
  private UtilService utilService;

  @Override
  public void run(String... args) throws Exception {
    Faker faker = new Faker();

    List<User> users = new ArrayList<>();
    List<Account> accounts = new ArrayList<>();
    ObjectMapper objectMapper = new ObjectMapper();

    File usersJson = new File("src/main/resources/users.json");


    for (int i = 1; i <= 10000; i++) {
      User user = new User();

      user.setUser_id(null);
      user.setName(faker.name().fullName());
      user.setEmail(faker.internet().emailAddress());
      user.setPassword(utilService.bcrypt(faker.internet().password()));

      Account account = new Account();
      account.setAccount_id(null);
      account.setBalance(faker.number().randomDouble(2, 0, 1000));

      user.setAccount(account);

      objectMapper.writeValue(usersJson, user);
      users.add(user);
      objectMapper.writeValue(usersJson, user);

      accounts.add(account);

      System.out.println(user.getUser_id());
      if (i % 1000 == 0) {
        System.out.println("Saving " + i + " users and accounts");
        userRepository.saveAllAndFlush(users);
        accountRepository.saveAllAndFlush(accounts);

        users.clear();
        accounts.clear();
      }
    }



    // fake transactions
    for (int i = 1; i <= 10000; i++) {
      Optional fromAccount = accountRepository.findByIndex(new Random().nextInt(10000));
      Optional toAccount = accountRepository.findByIndex(new Random().nextInt(10000));
      double amount = faker.number().randomDouble(2, 0, 1000);

      ((Optional<Account>) fromAccount).ifPresent(account -> {
        ((Optional<Account>) toAccount).ifPresent(account1 -> {
          try {
            accountService.transferMoney((Account) account, (Account) account1, amount);
          } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
          }
        });
      });

    }


    System.out.println("Done");
  }
}
 **/