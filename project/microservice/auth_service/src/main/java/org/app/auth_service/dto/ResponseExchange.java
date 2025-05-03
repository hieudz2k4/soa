package org.app.auth_service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class ResponseExchange {
  private String accessToken;
  private String refreshToken;
  private String idToken;
  private String tokenType;
  private String scope;
  private long expiresIn;
  private String state;
  private String error;
  private String errorDescription;
}
