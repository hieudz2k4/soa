package org.app.auth_service.controller;

import com.nimbusds.jwt.JWTClaimsSet;
import java.util.List;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.app.auth_service.dto.RequestEmailVerifi;
import org.app.auth_service.dto.RequestExchange;
import org.app.auth_service.dto.TestRequest;
import org.app.auth_service.dto.UserInfo;
import org.app.auth_service.dto.UserInfoResponse;
import org.app.auth_service.service.JwtTokenValidator;
import org.app.auth_service.service.KeycloakService;
import org.app.auth_service.service.VerificationEmail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/v1/auth")
@CrossOrigin(origins = "*")
public class Auth {

  @Autowired
  private JwtTokenValidator jwtTokenValidator;

  @Autowired
  private KeycloakService keycloakService;

  @Autowired
  private VerificationEmail verificationEmail;

  @PostMapping("/exchange")
  public String getAccessToken(@RequestBody RequestExchange requestExchange) {
    return "Access Token";
  }

  @PostMapping("/verification-email")
  public String verifyCode(@RequestBody RequestEmailVerifi request) {
    // log.info("Request to verify email: {}", request.getEmail());
    return verificationEmail.generateVerificationCode(request.getEmail());
  }

  @GetMapping("/user-info")
  public Optional<List<UserInfo>> getUserInfo() {
    // log.info("Request to get user info");
    return Optional.of(keycloakService.getListOfUsers());
  }


  @PostMapping("/test")
  public JWTClaimsSet test(@RequestBody TestRequest testRequest) throws Exception {
    // log.info("Request to test JWT token: {}", testRequest.getAccessToken());
    return jwtTokenValidator.validate(testRequest.getAccessToken());
  }

  @PostMapping("/save-profile")
  public String saveProfile(@RequestBody UserInfo userInfo) {
    // log.info("Request to save user profile: {}", userInfo);
    keycloakService.saveProfile(userInfo);
    return "Profile saved successfully";
  }

  @GetMapping("/get-profile/{userId}")
  public UserInfoResponse getProfile(@PathVariable String userId) {
    // log.info("Request to get user profile for userId: {}", userId);
    return keycloakService.getUserInfoById(userId);
  }

}
