package com.zys.springbootMQ.workQueue;

import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class WorkCustomer {


    @RabbitListener(queuesToDeclare =@Queue("work"))
    public void receive1(String message){
        System.out.println("消费者===============》"+message);
    }

    @RabbitListener(queuesToDeclare =@Queue("work"))
    public void receive2(String message){
        System.out.println("消费者2===============》"+message);
    }

}
