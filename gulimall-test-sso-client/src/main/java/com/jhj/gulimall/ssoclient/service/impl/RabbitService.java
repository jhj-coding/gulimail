package com.jhj.gulimall.ssoclient.service.impl;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class RabbitService {

    /**
     * 接受消息 必须开启EnableRabbit
     *
     * RabbitListener 监听注解 声明队列数组 （类加方法）
     * RabbitHandler (方法) 用于重载接受每一个队列的不同类型的消息并处理
     * 比如@RabbitListener(queues = {"test"})在类上
     * RabbitHandler 一个接受Integer 一个接受String
     *
     *
     * Message message 原生 消息头+消息体
     * String s 当时发送的消息体内容 类型
     * Channel channel 当前传输通道
     *
     * Queue 很多人可以监听，只要收到消息，队列删除信息，而且只能有一个收到此消息
     * 只有当消息处理完成后才能接收到下一个消息
     */
    @RabbitListener(queues = {"test"})
    public void recieveMessage(Message message, String s, Channel channel){
        message.getBody();
        System.out.println(s);
        try {
            // 第二个参数代表 是否批量回复 确认成功回复 收货
            channel.basicAck(message.getMessageProperties().getDeliveryTag(),false);
            // 第二个参数代表 是否批量回复
            // 第三个参数代表 是否重新放到queue中 退货 确认失败回复 false 丢弃 true 重新入队
            channel.basicNack(message.getMessageProperties().getDeliveryTag(),false,false);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @RabbitListener(queues = {"order.release.order.queue"})
    public void listener(Message message, String s, Channel channel){
        message.getBody();
        System.out.println(s);
        try {
            // 第二个参数代表 是否批量回复 确认成功回复 收货
            channel.basicAck(message.getMessageProperties().getDeliveryTag(),false);
            // 第二个参数代表 是否批量回复
            // 第三个参数代表 是否重新放到queue中 退货 确认失败回复 false 丢弃 true 重新入队
            //channel.basicNack(message.getMessageProperties().getDeliveryTag(),false,false);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }



}
