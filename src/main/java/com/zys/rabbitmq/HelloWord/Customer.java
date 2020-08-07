package com.zys.rabbitmq.HelloWord;

import com.rabbitmq.client.*;
import com.zys.rabbitmq.utils.RabbitmqUtil;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Customer {

    public static void main(String[] args) throws IOException, TimeoutException {
       /* // 创建连接工厂
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("192.168.42.130");
        connectionFactory.setPort(5672);
        connectionFactory.setVirtualHost("/ems");
        connectionFactory.setUsername("ems");
        connectionFactory.setPassword("123456");

        // 获取连接对象
        Connection connection = connectionFactory.newConnection();*/
        Connection connection = RabbitmqUtil.getConnection();
        // 获取连接通道
        Channel channel = connection.createChannel();
        // 通道绑定对应的消息队列
        channel.queueDeclare("hello",false,false,false,null);

        String consume = channel.basicConsume("hello", true, new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                System.out.println("=================="+new String(body));
            }
        });

        //一般不关闭连接，这样消费者可以一直监听某个队列进行消费
        //channel.close();
        //connection.close();

    }
}
