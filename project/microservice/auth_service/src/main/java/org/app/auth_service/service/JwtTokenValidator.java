package org.app.auth_service.service;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.KeySourceException;
import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.JWKSelector;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.JWSKeySelector;
import com.nimbusds.jose.proc.JWSVerificationKeySelector;
import com.nimbusds.jose.proc.SecurityContext;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.proc.ConfigurableJWTProcessor;
import com.nimbusds.jwt.proc.DefaultJWTClaimsVerifier;
import com.nimbusds.jwt.proc.DefaultJWTProcessor;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

@Component
public class JwtTokenValidator {

  @Value("${spring.security.oauth2.resourceserver.jwt.jwk-set-uri}")
  private String jwkSetUriString;

  @Value("${spring.security.oauth2.resourceserver.jwt.issuer-uri}")
  private String expectedIssuer;

  @Value("${spring.security.oauth2.resourceserver.jwt.audiences}")
  private String expectedAudience;

  @Value("${spring.security.oauth2.resourceserver.jwt.cache.cache-duration}")
  private long cacheExpireMinutes;

  @Value("${spring.security.oauth2.resourceserver.jwt.cache.cache-size}")
  private long cacheMaxSize;

  private ConfigurableJWTProcessor<SecurityContext> jwtProcessor;
  private LoadingCache<String, JWKSet> jwkSetCache;
  private URL jwkSetURL;

  private final Logger log = org.slf4j.LoggerFactory.getLogger(JwtTokenValidator.class);

  @PostConstruct
  public void init() {
    try {
      jwkSetURL = new URL(jwkSetUriString);
      log.info("Initializing JWT Validator with JWKSet URI: {}", jwkSetURL);
      log.info("Expected Issuer: {}", expectedIssuer);
      log.info("Expected Audience: {}", expectedAudience);

      // --- Caching JWKSet ---
      CacheLoader<String, JWKSet> loader = new CacheLoader<>() {
        @Override
        public JWKSet load(String key) throws Exception {
          // key ở đây chính là jwkSetURL.toString()
          log.info("Fetching JWKSet from URI: {}", key);
          return JWKSet.load(new URL(key));
        }
      };

      jwkSetCache = CacheBuilder.newBuilder()
          .maximumSize(cacheMaxSize)
          .expireAfterWrite(cacheExpireMinutes, TimeUnit.MINUTES)
          .build(loader);
      // Pre-load cache on startup (optional but good)
      try {
        jwkSetCache.get(jwkSetURL.toString());
        log.info("JWKSet pre-loaded successfully.");
      } catch (ExecutionException e) {
        log.error("Failed to pre-load JWKSet during initialization", e);
        // Decide if application should fail to start or try again later
      }

      // Use a JWKSource that uses the cache
      JWKSource<SecurityContext> keySource = new JWKSource<SecurityContext>() {
        @Override
        public List<JWK> get(JWKSelector jwkSelector, SecurityContext context) throws KeySourceException {
          try {
            JWKSet jwkSet = jwkSetCache.get(jwkSetURL.toString());
            return jwkSelector.select(jwkSet);
          } catch (ExecutionException e) {
            log.error("Failed to retrieve JWKSet from cache", e);
            // Invalidate cache entry if loading failed? Consider retry logic?
            jwkSetCache.invalidate(jwkSetURL.toString());
            throw new KeySourceException("Failed to retrieve JWKSet from cache", e);
          }
        }
      };

      jwtProcessor = new DefaultJWTProcessor<>();

      // Configure the key selector
      JWSKeySelector<SecurityContext> keySelector = new JWSVerificationKeySelector<>(
          JWSAlgorithm.RS256, // <--- Kiểm tra lại thuật toán này với Keycloak
          keySource
      );
      jwtProcessor.setJWSKeySelector(keySelector);

      // Configure the claims verifier: checks expiration, audience, etc.
      jwtProcessor.setJWTClaimsSetVerifier(new DefaultJWTClaimsVerifier<>(
          new JWTClaimsSet.Builder().audience(expectedAudience).build(), // Audience phải khớp
          new HashSet<>(Arrays.asList("sub", "iss", "exp", "aud")) // Các claims bắt buộc
      ));

      log.info("JWT Validator Initialized successfully.");

    } catch (MalformedURLException e) {
      log.error("Invalid JWK Set URI: {}", jwkSetUriString, e);
      throw new RuntimeException("Invalid JWK Set URI configuration", e);
    } catch (Exception e) {
      log.error("Error initializing JWT Validator", e);
      throw new RuntimeException("Error initializing JWT Validator", e);
    }
  }

  public JWTClaimsSet validate(String token) throws Exception {
    if (token == null || token.isBlank()) {
      throw new IllegalArgumentException("Token cannot be null or blank");
    }
    SecurityContext context = null; // Can be null if no specific security context is needed
    JWTClaimsSet claimsSet = jwtProcessor.process(token, context);

    // Double-check issuer manually for extra safety
    if (!expectedIssuer.equals(claimsSet.getIssuer())) {
      throw new Exception(
          "Invalid JWT issuer: expected '" + expectedIssuer + "' but got '" + claimsSet.getIssuer()
              + "'");
    }

    log.debug("Token validated successfully for subject: {}", claimsSet.getSubject());
    return claimsSet;
  }
}