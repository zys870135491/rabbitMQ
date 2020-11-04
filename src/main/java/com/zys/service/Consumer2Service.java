package com.zys.service;

import com.alibaba.fastjson.JSONObject;
import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * 死信队列消费者
 */
@Service
public class Consumer2Service {

     //@RabbitListener(queues = "dead_queue")
     public void process(Message message, @Headers Map<String, Object> headers, Channel channel) throws Exception {

         //判断唯一Id是否被消费，①存入日志表中每次都查询一次是否被消费 ②存入缓存中每次都查一下是否被消费
         String messageId = message.getMessageProperties().getMessageId();

         String msg = new String(message.getBody(), "UTF-8");
         System.out.println("邮件消费者获取生产者消息2" + "messageId2:" + messageId + ",消息内容2:" + msg);
         JSONObject jsonObject = JSONObject.parseObject(msg);
         String email = jsonObject.getString("email");
         String timestamp = jsonObject.getString("email");

         // 模拟第三方接口失败，就会触发重试机制
         JSONObject result = jsonObject;
         if(result == null){
             throw new Exception("调用第三方接口失败");
         }

         // 手动ack
         Long deliveryTag = (Long) headers.get(AmqpHeaders.DELIVERY_TAG);
         // 手动签收
         channel.basicAck(deliveryTag, false);


     }

}
