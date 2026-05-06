package org.example.servicecatalogservice.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class CreateOfferRequest {
    private Long providerId;
    private Long categoryId;
    private Double price;
    private LocalDate availableDate;
}
