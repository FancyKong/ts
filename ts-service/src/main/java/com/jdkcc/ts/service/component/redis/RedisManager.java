/**
 * Bestpay.com.cn Inc.
 * Copyright (c) 2011-2016 All Rights Reserved.
 */
package com.jdkcc.ts.service.component.redis;

import com.google.common.base.Throwables;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Jiangjiaze
 * @version Id: RedisManagers.java, v 0.1 2016/10/15 10:51 FancyKong Exp $$
 */
@Slf4j
@Component
public class RedisManager {

    @Resource(name = "connectionFactory")
    private JedisConnectionFactory connectionFactory;

    private static Map<Class, RedisTemplate<String, ?>> templateMap = new HashMap<>();

    /**
     * 获取redis数据
     */
    public <V> V get(String key, Class<V> vClass) {
        try {
            log.debug("Load from redis, KEY: {}", key);
            ValueOperations<String, V> ops = getTemplate(vClass).opsForValue();
            return ops.get(key);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("Redis get error, KEY: {}, CASE: {}", key, Throwables.getStackTraceAsString(e));
            return null;
        }
    }

    /**
     * 设置
     */
    public <V> void set(String key, V value, Class<V> vClass) {
        try {
            log.debug("Set redis, KEY: {}, VALUE: {}", key, value);
            ValueOperations<String, V> ops = getTemplate(vClass).opsForValue();
            ops.set(key, value);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("Redis set error, KEY: {}, VALUE: {}", key, value, Throwables.getStackTraceAsString(e));
        }
    }

    private <V> RedisTemplate getTemplate(Class<V> clazz) {
        RedisTemplate<String, ?> redisTemplate = templateMap.get(clazz);
        if (redisTemplate == null) {
            redisTemplate = new RedisTemplate<>();
            RedisSerializer valueSerializer = new ProtoSerializerForRedis<>(clazz);
            StringRedisSerializer keySerializer = new StringRedisSerializer();
            redisTemplate.setConnectionFactory(connectionFactory);
            redisTemplate.setKeySerializer(keySerializer);
            redisTemplate.setHashKeySerializer(keySerializer);
            redisTemplate.setValueSerializer(valueSerializer);
            redisTemplate.setHashValueSerializer(valueSerializer);
            redisTemplate.afterPropertiesSet();
            templateMap.put(clazz, redisTemplate);
            return redisTemplate;
        } else {
            return redisTemplate;
        }
    }
}
