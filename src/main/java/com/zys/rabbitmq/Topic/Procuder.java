package com.zys.rabbitmq.Topic;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.zys.rabbitmq.utils.RabbitmqUtil;

import java.io.IOException;

public class Procuder {

    public static void main(String[] args) throws IOException {
        Connection connection = RabbitmqUtil.getConnection();
        Channel channel = connection.createChannel();
        String exchangeName = "topics";
        String routeKey = "users.save";
        // 将通道声明指定给交换机
        // 参数1：交换机名称  参数2：交换机类型   topic 广播
        channel.exchangeDeclare(exchangeName,"topic");
        //参数1：交换机名字 参数2：路由名字，参数3： 参数4：body
        channel.basicPublish(exchangeName,routeKey,null,("这是topic,routeKey:[" + routeKey + "]").getBytes());
        RabbitmqUtil.closeConnectionAndChanel(channel,connection);
    }

}
