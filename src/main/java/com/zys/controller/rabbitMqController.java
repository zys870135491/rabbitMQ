package com.zys.controller;

import com.zys.service.ProducerConfirmService;
import com.zys.service.ProducerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/rabbitMq")
public class rabbitMqController {

    @Autowired
    private ProducerService producerService;

    @Autowired
    private ProducerConfirmService producerConfirmService;

    @RequestMapping(value = "test" ,method = RequestMethod.GET)
    @ResponseBody
    public String test(@RequestParam String queueName){

        try {
            producerService.send(queueName);
            return "success";
        }catch (Exception e){
            e.printStackTrace();
            return "fail";
        }
    }

    @RequestMapping(value = "order" ,method = RequestMethod.GET)
    @ResponseBody
    public String order(@RequestParam String orderId){

        try {
            producerConfirmService.sendConfirm(orderId);
            return "success";
        }catch (Exception e){
            e.printStackTrace();
            return "fail";
        }
    }

}
