package com.zys.rabbitmq;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class rabbitmqApplication {

    public static void main(String[] args) {
        System.out.println("............rabbitmqApplication start........");
        SpringApplication.run(rabbitmqApplication.class,args);
        System.out.println("............rabbitmqApplication end........");
    }
}
