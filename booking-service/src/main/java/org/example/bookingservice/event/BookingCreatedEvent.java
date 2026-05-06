package org.example.bookingservice.event;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BookingCreatedEvent {
    private Long bookingId;
    private Long customerId;
    private Long providerId;
}
