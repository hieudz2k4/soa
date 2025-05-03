package uet.soa.pastebin.infrastructure.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import uet.soa.pastebin.infrastructure.interceptor.RateLimitInterceptor;

@Configuration
public class WebConfig implements WebMvcConfigurer {
  private final RateLimitInterceptor rateLimitInterceptor;

  public WebConfig(RateLimitInterceptor rateLimitInterceptor) {
    this.rateLimitInterceptor = rateLimitInterceptor;
  }

  @Override
  public void addInterceptors(InterceptorRegistry registry) {
    registry.addInterceptor(rateLimitInterceptor)
        .addPathPatterns("/api/**");
  }
}
