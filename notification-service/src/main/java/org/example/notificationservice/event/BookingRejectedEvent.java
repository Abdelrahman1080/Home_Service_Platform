package org.example.notificationservice.event;

import lombok.Data;

@Data
public class BookingRejectedEvent {
    private Long customerId;
    private String reason;
}
