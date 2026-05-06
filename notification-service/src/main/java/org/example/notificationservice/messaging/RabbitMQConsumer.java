package org.example.notificationservice.messaging;

import lombok.RequiredArgsConstructor;
import org.example.notificationservice.event.*;
import org.example.notificationservice.service.NotificationService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RabbitMQConsumer {

    private final NotificationService service;

    @RabbitListener(queues = RabbitMQConfig.BOOKING_CREATED_QUEUE)
    public void handleBookingCreated(BookingCreatedEvent event) {

        service.notifyUser(
                event.getCustomerId(),
                "Booking confirmed. ID: " + event.getBookingId()
        );

        service.notifyUser(
                event.getProviderId(),
                "New booking received. ID: " + event.getBookingId()
        );
    }

    @RabbitListener(queues = RabbitMQConfig.BOOKING_REJECTED_QUEUE)
    public void handleBookingRejected(BookingRejectedEvent event) {

        service.notifyUser(
                event.getCustomerId(),
                "Booking rejected: " + event.getReason()
        );
    }

    @RabbitListener(queues = RabbitMQConfig.PAYMENT_QUEUE)
    public void handlePayment(Object event) {

        if (event instanceof PaymentSuccessEvent success) {
            service.notifyUser(
                    success.getUserId(),
                    "Payment successful: $" + success.getAmount()
            );
        }

        else if (event instanceof PaymentRollbackEvent rollback) {
            service.notifyUser(
                    rollback.getUserId(),
                    "Payment rolled back: $" + rollback.getAmount()
            );
        }
    }
}
