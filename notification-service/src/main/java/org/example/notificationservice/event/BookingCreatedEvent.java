package org.example.notificationservice.event;

import lombok.Data;

@Data
public class BookingCreatedEvent {
    private Long bookingId;
    private Long customerId;
    private Long providerId;
}
