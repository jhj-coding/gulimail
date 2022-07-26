package com.jhj.gulimall.order;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@RunWith(SpringRunner.class)
@SpringBootTest
public class GulimallOrderApplicationTests {

    @Resource
    AmqpAdmin amqpAdmin;

    /**
     * 使用AmpqAdmin进行创建
     */
    @Test
    public void test01(){
        System.out.println(1);
    }
}
