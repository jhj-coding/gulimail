package com.jhj.gulimall.product;


import com.jhj.gulimall.product.service.BrandService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.UUID;


@RunWith(SpringRunner.class)
@SpringBootTest
public class GulimallProductApplicationTests {
    @Autowired
    BrandService brandService;
    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Test
    public void contextLoads() {
        System.out.println(1);
    }


    @Test
    public void redisTest() {
        ValueOperations<String, String> stringStringValueOperations = stringRedisTemplate.opsForValue();
        //保存
        stringStringValueOperations.set("hello","word_"+ UUID.randomUUID());
        //查询
        System.out.println(stringStringValueOperations.get("hello"));
    }



}
