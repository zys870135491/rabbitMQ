package com.zys.service;

import com.alibaba.fastjson.JSONObject;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * 采用应答机制
 */
@Service
public class ProducerConfirmService implements RabbitTemplate.ConfirmCallback, RabbitTemplate.ReturnCallback {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void sendConfirm(String orderId){


        JSONObject jsonObject = new JSONObject();
        jsonObject.put("orderId",orderId);

        String jsonString = jsonObject.toJSONString();
        Message message = MessageBuilder.withBody(jsonString.getBytes()).setContentType(MessageProperties.CONTENT_TYPE_JSON)
                .setContentEncoding("utf-8").setMessageId(UUID.randomUUID() + "").build();
        this.rabbitTemplate.setMandatory(true);
        this.rabbitTemplate.setConfirmCallback(this);
        this.rabbitTemplate.setReturnCallback(this);
        CorrelationData correlationData = new CorrelationData(orderId);

        this.rabbitTemplate.convertAndSend("order_exchange","order_routing_key",message,correlationData);

    }

    /**
     * 通过实现 ConfirmCallback 接口，消息发送到 Broker 后触发回调，确认消息是否到达 Broker 服务器，
     * 也就是只确认是否正确到达 Exchange 中
     * @param correlationData
     * @param ack
     * @param cause
     */
    @Override
    public void confirm(CorrelationData correlationData, boolean ack, String cause) {
        System.out.println("CallBackConfirm orderId: " + correlationData.getId());

        if(ack) {
            System.out.println("CallBackConfirm 投递成功！");
        }else {
            System.out.println("CallBackConfirm 投递失败！");
        }

        if(cause!=null) {
            System.out.println("CallBackConfirm Cause: " + cause);
        }
    }

    /**
     * 通过实现 ReturnCallback 接口，启动消息失败返回，比如路由不到队列时触发回调
     * @param message
     * @param replyCode
     * @param replyText
     * @param exchange
     * @param routingKey
     */
    @Override
    public void returnedMessage(Message message, int replyCode, String replyText, String exchange, String routingKey) {
        System.out.println("return--message:"+new String(message.getBody())+
                ",replyCode:"+replyCode+",replyText:"+replyText+",exchange:"+exchange+",routingKey:"+routingKey);
    }
}
