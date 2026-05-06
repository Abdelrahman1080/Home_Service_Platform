package org.example.notificationservice.event;

import lombok.Data;

@Data
public class PaymentSuccessEvent {
    private Long userId;
    private Double amount;
}
