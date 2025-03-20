package uet.soa.pastebin.infrastructure.config;

import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Bucket4j;
import io.github.bucket4j.Refill;
import java.time.Duration;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RateLimitConfig {
  public Bucket createNewBucket(int capacity, int refillTokens, int refillPeriod) {
    return Bucket4j.builder()
      .addLimit(Bandwidth.classic(capacity, Refill.intervally(refillTokens, Duration.ofSeconds(refillPeriod))))
      .build();
  }
}
