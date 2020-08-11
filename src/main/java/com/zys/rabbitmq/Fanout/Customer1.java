package com.zys.rabbitmq.Fanout;

import com.rabbitmq.client.*;
import com.zys.rabbitmq.utils.RabbitmqUtil;

import java.io.IOException;

public class Customer1 {

    public static void main(String[] args) throws IOException {
        Connection connection = RabbitmqUtil.getConnection();
        Channel channel = connection.createChannel();

        // 将通道声明指定给交换机
        // 参数1：交换机名称  参数2：交换机类型   fanout 广播
        channel.exchangeDeclare("logs", "fanout");

        //临时队列名字
        String queueName = channel.queueDeclare().getQueue();
        // 将队列和交换机绑定
        //参数1：队列，参数2：交换机 参数3：路由key
        channel.queueBind(queueName,"logs","");

        channel.basicConsume(queueName,true,new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                System.out.println("消费者1: "+new String(body));
            }
        });

    }
}
