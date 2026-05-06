package org.example.notificationservice.messaging;

import org.springframework.amqp.core.*;
import org.springframework.amqp.support.converter.JacksonJsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    public static final String EXCHANGE = "booking.exchange";

    public static final String BOOKING_CREATED_QUEUE = "booking.created.queue";
    public static final String BOOKING_REJECTED_QUEUE = "booking.rejected.queue";
    public static final String PAYMENT_QUEUE = "payment.queue";

    @Bean
    public TopicExchange exchange() {
        return new TopicExchange(EXCHANGE);
    }

    @Bean
    public Queue bookingCreatedQueue() {
        return QueueBuilder.durable(BOOKING_CREATED_QUEUE).build();
    }

    @Bean
    public Queue bookingRejectedQueue() {
        return QueueBuilder.durable(BOOKING_REJECTED_QUEUE).build();
    }

    @Bean
    public Queue paymentQueue() {
        return QueueBuilder.durable(PAYMENT_QUEUE).build();
    }

    @Bean
    public Binding bindCreated() {
        return BindingBuilder.bind(bookingCreatedQueue())
                .to(exchange())
                .with("booking.created");
    }

    @Bean
    public Binding bindRejected() {
        return BindingBuilder.bind(bookingRejectedQueue())
                .to(exchange())
                .with("booking.rejected");
    }

    @Bean
    public Binding bindPayment() {
        return BindingBuilder.bind(paymentQueue())
                .to(exchange())
                .with("payment.*");
    }

    @Bean
    public MessageConverter converter() {
        return new JacksonJsonMessageConverter();
    }
}
