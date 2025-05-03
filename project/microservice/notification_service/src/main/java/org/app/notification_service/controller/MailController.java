package org.app.notification_service.controller;

import org.app.notification_service.dto.MailRequest;
import org.app.notification_service.service.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/mail")
public class MailController {
  @Autowired
  private MailService mailService;

  @PostMapping("/sender")
  public ResponseEntity<String> sendEmail(@RequestBody MailRequest mailRequest) {
    try {
      mailService.sendEmailAsync(mailRequest.getTo(), mailRequest.getSubject(), mailRequest.getBody());
    } catch (Exception e) {
      return ResponseEntity.status(500).body("Failed to send email: " + e.getMessage());
    }
    return ResponseEntity.ok("Email sent successfully");
  }
}
