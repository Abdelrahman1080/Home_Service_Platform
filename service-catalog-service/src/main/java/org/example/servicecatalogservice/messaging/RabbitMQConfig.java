package org.example.servicecatalogservice.messaging;

import org.springframework.amqp.core.*;
import org.springframework.amqp.support.converter.JacksonJsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.amqp.support.converter.SimpleMessageConverter;

@Configuration
public class RabbitMQConfig {

    public static final String EXCHANGE = "catalog.events";

    public static final String OFFER_CREATED_QUEUE = "offer.created.queue";
    public static final String OFFER_UPDATED_QUEUE = "offer.updated.queue";

    public static final String OFFER_CREATED_ROUTING_KEY = "offer.created";
    public static final String OFFER_UPDATED_ROUTING_KEY = "offer.updated";


        @Bean
        public TopicExchange exchange() {
            return new TopicExchange(EXCHANGE);
        }

        @Bean
        public Queue offerCreatedQueue() {
            return QueueBuilder.durable(OFFER_CREATED_QUEUE).build();
        }

        @Bean
        public Queue offerUpdatedQueue() {
            return QueueBuilder.durable(OFFER_UPDATED_QUEUE).build();
        }

        @Bean
        public Binding bindingCreated() {
            return BindingBuilder.bind(offerCreatedQueue())
                    .to(exchange())
                    .with(OFFER_CREATED_ROUTING_KEY);
        }

        @Bean
        public Binding bindingUpdated() {
            return BindingBuilder.bind(offerUpdatedQueue())
                    .to(exchange())
                    .with(OFFER_UPDATED_ROUTING_KEY);
        }

        // 🔥 الحل الصحيح
        @Bean
        public MessageConverter jsonMessageConverter() {
            return new JacksonJsonMessageConverter();
        }

        // مهم: لو عايز تحدد اسم الـ Type للـ Deserialization
        // @Bean
        // public MessageConverter jsonMessageConverter() {
        //     Jackson2JsonMessageConverter converter = new Jackson2JsonMessageConverter();
        //     converter.setTypeMapper(...); // اختياري
        //     return converter;
        // }
    }