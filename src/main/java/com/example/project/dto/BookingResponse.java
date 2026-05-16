package com.example.project.dto;

import java.time.LocalDateTime;

public class BookingResponse {

    private Long id;
    private String customerName;
    private String flightNumber;
    private LocalDateTime bookingDate;
    private LocalDateTime departureTime;
    private LocalDateTime arrivalTime;

    public BookingResponse(
        Long id,
        String customerName,
        String flightNumber,
        LocalDateTime bookingDate,
        LocalDateTime departureTime,
        LocalDateTime arrivalTime
    ) {
        this.id = id;
        this.customerName = customerName;
        this.flightNumber = flightNumber;
        this.bookingDate = bookingDate;
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
    }

    public Long getId() {
        return id;
    }

    public String getCustomerName() {
        return customerName;
    }

    public String getFlightNumber() {
        return flightNumber;
    }

    public LocalDateTime getBookingDate() {
        return bookingDate;
    }

    public LocalDateTime getDepartureTime() {
        return departureTime;
    }

    public LocalDateTime getArrivalTime() {
        return arrivalTime;
    }
}
