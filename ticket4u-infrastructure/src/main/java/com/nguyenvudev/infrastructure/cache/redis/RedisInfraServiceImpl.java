package com.nguyenvudev.cache.redis;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import tools.jackson.core.JacksonException;
import tools.jackson.databind.ObjectMapper;

import java.util.Map;
import java.util.Optional;

@Service
@Slf4j
public class RedisInfraServiceImpl implements RedisInfraService {

    private final RedisTemplate<Object, Object> redisTemplate;

    public RedisInfraServiceImpl(RedisTemplate<Object, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public void setString(String key, String value) {
        if (StringUtils.hasLength(key)) {
            return;
        }
        redisTemplate.opsForValue().set(key, value);
    }

    @Override
    public String getString(String key) {
        return Optional.ofNullable(redisTemplate.opsForValue().get(key)).map(String::valueOf).orElse(null);
    }

    @Override
    public void setObject(String key, Object value) {
        if (StringUtils.hasLength(key)) {
            return;
        }

        try {
            this.redisTemplate.opsForValue().set(key, value);
        } catch (Exception e) {
            log.error("setObject error:{}", e.getMessage());
        }
    }

    @Override
    public <T> T getObject(String key, Class<T> targetClass) {
        Object result = this.redisTemplate.opsForValue().get(key);
        log.info("getObject info:{}", result);

        if (result == null) {
            return null;
        }

        if (result instanceof Map){
            try {
                ObjectMapper objectMapper = new ObjectMapper();
                return objectMapper.convertValue(result,targetClass);
            }catch (IllegalArgumentException e){
                log.error("Error converting LinkedHashMap to object: {}",e.getMessage());
                return null;
            }
        }

        if (result instanceof String){
            try {
                ObjectMapper objectMapper = new ObjectMapper();
                return objectMapper.readValue((String)result,targetClass);
            }catch (JacksonException e){
                log.error("Error converting LinkedHashMap to object: {}",e.getMessage());
                return null;
            }
        }

        return null;
    }

}
