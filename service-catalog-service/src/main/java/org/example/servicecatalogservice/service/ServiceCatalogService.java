package org.example.servicecatalogservice.service;

import lombok.RequiredArgsConstructor;
import org.example.servicecatalogservice.dto.AuthResponse;
import org.example.servicecatalogservice.entity.ServiceCategory;
import org.example.servicecatalogservice.entity.ServiceOffer;
import org.example.servicecatalogservice.event.OfferCreatedEvent;
import org.example.servicecatalogservice.event.OfferUpdatedEvent;
import org.example.servicecatalogservice.messaging.RabbitMQProducer;
import org.example.servicecatalogservice.repository.ServiceCategoryRepository;
import org.example.servicecatalogservice.repository.ServiceOfferRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ServiceCatalogService {

    private final ServiceCategoryRepository categoryRepo;
    private final ServiceOfferRepository offerRepo;
    private final RabbitMQProducer producer;
    private final UserServiceClient userServiceClient;

    public ServiceCategory createCategory(String sessionId, String name) {

        var user = userServiceClient.validate(sessionId);

        if (user == null || !"ADMIN".equals(user.getRole())) {
            throw new RuntimeException("Unauthorized");
        }

        ServiceCategory category = ServiceCategory.builder()
                .name(name)
                .build();

        return categoryRepo.save(category);
    }

    public ServiceOffer createOffer(String sessionId, Long categoryId, Double price, LocalDate date) {

        // 🔥 validate user from user-service
        AuthResponse user = userServiceClient.validate(sessionId);

        if (user == null || !"PROVIDER".equals(user.getRole())) {
            throw new RuntimeException("Only providers can create offers");
        }

        ServiceCategory category = categoryRepo.findById(categoryId)
                .orElseThrow(() -> new RuntimeException("Category not found"));

        ServiceOffer offer = ServiceOffer.builder()
                .providerId(user.getUserId()) // 🔥 from session NOT request
                .price(price)
                .availableDate(date)
                .category(category)
                .build();

        ServiceOffer saved = offerRepo.save(offer);

        producer.send("offer.created", OfferCreatedEvent.builder()
                .offerId(saved.getId())
                .providerId(user.getUserId())
                .build());

        return saved;
    }

    public List<ServiceOffer> getAllOffers(String sessionId) {

        var user = userServiceClient.validate(sessionId);

        if (user == null ||
                (!"ADMIN".equals(user.getRole()) && !"PROVIDER".equals(user.getRole()))) {
            throw new RuntimeException("Unauthorized");
        }

        return offerRepo.findAll();
    }
    public List<ServiceOffer> getOffersByCategory(Long categoryId) {
        return offerRepo.findByCategoryId(categoryId);
    }

    public ServiceOffer updateOffer(String sessionId, Long id, Double price, LocalDate date) {

        AuthResponse user = userServiceClient.validate(sessionId);

        if (user == null || !"PROVIDER".equals(user.getRole())) {
            throw new RuntimeException("Unauthorized");
        }

        ServiceOffer offer = offerRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Offer not found"));

        if (!offer.getProviderId().equals(user.getUserId())) {
            throw new RuntimeException("You can only update your own offers");
        }

        offer.setPrice(price);
        offer.setAvailableDate(date);

        ServiceOffer updated = offerRepo.save(offer);

        producer.send("offer.updated", OfferUpdatedEvent.builder()
                .offerId(updated.getId())
                .price(updated.getPrice())
                .build());

        return updated;
    }

    public ServiceOffer getOfferById(Long id) {
        return offerRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Offer not found"));
    }
}