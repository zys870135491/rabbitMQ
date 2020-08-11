package com.zys.rabbitmq.Direct;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.zys.rabbitmq.utils.RabbitmqUtil;

import java.io.IOException;

public class Procuder {

    public static void main(String[] args) throws IOException {
        // 获取连接对象
        Connection connection = RabbitmqUtil.getConnection();
        Channel channel = connection.createChannel();

        String exchangeName = "logs_direct";
        // 将通道声明指定给交换机
        // 参数1：交换机名称  参数2：交换机类型  direct：Routing 之订阅模型（直连）
        channel.exchangeDeclare(exchangeName,"direct");
        String routeKey ="error";
        //发布消息
        //参数1：交换机名字 参数2：路由名字，参数3： 参数4：body
        channel.basicPublish(exchangeName,routeKey,null,("这是direct,routeKey:[" + routeKey + "]").getBytes());
        RabbitmqUtil.closeConnectionAndChanel(channel,connection);
    }

}
