package org.app.redis_tutorial.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UtilService {
  private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

  public String bcrypt(String password) {
    return passwordEncoder.encode(password);
  }

  public boolean verify(String password, String hash) {
    return passwordEncoder.matches(password, hash);
  }
}
