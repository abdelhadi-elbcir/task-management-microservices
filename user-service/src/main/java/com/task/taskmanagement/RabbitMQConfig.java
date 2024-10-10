package com.task.taskmanagement;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableRabbit
public class RabbitMQConfig {

    public static final String USER_EVENTS_QUEUE = "user.events.queue";
    public static final String USER_EXCHANGE = "user.exchange";
    public static final String USER_ROUTING_KEY = "user.routing.key";

    // Define the Queue
    @Bean
    public Queue userEventsQueue() {
        return new Queue(USER_EVENTS_QUEUE, true);  // durable queue
    }

    // Define the Exchange
    @Bean
    public TopicExchange userExchange() {
        return new TopicExchange(USER_EXCHANGE);
    }

    // Bind the Queue and Exchange with a Routing Key
    @Bean
    public Binding USERBinding(Queue userEventsQueue, TopicExchange userExchange) {
        return BindingBuilder
                .bind(userEventsQueue)
                .to(userExchange)
                .with(USER_ROUTING_KEY);
    }
}