package uet.soa.pastebin.infrastructure.interceptor;

import io.github.bucket4j.Bucket;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import uet.soa.pastebin.infrastructure.config.RateLimitConfig;

@Component
public class RateLimitInterceptor implements HandlerInterceptor {
  private final RateLimitConfig rateLimitConfig;
  private final ConcurrentHashMap<String, Bucket> bucketMap = new ConcurrentHashMap<>();


  public RateLimitInterceptor(RateLimitConfig rateLimitConfig) {
    this.rateLimitConfig = rateLimitConfig;
  }

  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
    String ip = request.getRemoteAddr();
    String key = "rate_limit:" + ip;

    Bucket bucket = bucketMap.computeIfAbsent(key,
                                              k -> rateLimitConfig.createNewBucket(100, 10, 60));

    if (!bucket.tryConsume(1)) {
      response.setStatus(HttpStatus.TOO_MANY_REQUESTS.value());
      return false;
    }

    return true;
  }
}