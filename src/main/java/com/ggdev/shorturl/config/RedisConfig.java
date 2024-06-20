package com.ggdev.shorturl.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;


//RedisConfig
@Slf4j
@RequiredArgsConstructor
@Configuration
@EnableCaching
@EnableRedisRepositories
public class RedisConfig {


	@Bean
	public RedisTemplate<?, ?> redisTemplate(RedisConnectionFactory factory) {
		RedisTemplate<byte[], byte[]> redisTemplate = new RedisTemplate<>();
		// 아래 두 라인을 작성하지 않으면, key값이 \xac\xed\x00\x05t\x00\x03sol 이렇게 조회된다.
		redisTemplate.setConnectionFactory(factory);
		redisTemplate.setKeySerializer(new StringRedisSerializer());
		redisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer());
		return redisTemplate;
	}

	@Bean
	public RedisCacheManager redisCacheManager(RedisConnectionFactory factory) {
		RedisCacheConfiguration redisCacheConfiguration = RedisCacheConfiguration
				.defaultCacheConfig()
				.disableCachingNullValues()
				.serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(new StringRedisSerializer()))
				.serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(new GenericJackson2JsonRedisSerializer()))
				.entryTtl(Duration.ofMinutes(10L));

		// 캐시키별 default 유효기간 설정
		Map<String, RedisCacheConfiguration> cacheConfiguration = new HashMap<>();

		cacheConfiguration.put(CacheKey.SHORTURL, RedisCacheConfiguration
				.defaultCacheConfig()
				.disableCachingNullValues()
				.serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(new StringRedisSerializer()))
				.serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(new GenericJackson2JsonRedisSerializer()))
				.entryTtl(Duration.ofSeconds(CacheKey.SHORTURL_EXPIRE))
		);

		return RedisCacheManager
				.RedisCacheManagerBuilder
				.fromConnectionFactory(factory)
				.cacheDefaults(redisCacheConfiguration)
				.withInitialCacheConfigurations(cacheConfiguration)
				.build();
	}



}
