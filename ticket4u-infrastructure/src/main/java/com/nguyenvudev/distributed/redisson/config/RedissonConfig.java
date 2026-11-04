package com.nguyenvudev.distributed.redisson.config;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RedissonConfig {


    @Bean
    public RedissonClient redissonConfig() {
        Config config = new Config();
        config.useClusterServers().addNodeAddress("redis://127.0.0.1:6180");

        return Redisson.create(config);
    }
}
