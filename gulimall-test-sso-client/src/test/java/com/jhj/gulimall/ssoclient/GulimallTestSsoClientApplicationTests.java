package com.jhj.gulimall.ssoclient;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@RunWith(SpringRunner.class)
@SpringBootTest
public class GulimallTestSsoClientApplicationTests {

    @Resource
    AmqpAdmin amqpAdmin;
    @Resource
    RabbitTemplate rabbitTemplate;
    /**
     * 使用AmpqAdmin进行创建交换机
     */
    @Test
    public void createExchange() {
        DirectExchange directExchange = new DirectExchange("test",true,false);
        amqpAdmin.declareExchange(directExchange);
    }

    /**
     * 创建队列
     */
    @Test
    public void createQueue() {
        //exclusive 是否排它 指的是只有一个能连接
        Queue queue = new Queue("test",true,false,false);
        amqpAdmin.declareQueue(queue);
    }

    /**
     * 创建绑定
     */
    @Test
    public void createBinding() {
        //exclusive 是否排它 指的是只有一个能连接
        Binding binding = new Binding("test", Binding.DestinationType.QUEUE,"test","test",null);
        amqpAdmin.declareBinding(binding);
    }

    /**
     * 发送消息
     */
    @Test
    public void sendMessage(){
        rabbitTemplate.convertAndSend("order-event-exchange","order.create.order","test");
    }


}
