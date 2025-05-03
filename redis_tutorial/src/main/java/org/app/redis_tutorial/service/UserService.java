package org.app.redis_tutorial.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.app.redis_tutorial.model.User;
import org.app.redis_tutorial.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
  @Autowired
  private UserRepository userRepository;
  public Optional<List<User>> getAllUsers() {
    return Optional.of(userRepository.findAll());
  }

  public User getUserById(UUID id) {
    return userRepository.findById(id).orElse(null);
  }
}
