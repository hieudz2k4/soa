package org.app.auth_service.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.gson.JsonParser;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import org.app.auth_service.dto.UserInfo;
import org.app.auth_service.dto.UserInfoResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import utils.Utils;

@Service
public class KeycloakService {

  @Value("${keycloak.base-url}")
  private String keycloakBaseUrl;
  @Value("${keycloak.client-id}")
  private String clientId;
  @Value("${keycloak.client-secret}")
  private String clientSecret;
  @Value("${keycloak.realm}")
  private String realm;

  @Autowired
  private RestTemplate restTemplate;


  private String getAccessToken() {
    String endpoint = keycloakBaseUrl + "/realms/soa/protocol/openid-connect/token";

    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

    MultiValueMap<String, String> form = new LinkedMultiValueMap<>();
    form.add("client_id", clientId);
    form.add("client_secret", clientSecret);
    form.add("grant_type", "client_credentials");

    HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(form, headers);

    String response = restTemplate.postForObject(endpoint, request, String.class);

    if (response != null) {
      String accessToken = JsonParser.parseString(response)
          .getAsJsonObject().get("access_token").getAsString();
      return accessToken;
    }

    return null;
  }


  public List<UserInfo> getListOfUsers() {
    String accessToken = getAccessToken();
    if (accessToken != null) {
      String endpoint = keycloakBaseUrl + "/admin/realms/soa/users";

      HttpHeaders headers = new HttpHeaders();
      headers.setBearerAuth(accessToken);
      HttpEntity<Void> entity = new HttpEntity<>(headers);

      ResponseEntity<String> response = restTemplate.exchange(
          endpoint,
          HttpMethod.GET,
          entity,
          String.class,
          realm
      );

      try {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode root = objectMapper.readTree(response.getBody());
        List<UserInfo> userList = new ArrayList<>();

        for (JsonNode node : root) {
          String username = node.path("username").asText();
          String email = node.path("email").asText();
          userList.add(new UserInfo(username, email));
        }

        return userList;
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
    return Collections.emptyList();
  }

  public String saveProfile(UserInfo userInfo) {
    String accessToken = getAccessToken();
    if (accessToken != null) {
      String endpoint = keycloakBaseUrl + "/admin/realms/soa/users/" + userInfo.getUserId();

      HttpHeaders headers = new HttpHeaders();
      headers.setBearerAuth(accessToken);
      headers.setContentType(MediaType.APPLICATION_JSON);

      ObjectMapper mapper = new ObjectMapper();
      String jsonString = "";
      try {
        ObjectNode node = mapper.valueToTree(userInfo);
        node.remove("userId");
        jsonString = mapper.writeValueAsString(node);
      } catch (JsonProcessingException e) {
        throw new RuntimeException("Failed to convert userInfo to JSON", e);
      }

      HttpEntity<String> entity = new HttpEntity<>(jsonString, headers);

      ResponseEntity<String> response = restTemplate.exchange(
          endpoint,
          HttpMethod.PUT,
          entity,
          String.class
      );

      return response.getBody();
    }
    return null;
  }

  public UserInfoResponse getUserInfoById(String userId) {
    String accessToken = getAccessToken();
    if (accessToken != null) {
      String endpoint = keycloakBaseUrl + "/admin/realms/soa/users/" + userId;

      HttpHeaders headers = new HttpHeaders();
      headers.setBearerAuth(accessToken);
      HttpEntity<Void> entity = new HttpEntity<>(headers);

      ResponseEntity<UserInfoResponse> resp = restTemplate.exchange(
          endpoint,
          HttpMethod.GET,
          entity,
          UserInfoResponse.class
      );

      if (resp.getStatusCode().is2xxSuccessful() && resp.getBody() != null) {
        return resp.getBody();
      } else {
        throw new RestClientException(
            "Failed to fetch user info: HTTP " + resp.getStatusCodeValue()
        );

      }
    }
    return null;
  }
}
