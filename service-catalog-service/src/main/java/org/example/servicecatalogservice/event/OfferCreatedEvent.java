package org.example.servicecatalogservice.event;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OfferCreatedEvent {
    private Long offerId;
    private Long providerId;
}
