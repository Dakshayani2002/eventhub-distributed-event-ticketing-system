package com.eventhub.config;

import com.eventhub.dto.EventResponse;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.databind.DeserializationFeature;


//@Configuration
//public class RedisCacheConfig {
//
//    @Bean
//    public CacheManager cacheManager(RedisConnectionFactory connectionFactory) {
//
//        ObjectMapper redisMapper = new ObjectMapper();
//        redisMapper.findAndRegisterModules();
//
//        redisMapper.activateDefaultTyping(
//                redisMapper.getPolymorphicTypeValidator(),
//                ObjectMapper.DefaultTyping.NON_FINAL
//        );
//
//        GenericJackson2JsonRedisSerializer serializer =
//                new GenericJackson2JsonRedisSerializer(redisMapper);
//
//        RedisCacheConfiguration config =
//                RedisCacheConfiguration.defaultCacheConfig()
//                        .entryTtl(Duration.ofMinutes(10))
//                        .serializeKeysWith(
//                                RedisSerializationContext.SerializationPair
//                                        .fromSerializer(new StringRedisSerializer()))
//                        .serializeValuesWith(
//                                RedisSerializationContext.SerializationPair
//                                        .fromSerializer(serializer));
//
//        return RedisCacheManager.builder(connectionFactory)
//                .cacheDefaults(config)
//                .build();
//    }
//}
@Configuration
public class RedisCacheConfig {

    @Bean
    public CacheManager cacheManager(RedisConnectionFactory connectionFactory) {

        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        // Deliberately NOT calling activateDefaultTyping() — we don't need it.

        // Typed serializer for a single EventResponse
        Jackson2JsonRedisSerializer<EventResponse> eventSerializer =
                new Jackson2JsonRedisSerializer<>(mapper, EventResponse.class);

        // Typed serializer for List<EventResponse>
        JavaType listType = mapper.getTypeFactory()
                .constructCollectionType(List.class, EventResponse.class);
        Jackson2JsonRedisSerializer<List<EventResponse>> listSerializer =
                new Jackson2JsonRedisSerializer<>(mapper, listType);

        RedisCacheConfiguration base = RedisCacheConfiguration.defaultCacheConfig()
                .entryTtl(Duration.ofMinutes(10))
                .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(new StringRedisSerializer()));

        Map<String, RedisCacheConfiguration> perCacheConfigs = new HashMap<>();
        perCacheConfigs.put("events",
                base.serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(eventSerializer)));
        perCacheConfigs.put("allEvents",
                base.serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(listSerializer)));

        return RedisCacheManager.builder(connectionFactory)
                .cacheDefaults(base.serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(eventSerializer)))
                .withInitialCacheConfigurations(perCacheConfigs)
                .build();
    }
}