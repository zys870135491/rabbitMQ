package com.zys.service;

import com.alibaba.fastjson.JSONObject;
import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class ConsumerConfirmService {

    @RabbitListener(queues = "order_queue")
    public void process(Message message, @Headers Map<String, Object> headers, Channel channel) throws Exception {

        //判断唯一Id是否被消费，①存入日志表中每次都查询一次是否被消费 ②存入缓存中每次都查一下是否被消费
        String messageId = message.getMessageProperties().getMessageId();

        String msg = new String(message.getBody(), "UTF-8");

        JSONObject jsonObject = JSONObject.parseObject(msg);
        String orderId = jsonObject.getString("orderId");
        System.out.println("消费了orderId:"+orderId);

        // 手动ack
        Long deliveryTag = (Long) headers.get(AmqpHeaders.DELIVERY_TAG);
        // 手动签收
        channel.basicAck(deliveryTag, false);
    }
}
