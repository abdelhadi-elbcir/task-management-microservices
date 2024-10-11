package com.learnrmq.rabbitMq;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;
import org.springframework.amqp.rabbit.annotation.RabbitListener;

@Component
public class MessageListener {

    private final SimpMessagingTemplate simpMessagingTemplate;

    @Autowired
    public MessageListener(SimpMessagingTemplate simpMessagingTemplate) {
        this.simpMessagingTemplate = simpMessagingTemplate;
    }

    @RabbitListener(queues = RabbitMQConfig.TASK_EVENTS_QUEUE)
    public void listener(String message) {
        System.out.println("Received from RabbitMQ: " + message);

        simpMessagingTemplate.convertAndSend("/topic/notifications", message);
    }
}
