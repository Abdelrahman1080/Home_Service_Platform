package org.example.notificationservice.event;

import lombok.Data;

@Data
public class PaymentRollbackEvent {
    private Long userId;
    private Double amount;
}
