package org.app.redis_tutorial;

import java.time.Duration;
import java.util.List;
import java.util.Optional;
import org.app.redis_tutorial.model.User;
import org.app.redis_tutorial.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class RedisRunner implements CommandLineRunner {
  @Autowired
  private RedisTemplate redisTemplate;
  @Autowired
  private UserService userService;

  @Override
  public void run(String... args) throws Exception {
    Optional<List<User>> users = userService.getAllUsers();

    System.out.println(users.get().size());
    if (users.isPresent()) {
      for (User user : users.get()) {
        redisTemplate.opsForHash().put("users", user.getUser_id(), user);
      }
      redisTemplate.expire("users", Duration.ofMinutes(5));
    }

    System.out.println(redisTemplate.opsForHash().get("users", users.get().get(0).getUser_id()));
  }
}
