package com.zys.rabbitmq.utils;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;



public class RabbitmqUtil {

    private static ConnectionFactory connectionFactory;
    static {
        //重量级代码，类加载时只执行一次
        // 创建连接mq的连接工厂对象
         connectionFactory = new ConnectionFactory();
        // 设置连接rabbitmq主机
        connectionFactory.setHost("192.168.42.130");
        // 设置端口号
        connectionFactory.setPort(5672);
        // 设置哪个虚拟机
        connectionFactory.setVirtualHost("/ems");
        connectionFactory.setUsername("ems");
        connectionFactory.setPassword("123456");
    }

    public static Connection getConnection(){
        try {
            // 获取连接对象
            return connectionFactory.newConnection();
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public static void closeConnectionAndChanel(Channel channel, Connection con){
        try {
            if(channel !=null){
                channel.close();
            }
            if(con !=null){
                con.close();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
