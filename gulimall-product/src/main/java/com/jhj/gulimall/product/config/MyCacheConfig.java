package com.jhj.gulimall.product.config;

import org.springframework.boot.autoconfigure.cache.CacheProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import javax.annotation.Resource;

/**
 * @Description MyCacheConfig
 * @Author jhj
 * @Date 2022/6/3015:49
 **/
@EnableConfigurationProperties(CacheProperties.class)
@EnableCaching
@Configuration
public class MyCacheConfig {

    @Bean
    RedisCacheConfiguration redisCacheConfiguration(CacheProperties cacheProperties){
        RedisCacheConfiguration redisCacheConfiguration = RedisCacheConfiguration
                .defaultCacheConfig()
                .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(new StringRedisSerializer()))
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(new GenericJackson2JsonRedisSerializer()));

        CacheProperties.Redis redis = cacheProperties.getRedis();
        if (redis.getTimeToLive()!=null){
            redisCacheConfiguration = redisCacheConfiguration.entryTtl(redis.getTimeToLive());
        }

        if (redis.getKeyPrefix()!=null){
            redisCacheConfiguration = redisCacheConfiguration.prefixKeysWith(redis.getKeyPrefix());
        }

        if (!redis.isCacheNullValues()){
            redisCacheConfiguration = redisCacheConfiguration.disableCachingNullValues();
        }

        if (!redis.isUseKeyPrefix()){
            redisCacheConfiguration = redisCacheConfiguration.disableKeyPrefix();
        }

        return redisCacheConfiguration;
    }
}
