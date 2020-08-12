package com.zys.rabbitmq.Topic;

import com.rabbitmq.client.*;
import com.zys.rabbitmq.utils.RabbitmqUtil;

import java.io.IOException;

public class Cusomer1 {

    /**
     * # 统配符
     * (star) can substitute for exactly one word.    匹配不多不少恰好1个词
     *  # (hash) can substitute for zero or more words.  匹配一个或多个词
     *  # 如:
     *  audit.#    匹配audit.irs.corporate或者 audit.irs 等
     *  audit.*   只能匹配 audit.irs
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        Connection connection = RabbitmqUtil.getConnection();
        Channel channel = connection.createChannel();
        String exchangeName = "topics";
        String routeKey = "users.#";
        // 将通道声明指定给交换机
        // 参数1：交换机名称  参数2：交换机类型  topic
        channel.exchangeDeclare(exchangeName,"topic");
        String queueName = channel.queueDeclare().getQueue();
        channel.queueBind(queueName,exchangeName,routeKey);
        channel.basicConsume(queueName,true,new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                System.out.println("消费者1："+new String(body));
            }
        });
    }
}
