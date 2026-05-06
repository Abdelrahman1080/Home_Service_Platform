package org.example.bookingservice.event;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PaymentSuccessEvent {
    private Long userId;
    private Double amount;
}
