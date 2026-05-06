package org.example.bookingservice.controller;

import lombok.RequiredArgsConstructor;
import org.example.bookingservice.dto.BookingRequest;
import org.example.bookingservice.service.BookingService;
import org.example.bookingservice.util.ResponseUtil;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/booking")
@RequiredArgsConstructor
public class BookingController {

    private final BookingService service;

    @PostMapping
    public Object book(
            @RequestHeader("X-SESSION-ID") String sessionId,
            @RequestBody BookingRequest req
    ) {
        return ResponseUtil.success(
                service.createBooking(sessionId, req.getOfferId())
        );
    }

    @GetMapping("/customer/all")
    public Object getBookingsForCustomer(@RequestHeader("X-SESSION-ID") String sessionId) {
        return ResponseUtil.success(service.getBookingsForCustomer(sessionId));
    }

    @GetMapping("/admin/all")
    public Object getAllBookings(@RequestHeader("X-SESSION-ID") String sessionId) {

        return ResponseUtil.success(
                service.getAllBookingsForAdmin(sessionId)
        );
    }
}
