package com.task.taskmanagement;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserEventPublisher {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void publishUserCreatedEvent(User user) {
        rabbitTemplate.convertAndSend(RabbitMQConfig.USER_EVENTS_QUEUE, "User Created: " + user.getId());
    }

    public void publishUserUpdatedEvent(User user) {
        rabbitTemplate.convertAndSend(RabbitMQConfig.USER_EVENTS_QUEUE, "User Updated: " + user.getId());
    }

    public void publishUserDeletedEvent(UUID userId) {
        rabbitTemplate.convertAndSend(RabbitMQConfig.USER_EVENTS_QUEUE, "User Deleted: " + userId);
    }
}