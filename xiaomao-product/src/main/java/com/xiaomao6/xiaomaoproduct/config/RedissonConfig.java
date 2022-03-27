package com.xiaomao6.xiaomaoproduct.config;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

/**
 * @ClassName RedissonConfig
 * @Description 简介
 * @Author Micah
 * @Date 2022/3/22 17:30
 * @Version 1.0
 **/
@Configuration
public class RedissonConfig {

    @Bean(destroyMethod = "shutdown")
    public RedissonClient redisson() throws IOException {
        Config config = new Config();
        config.useSingleServer()
                .setAddress("redis://localhost:6379");
        RedissonClient redissonClient = Redisson.create(config);
        return redissonClient;
    }
}
