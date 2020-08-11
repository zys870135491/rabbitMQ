package com.zys.rabbitmq.Fanout;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.zys.rabbitmq.utils.RabbitmqUtil;

import java.io.IOException;

public class Procuder {

    public static void main(String[] args) throws IOException {
        // 获取连接对象
        Connection connection = RabbitmqUtil.getConnection();
        Channel channel = connection.createChannel();

        // 将通道声明指定给交换机
        // 参数1：交换机名称  参数2：交换机类型   fanout 广播
        channel.exchangeDeclare("logs","fanout");

        byte[] bodys = "fanout rabbitmq".getBytes();
        channel.basicPublish("logs","", null,bodys);
        RabbitmqUtil.closeConnectionAndChanel(channel,connection);
    }

}
