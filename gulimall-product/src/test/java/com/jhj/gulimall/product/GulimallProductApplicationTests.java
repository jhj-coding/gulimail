package com.jhj.gulimall.product;


import com.jhj.gulimall.product.service.BrandService;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.codec.digest.Md5Crypt;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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


    @Test
    public void md5Test() {

        //org.apache.commons.codec.digest
        // 由于抗修改性 所以可以暴力 破解 彩虹表 所以MD5不能进行直接加密
        DigestUtils.md5Hex("123456");
        // 盐值加密:随机值 格式 $1$+8位字符
        //比如加上当前系统时间  为了后续验证则需要将系统时间也保存 验证时再次加密对比即可
        Md5Crypt.md5Crypt("123456".getBytes(),"$1$88888888");

        // 密码编码器 springsecurity 不用我们再存储盐值，匹配会自动解析出盐值
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        //编码 每次得到的不同
        String encode = bCryptPasswordEncoder.encode("123456");
        //验证匹配
        bCryptPasswordEncoder.matches("123456",encode);

    }


}
