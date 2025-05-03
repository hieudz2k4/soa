package org.app.redis_tutorial.controller;


import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.app.redis_tutorial.model.User;
import org.app.redis_tutorial.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {
  @Autowired
  private UserService userService;

  @GetMapping
  public Optional<List<User>> getAllUsers() {
    return userService.getAllUsers();
  }

  @GetMapping("/{id}")
  public Optional<User> getUserById(@PathVariable String id) {
    return Optional.of(userService.getUserById(UUID.fromString(id)));
  }
}
