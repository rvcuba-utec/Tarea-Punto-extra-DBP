package com.example.project.dto;

import java.time.LocalDateTime;

public class FlightResponse {

    private Long id;
    private String flightNumber;
    private String airlineName;
    private LocalDateTime departureTime;
    private LocalDateTime arrivalTime;
    private int availableSeats;

    public FlightResponse() {}

    public FlightResponse(
        Long id,
        String flightNumber,
        String airlineName,
        LocalDateTime departureTime,
        LocalDateTime arrivalTime,
        int availableSeats
    ) {
        this.id = id;
        this.flightNumber = flightNumber;
        this.airlineName = airlineName;
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
        this.availableSeats = availableSeats;
    }

    public Long getId() {
        return id;
    }

    public String getFlightNumber() {
        return flightNumber;
    }

    public String getAirlineName() {
        return airlineName;
    }

    public LocalDateTime getDepartureTime() {
        return departureTime;
    }

    public LocalDateTime getArrivalTime() {
        return arrivalTime;
    }

    public int getAvailableSeats() {
        return availableSeats;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setFlightNumber(String flightNumber) {
        this.flightNumber = flightNumber;
    }

    public void setAirlineName(String airlineName) {
        this.airlineName = airlineName;
    }

    public void setDepartureTime(LocalDateTime departureTime) {
        this.departureTime = departureTime;
    }

    public void setArrivalTime(LocalDateTime arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public void setAvailableSeats(int availableSeats) {
        this.availableSeats = availableSeats;
    }
}
