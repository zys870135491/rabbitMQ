package com.zys.rabbitmq.Direct;

import com.rabbitmq.client.*;
import com.zys.rabbitmq.utils.RabbitmqUtil;

import java.io.IOException;
import java.util.HashMap;

public class Cusomer2 {

    public static void main(String[] args) throws IOException {
        Connection connection = RabbitmqUtil.getConnection();
        Channel channel = connection.createChannel();
        String exchangeName = "logs_direct";
        // 将通道声明指定给交换机
        // 参数1：交换机名称  参数2：交换机类型  direct：Routing 之订阅模型（直连）
        channel.exchangeDeclare(exchangeName,"direct");
        // 临时队列
        String queueName = channel.queueDeclare().getQueue();
        // 临时队列和交换机绑定
        // 参数1：队列 参数2：交换机 参数3：路由key
        channel.queueBind(queueName,exchangeName,"error");
        channel.queueBind(queueName,exchangeName,"info");
        channel.queueBind(queueName,exchangeName,"waring");
        channel.basicConsume(queueName,true,new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                System.out.println("消费者1："+new String(body));
            }
        });

    }
}
