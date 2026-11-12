package com.nguyenvudev.infrastructure.distributed.redisson.config;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class RedissonConfig {

    @Bean
    @Primary
    public RedissonClient config() {
        Config config = new Config();
        config.useSingleServer().setAddress("redis://127.0.0.1:6380");

        return Redisson.create(config);
    }
}
