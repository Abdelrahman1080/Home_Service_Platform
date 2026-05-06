package org.example.bookingservice.messaging;

import org.springframework.amqp.core.*;
import org.springframework.amqp.support.converter.JacksonJsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
@Configuration
public class RabbitMQConfig {

    public static final String BOOKING_EXCHANGE = "booking.exchange";

    public static final String BOOKING_CREATED_QUEUE = "booking.created.queue";
    public static final String BOOKING_REJECTED_QUEUE = "booking.rejected.queue";
    public static final String PAYMENT_QUEUE = "payment.queue";

    @Bean
    public TopicExchange bookingExchange() {
        return new TopicExchange(BOOKING_EXCHANGE);
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
    public Binding bindBookingCreated() {
        return BindingBuilder.bind(bookingCreatedQueue())
                .to(bookingExchange())
                .with("booking.created");
    }

    @Bean
    public Binding bindBookingRejected() {
        return BindingBuilder.bind(bookingRejectedQueue())
                .to(bookingExchange())
                .with("booking.rejected");
    }

    @Bean
    public Binding bindPayment() {
        return BindingBuilder.bind(paymentQueue())
                .to(bookingExchange())
                .with("payment.*");
    }

    @Bean
    public MessageConverter jsonConverter() {
        return new JacksonJsonMessageConverter();
    }
}