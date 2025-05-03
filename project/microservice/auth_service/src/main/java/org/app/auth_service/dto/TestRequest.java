package org.app.auth_service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class TestRequest {
  private String accessToken;

  public String getAccessToken() {
    return accessToken;
  }
}
