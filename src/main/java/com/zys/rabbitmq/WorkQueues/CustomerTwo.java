package com.zys.rabbitmq.WorkQueues;

import com.rabbitmq.client.*;
import com.zys.rabbitmq.utils.RabbitmqUtil;

import java.io.IOException;

public class CustomerTwo {

    public static void main(String[] args) throws IOException {
        Connection connection = RabbitmqUtil.getConnection();
        final Channel channel = connection.createChannel();
        channel.basicQos(1);//每一次只能消费一个消息
        channel.queueDeclare("workQueues",true,false,false,null);
        //参数1:队列名称  参数2:消息自动确认 true  消费者自动向rabbitmq确认消息消费  false 不会自动确认消息
        channel.basicConsume("workQueues", false, new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("消费者2========================"+new String(body));
                //手动确认  参数1:手动确认消息标识  参数2:false 每次确认一个
                channel.basicAck(envelope.getDeliveryTag(),false);
            }
        });


    }

}
