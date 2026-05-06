package org.example.servicecatalogservice.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OfferResponse {
    private Long id;
    private Double price;
}
