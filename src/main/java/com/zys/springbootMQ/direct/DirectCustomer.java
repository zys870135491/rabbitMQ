package com.zys.springbootMQ.direct;

import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class DirectCustomer {

    @RabbitListener(bindings = @QueueBinding(
                value = @Queue, // 不起名字就是临时队列
                exchange = @Exchange(name = "directs",type = "direct"),
                key = {"error"}
    ))
    public void receive1(String message){
        System.out.println("消费者1==============》" + message);
    }

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue, // 不起名字就是临时队列
            exchange = @Exchange(name = "directs",type = "direct"),
            key = {"error","info","waring"}
    ))
    public void receive2(String message){
        System.out.println("消费者2==============》" + message);
    }
}
