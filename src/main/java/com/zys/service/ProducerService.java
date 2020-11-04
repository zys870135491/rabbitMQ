package com.zys.service;

import com.alibaba.fastjson.JSONObject;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ProducerService{

    @Autowired
    private AmqpTemplate amqpTemplate;

    public void send(String queueName){

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("email","644655");
        jsonObject.put("timestamp",System.currentTimeMillis());
        String jsonString = jsonObject.toJSONString();
        Message message = MessageBuilder.withBody(jsonString.getBytes()).setContentType(MessageProperties.CONTENT_TYPE_JSON)
                .setContentEncoding("utf-8").setMessageId(UUID.randomUUID() + "").build();
        amqpTemplate.convertAndSend(queueName,message);
    }




}
