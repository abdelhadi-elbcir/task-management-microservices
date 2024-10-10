package com.learnrmq.rabbitMq;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class MessageListener {

    @RabbitListener(queues = RabbitMQConfig.TASK_EVENTS_QUEUE)
    public void listener(Object message) {
        System.out.println(message);
    }

}