package com.zys.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RabbitmqTest {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Test
    public void RabbitMQTopicTest(){
        String routeKey ="proce.12.21";
        rabbitTemplate.convertAndSend("topics",routeKey,"routeKey["+routeKey+"]");
    }

    @Test
    public void RabbitMQDirectTest(){
        String routeKey ="info";
        rabbitTemplate.convertAndSend("directs",routeKey,"routeKey["+routeKey+"]");
    }


    @Test
    public void RabbitMQFanoutTest(){
        rabbitTemplate.convertAndSend("logs","","Fanout模型的数据");
    }

    @Test
    public void RabbitMQWorkTest(){
       for(int i =0;i<10;i++){
           rabbitTemplate.convertAndSend("work","work模型"+i);
       }
    }

    @Test
    public void RabbitMQHelloTest(){
        rabbitTemplate.convertAndSend("hello","hello world MQ");
    }

}
