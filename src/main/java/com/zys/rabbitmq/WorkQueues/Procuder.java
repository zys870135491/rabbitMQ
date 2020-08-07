package com.zys.rabbitmq.WorkQueues;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.zys.rabbitmq.utils.RabbitmqUtil;

import java.io.IOException;

public class Procuder {

    /**
     * 最简单的第二种模型（`Work queues`）
     */
    public static void main(String[] args) throws IOException {
        Connection connection = RabbitmqUtil.getConnection();
        Channel channel = connection.createChannel();
        channel.queueDeclare("workQueues",true,false,false,null);

        for(int i =20 ;i<=40;i++){
            byte[] bodys = ("workQueues" + i).getBytes();
            channel.basicPublish("","workQueues",null,bodys);
        }
        RabbitmqUtil.closeConnectionAndChanel(channel,connection);
    }

}
