package uet.soa.pastebin.infrastructure.config;

import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisPassword;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import uet.soa.pastebin.infrastructure.persistence.model.RedisPaste;

import java.time.Duration;

@Configuration
@EnableCaching
public class RedisConfig {
    @Bean
    public LettuceConnectionFactory redisConnectionFactory() {
        String host = System.getProperty("SPRING_REDIS_HOST");
        int port = Integer.parseInt(System.getProperty("SPRING_REDIS_PORT"));
        String password = System.getProperty("SPRING_REDIS_PASSWORD");

        RedisStandaloneConfiguration configuration = new RedisStandaloneConfiguration();
        configuration.setHostName(host);
        configuration.setPort(port);
        configuration.setPassword(RedisPassword.of(password));
        LettuceClientConfiguration clientConfig = LettuceClientConfiguration.builder()
            .useSsl()
            .build();

        return new LettuceConnectionFactory(configuration, clientConfig);
    }

    @Bean
    public RedisCacheConfiguration cacheConfiguration() {
        return RedisCacheConfiguration.defaultCacheConfig()
                .entryTtl(Duration.ofMinutes(60))
                .disableCachingNullValues()
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(new GenericJackson2JsonRedisSerializer()));
    }

    @Bean
    public RedisTemplate<String, RedisPaste> redisTemplate(RedisConnectionFactory factory) {
        RedisTemplate<String, RedisPaste> template = new RedisTemplate<>();
        template.setConnectionFactory(factory);
        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(new Jackson2JsonRedisSerializer<>(RedisPaste.class));
        return template;
    }

}
