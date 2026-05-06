package org.example.servicecatalogservice.repository;

import org.example.servicecatalogservice.entity.ServiceOffer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ServiceOfferRepository extends JpaRepository<ServiceOffer, Long> {

    List<ServiceOffer> findByCategoryId(Long categoryId);
}
