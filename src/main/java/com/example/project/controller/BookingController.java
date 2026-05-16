package com.example.project.controller;

import com.example.project.dto.BookingRequest;
import com.example.project.dto.BookingResponse;
import com.example.project.model.User;
import com.example.project.service.BookingService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
public class BookingController {

    private final BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @PostMapping("/flights/book")
    public ResponseEntity<BookingResponse> bookFlight(
        @RequestBody BookingRequest request,
        @AuthenticationPrincipal User user
    ) {
        return ResponseEntity.ok(
            bookingService.bookFlight(user, request.getFlightId())
        );
    }

    @GetMapping("/flight/book/{id}")
    public ResponseEntity<BookingResponse> getBooking(
        @PathVariable Long id,
        @AuthenticationPrincipal User user
    ) {
        return ResponseEntity.ok(bookingService.getBooking(id, user));
    }
}
