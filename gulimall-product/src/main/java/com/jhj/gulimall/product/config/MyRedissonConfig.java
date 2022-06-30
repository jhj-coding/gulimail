package com.jhj.gulimall.product.config;


import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

/**
 * @Description MyRedissonConfig
 * @Author jhj
 * @Date 2022/6/3014:38
 **/
@Configuration
public class MyRedissonConfig {
    @Bean(destroyMethod = "shutdown")
    public RedissonClient redisson() throws IOException{
        Config config = new Config();
        //单节点
        config.useSingleServer().setAddress("redis://192.168.16.223:6379");
        return Redisson.create();
    }
}
