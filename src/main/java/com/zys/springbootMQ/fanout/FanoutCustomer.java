package com.zys.springbootMQ.fanout;

import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class FanoutCustomer {

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue, // 不起名字就是临时队列
            exchange = @Exchange(name = "logs",type = "fanout")
    ))
    public void revice1(String message){
        System.out.println("消费者1==============》"+message);
    }

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue, // 不起名字就是临时队列
            exchange = @Exchange(name = "logs",type = "fanout")
    ))
    public void revice2(String message){
        System.out.println("消费者2==============》"+message);
    }
}
