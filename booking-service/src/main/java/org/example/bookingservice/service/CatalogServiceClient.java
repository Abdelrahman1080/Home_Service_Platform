package org.example.bookingservice.service;

import lombok.Data;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class CatalogServiceClient {

    private final RestTemplate restTemplate;

    public CatalogServiceClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public OfferResponse getOffer(Long offerId) {

        String url = "http://localhost:8081/catalog/offer/" + offerId;

        return restTemplate.getForObject(url, OfferResponse.class);
    }

    @Data
    public static class OfferResponse {
        private Long id;
        private Long providerId;
        private Double price;
    }
}
