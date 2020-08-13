package com.zys.springbootMQ.topic;

import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class TopicCustomer {

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue, // 不起名字就是临时队列
            exchange = @Exchange(name = "topics",type = "topic"),
            key = {"user.*"}
    ))
    public void receive1(String message){
        System.out.println("消费者1============》"+message);
    }

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue, // 不起名字就是临时队列
            exchange = @Exchange(name = "topics",type = "topic"),
            key = {"user.#","proce.#"}
    ))
    public void receive2(String message){
        System.out.println("消费者2============》"+message);
    }
}
