package com.example.config;


import com.example.model.ChattingMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;


@Configuration
public class RedisConfig {

    private final Logger log = LoggerFactory.getLogger(getClass());

    // Redis 서버와 상호작용하기 위한 RedisTemplate 설정
    // key와 value에 Serializer 해줌 (Redis 서버는 bytes 코드만 저장)
    // Json 포맷 형식으로 메시지 교환 위해 Jackson2JsonRedisSerializer 설정
//    @Bean
//    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory){
//        RedisTemplate<String, Object> template = new RedisTemplate<>();
//        template.setConnectionFactory(redisConnectionFactory);
//        template.setKeySerializer(new StringRedisSerializer());
//        template.setValueSerializer(new Jackson2JsonRedisSerializer<>(String.class));
//
//        return template;
//    }

    @Bean
    public RedisTemplate<String, ChattingMessage> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, ChattingMessage> template = new RedisTemplate<>();
        template.setConnectionFactory(redisConnectionFactory);

        // Key Serializer
        template.setKeySerializer(new StringRedisSerializer());

        // Value Serializer using Jackson2JsonRedisSerializer
        Jackson2JsonRedisSerializer<ChattingMessage> jsonSerializer = new Jackson2JsonRedisSerializer<>(ChattingMessage.class);
        template.setValueSerializer(jsonSerializer);

        // Hash Key Serializer
        template.setHashKeySerializer(new StringRedisSerializer());

        // Hash Value Serializer using Jackson2JsonRedisSerializer
        template.setHashValueSerializer(jsonSerializer);

        log.info("RedisTemplate configured successfully");

        return template;
    }


}
