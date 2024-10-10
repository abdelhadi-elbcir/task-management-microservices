package com.learnrmq.rabbitMq;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableRabbit
public class RabbitMQConfig {

    public static final String TASK_EVENTS_QUEUE = "task.events.queue";
    public static final String TASK_EXCHANGE = "task.exchange";
    public static final String TASK_ROUTING_KEY = "task.routing.key";

    // Define the Queue
    @Bean
    public Queue taskEventsQueue() {
        return new Queue(TASK_EVENTS_QUEUE, true);  // durable queue
    }

    // Define the Exchange
    @Bean
    public TopicExchange taskExchange() {
        return new TopicExchange(TASK_EXCHANGE);
    }

    // Bind the Queue and Exchange with a Routing Key
    @Bean
    public Binding taskBinding(Queue taskEventsQueue, TopicExchange taskExchange) {
        return BindingBuilder
                .bind(taskEventsQueue)
                .to(taskExchange)
                .with(TASK_ROUTING_KEY);
    }
}