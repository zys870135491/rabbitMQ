package com.zys.config;


import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class RabbitmqConfig {


    public static final String FANOUT_EMAIL_QUEUE = "fanout_email_queue";
    public static final String FANOUT_Exchange = "fanoutExchange";

    /**
     * 定义死信队列的相关信息
     * @return
     */
    public static final String deadQueueName = "dead_queue";
    public static final String deadRoutingKey = "dead_routing_key";
    public static final String deadExchangeName = "dead_exchange";

    /**
     * 死信队列 交换机标识
     */
    public static final String DEAD_LETTER_QUEUE_KEY = "x-dead-letter-exchange";

    /**
     * 死信队列交换机绑定键标识符
     */
    public static final String DEAD_LETTER_ROUTING_KEY = "x-dead-letter-routing-key";


    public static final String ORDER_QUEUE = "order_queue";
    public static final String ORDER_Exchange = "order_exchange";
    public static final String ORDER_ROUTING_KEY = "order_routing_key";


    /**
     * arguments：
     * 队列中的消息什么时候会自动被删除？
     *
     * Message TTL(x-message-ttl)：设置队列中的所有消息的生存周期(统一为整个队列的所有消息设置生命周期), 也可以在发布消息的时候单独为某个消息指定剩余生存时间,单位毫秒, 类似于redis中的ttl，生存时间到了，消息会被从队里中删除，注意是消息被删除，而不是队列被删除， 特性Features=TTL, 单独为某条消息设置过期时间AMQP.BasicProperties.Builder properties = new AMQP.BasicProperties().builder().expiration(“6000”);
     * channel.basicPublish(EXCHANGE_NAME, “”, properties.build(), message.getBytes(“UTF-8”));
     *
     * Auto Expire(x-expires): 当队列在指定的时间没有被访问(consume, basicGet, queueDeclare…)就会被删除,Features=Exp
     *
     * Max Length(x-max-length): 限定队列的消息的最大值长度，超过指定长度将会把最早的几条删除掉， 类似于mongodb中的固定集合，例如保存最新的100条消息, Feature=Lim
     *
     * Max Length Bytes(x-max-length-bytes): 限定队列最大占用的空间大小， 一般受限于内存、磁盘的大小, Features=Lim B
     *
     * Dead letter exchange(x-dead-letter-exchange)： 当队列消息长度大于最大长度、或者过期的等，将从队列中删除的消息推送到指定的交换机中去而不是丢弃掉,Features=DLX
     *
     * Dead letter routing key(x-dead-letter-routing-key)：将删除的消息推送到指定交换机的指定路由键的队列中去, Feature=DLK
     *
     * Maximum priority(x-max-priority)：优先级队列，声明队列时先定义最大优先级值(定义最大值一般不要太大)，在发布消息的时候指定该消息的优先级， 优先级更高（数值更大的）的消息先被消费,
     *
     * Lazy mode(x-queue-mode=lazy)： Lazy Queues: 先将消息保存到磁盘上，不放在内存中，当消费者开始消费的时候才加载到内存中
     * @return
     */
    // 定义队列
    @Bean
    public Queue fanoutEmailQueue(){
        //return new Queue(FANOUT_EMAIL_QUEUE);
        //邮件队列绑定死信交换机和死信队列
        Map<String, Object> args = new HashMap<>();
        args.put(DEAD_LETTER_QUEUE_KEY,deadExchangeName);
        args.put(DEAD_LETTER_ROUTING_KEY,deadRoutingKey);
        //args.put("x-message-ttl",2000);
        //args.put("x-max-length",3);
        Queue queue = new Queue(FANOUT_EMAIL_QUEUE, true, false, false, args);
        return queue;
    }

    // 定义交换机
    @Bean
    public FanoutExchange fanoutExchange(){
        return new FanoutExchange(FANOUT_Exchange);
    }

    //交换机和队列进行绑定
    public Binding bindingExchageEamil(Queue fanoutEmailQueue, FanoutExchange fanoutExchange){
        return BindingBuilder.bind(fanoutEmailQueue).to(fanoutExchange);
    }

    /**
     * 创建邮件的死信队列
     * @return
     */
    // 创建死信队列
    @Bean
    public Queue deadQueue(){
        return  new Queue(deadQueueName,true);
    }

    // 创建死信交换机
    @Bean
    public DirectExchange deadExchange(){
        return new DirectExchange(deadExchangeName);
    }

    // 死信队列和死信交换机绑定
    @Bean
    public Binding bindingDeadExchange(Queue deadQueue,DirectExchange deadExchange){
        return BindingBuilder.bind(deadQueue).to(deadExchange).with(deadRoutingKey);
    }

    /**
     * 创建订单确认机制
     */
    @Bean
    public Queue orderQueue(){

        return  new Queue(ORDER_QUEUE,true);
    }

    @Bean
    public DirectExchange orderExchange(){
        return new DirectExchange(ORDER_Exchange);
    }

    @Bean
    public Binding bindingOrderExchange(Queue orderQueue,DirectExchange orderExchange){
        return BindingBuilder.bind(orderQueue).to(orderExchange).with(ORDER_ROUTING_KEY);
    }


}
