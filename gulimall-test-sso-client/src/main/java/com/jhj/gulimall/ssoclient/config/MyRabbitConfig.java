package com.jhj.gulimall.ssoclient.config;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;

@Configuration
public class MyRabbitConfig {
    @Resource
    RabbitTemplate rabbitTemplate;
    public MyRabbitConfig(RabbitTemplate rabbitTemplate){
        this.rabbitTemplate=rabbitTemplate;
        initRabbitTemplate();
    }
    /**
     * 在当前对象创建完后再调用该方法
     */
    public void initRabbitTemplate(){
        //消息抵达broker的确认回调 都触发
        rabbitTemplate.setConfirmCallback(new RabbitTemplate.ConfirmCallback() {
            /**
             *
             * @param correlationData 当前消息的唯一关联数据
             * @param b 消息是否成功收到 只要消息抵达Broker 就为true
             * @param s 失败原因
             */
            @Override
            public void confirm(CorrelationData correlationData, boolean b, String s) {

            }
        });

        //消息抵达队列的确认回调 失败时触发
        rabbitTemplate.setReturnCallback(new RabbitTemplate.ReturnCallback(){
            /**
             * 只要消息没有投递给指定的队列，就会触发这个失败回调
             * @param message 投递失败的消息信息
             * @param i 回复状态码
             * @param s 回复的文本内容
             * @param s1 当时发送的交换机
             * @param s2 用的路由键
             */
            @Override
            public void returnedMessage(Message message, int i, String s, String s1, String s2) {

            }
        });
    }
}
