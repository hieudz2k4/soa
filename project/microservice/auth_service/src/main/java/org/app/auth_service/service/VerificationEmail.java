package org.app.auth_service.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VerificationEmail {
  @Autowired
  private GrpcNotificationService grpcNotificationService;

  public String generateVerificationCode(String email) {
    int code = (int) (Math.random() * 900000) + 100000;

    grpcNotificationService.sendVerificationEmail(email, String.valueOf(code));

    // send the code to the user's email
    return String.valueOf(code);
  }
}
