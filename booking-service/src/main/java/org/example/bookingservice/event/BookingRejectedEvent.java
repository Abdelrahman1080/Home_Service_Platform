package org.example.bookingservice.event;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BookingRejectedEvent {
    private Long customerId;
    private String reason;
}
