package com.zys.rabbitmq.HelloWord;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.MessageProperties;
import com.zys.rabbitmq.utils.RabbitmqUtil;
import org.junit.Test;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Procuder {

    /**
     * 最简单的第一种模型（直连）
     */
    @Test
    public void testSendMessage() throws IOException, TimeoutException {
/*        // 创建连接mq的连接工厂对象
        ConnectionFactory connectionFactory = new ConnectionFactory();
        // 设置连接rabbitmq主机
        connectionFactory.setHost("192.168.42.130");
        // 设置端口号
        connectionFactory.setPort(5672);
        // 设置哪个虚拟机
        connectionFactory.setVirtualHost("/ems");
        connectionFactory.setUsername("ems");
        connectionFactory.setPassword("123456");

        // 获取连接对象
        Connection connection = connectionFactory.newConnection();*/
        Connection connection = RabbitmqUtil.getConnection();
        // 获取连接通道
        Channel channel = connection.createChannel();

        /**
         * 通道绑定对应的消息队列
         '参数1':用来声明通道对应的队列
         '参数2':用来指定是否持久化队列
         '参数3':用来指定是否独占队列
         '参数4':用来指定是否自动删除队列
         '参数5':对队列的额外配置
         */
        channel.queueDeclare("hello",true,false,false,null);

        // 发布消息
        // 参数1：交换机名称（我们使用的第一种模型，直接把消息给到了队列，不需要交换机）
        // 参数2：队列名称 参数3：传递参数额外设置 参数4：消息具体内容
        byte[] bodys = "hello rabbitmq".getBytes();
        channel.basicPublish("","hello", MessageProperties.PERSISTENT_TEXT_PLAIN,bodys);

        /*channel.close();
        connection.close();*/
        RabbitmqUtil.closeConnectionAndChanel(channel,connection);
    }

}
