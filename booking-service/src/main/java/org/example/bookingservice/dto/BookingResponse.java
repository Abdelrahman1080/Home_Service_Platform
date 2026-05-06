package org.example.bookingservice.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BookingResponse {

    private Long bookingId;
    private Long offerId;
    private Long providerId;
    private Double price;
    private String status;
}
