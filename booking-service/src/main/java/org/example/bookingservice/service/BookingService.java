package org.example.bookingservice.service;

import lombok.RequiredArgsConstructor;
import org.example.bookingservice.client.*;
import org.example.bookingservice.entity.*;
import org.example.bookingservice.event.*;
import org.example.bookingservice.exception.*;
import org.example.bookingservice.messaging.RabbitMQProducer;
import org.example.bookingservice.repository.BookingRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BookingService {

    private final BookingRepository repo;
    private final UserServiceClient userClient;
    private final CatalogServiceClient catalogClient;
    private final WalletClient walletClient;
    private final RabbitMQProducer producer;


    public List<Booking> getAllBookingsForAdmin(String sessionId) {

        var user = userClient.validate(sessionId);

        if (user == null || !"ADMIN".equals(user.getRole())) {
            throw new RuntimeException("Unauthorized");
        }

        return repo.findAll();
    }
    public List<Booking> getBookingsForCustomer(String sessionId) {

        var user = userClient.validate(sessionId);

        if (user == null || !"CUSTOMER".equals(user.getRole())) {
            throw new RuntimeException("Unauthorized");
        }

        return repo.findByCustomerId(user.getUserId());
    }
    public Booking createBooking(String sessionId, Long offerId) {

        // 1️⃣ validate user
        var user = userClient.validate(sessionId);

        if (user == null || !"CUSTOMER".equals(user.getRole()) &&!"ADMIN".equals(user.getRole()))
            throw new RuntimeException("Unauthorized");

        // 2️⃣ get offer
        var offer = catalogClient.getOffer(offerId);

        if (offer == null)
            throw new ServiceUnavailableException("Offer not found");

        // 3️⃣ deduct money
        boolean success = walletClient.deduct(user.getUserId(), offer.getPrice());

        if (!success) {
            System.out.println("[RabbitMQ] Event: booking.rejected");
            producer.send("booking.rejected",
                    BookingRejectedEvent.builder()
                            .customerId(user.getUserId())
                            .reason("Insufficient balance")
                            .build());

            throw new InsufficientBalanceException("Not enough balance");
        }

            try {
                // 4️⃣ create booking
                Booking booking = Booking.builder()
                        .customerId(user.getUserId())
                        .providerId(offer.getProviderId())
                        .offerId(offerId)
                        .price(offer.getPrice())
                        .status(BookingStatus.CONFIRMED)
                        .createdAt(LocalDateTime.now())
                        .build();

                Booking saved = repo.save(booking);

                // 5️⃣ success events
                producer.send("booking.created",
                        BookingCreatedEvent.builder()
                                .bookingId(saved.getId())
                                .customerId(saved.getCustomerId())
                                .providerId(saved.getProviderId())
                                .build());

                producer.send("payment.success",
                        PaymentSuccessEvent.builder()
                                .userId(saved.getCustomerId())
                                .amount(saved.getPrice())
                                .build());

                return saved;

            } catch (Exception ex) {

                // rollback
                walletClient.refund(user.getUserId(), offer.getPrice());

                producer.send("payment.rollback",
                        PaymentRollbackEvent.builder()
                                .userId(user.getUserId())
                                .amount(offer.getPrice())
                                .build());

                throw new RuntimeException("Booking failed, rolled back");
            }

    }
}
