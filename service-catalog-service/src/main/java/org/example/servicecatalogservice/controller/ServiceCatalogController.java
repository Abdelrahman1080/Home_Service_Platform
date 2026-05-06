package org.example.servicecatalogservice.controller;

import lombok.RequiredArgsConstructor;
import org.example.servicecatalogservice.dto.CreateCategoryRequest;
import org.example.servicecatalogservice.dto.CreateOfferRequest;
import org.example.servicecatalogservice.service.ServiceCatalogService;
import org.example.servicecatalogservice.util.ResponseUtil;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/catalog")
@RequiredArgsConstructor
public class ServiceCatalogController {

    private final ServiceCatalogService service;

    @PostMapping("/category")
    public Object createCategory(@RequestBody CreateCategoryRequest req) {
        return ResponseUtil.success(service.createCategory(req.getName()));
    }

    @PostMapping("/offer")
    public Object createOffer(
            @RequestHeader("X-SESSION-ID") String sessionId,
            @RequestBody CreateOfferRequest req
    ) {
        return ResponseUtil.success(
                service.createOffer(
                        sessionId,
                        req.getCategoryId(),
                        req.getPrice(),
                        req.getAvailableDate()
                )
        );
    }

    @GetMapping("/offers")
    public Object getAll() {
        return ResponseUtil.success(service.getAllOffers());
    }

    @GetMapping("/offers/{categoryId}")
    public Object byCategory(@PathVariable Long categoryId) {
        return ResponseUtil.success(service.getOffersByCategory(categoryId));
    }

    @PutMapping("/offer/{id}")
    public Object update(
            @RequestHeader("X-SESSION-ID") String sessionId,
            @PathVariable Long id,
            @RequestBody CreateOfferRequest req
    ) {
        return ResponseUtil.success(
                service.updateOffer(sessionId, id, req.getPrice(), req.getAvailableDate())
        );
    }
}