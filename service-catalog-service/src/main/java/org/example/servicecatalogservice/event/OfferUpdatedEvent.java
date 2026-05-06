package org.example.servicecatalogservice.event;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OfferUpdatedEvent {
    private Long offerId;
    private Double price;
}
