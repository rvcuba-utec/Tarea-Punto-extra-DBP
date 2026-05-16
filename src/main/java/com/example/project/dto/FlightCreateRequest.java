package com.example.project.dto;

import jakarta.validation.constraints.*;

public class FlightCreateRequest {

    @NotBlank
    @Pattern(regexp = "^[A-Z0-9]{1,6}$")
    private String flightNumber;

    @NotBlank
    private String airlineName;

    @NotBlank
    private String departureTime; // ISO format, p. ej. "2026-05-16T10:00:00"

    @NotBlank
    private String arrivalTime;

    @Min(1)
    private int availableSeats;

    // getters y setters
    public FlightCreateRequest() {}

    public String getFlightNumber() {
        return flightNumber;
    }

    public void setFlightNumber(String flightNumber) {
        this.flightNumber = flightNumber;
    }

    public String getAirlineName() {
        return airlineName;
    }

    public void setAirlineName(String airlineName) {
        this.airlineName = airlineName;
    }

    public String getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(String departureTime) {
        this.departureTime = departureTime;
    }

    public String getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(String arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public int getAvailableSeats() {
        return availableSeats;
    }

    public void setAvailableSeats(int availableSeats) {
        this.availableSeats = availableSeats;
    }
}
