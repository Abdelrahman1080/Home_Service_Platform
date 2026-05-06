package org.example.bookingservice.messaging;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
public class RabbitMQProducer {

    private final RabbitTemplate rabbitTemplate;

    public RabbitMQProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void send(String routingKey, Object message) {

        System.out.println("[RabbitMQ] Event: " + routingKey);

        rabbitTemplate.convertAndSend(
                RabbitMQConfig.BOOKING_EXCHANGE,
                routingKey,
                message
        );
    }
}
