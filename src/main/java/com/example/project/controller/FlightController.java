package com.example.project.controller;

import com.example.project.dto.FlightCreateRequest;
import com.example.project.dto.FlightResponse;
import com.example.project.service.FlightService;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class FlightController {

    private final FlightService flightService;

    public FlightController(FlightService flightService) {
        this.flightService = flightService;
    }

    @PostMapping("/flights/create")
    public ResponseEntity<FlightResponse> createFlight(
        @Valid @RequestBody FlightCreateRequest request
    ) {
        return ResponseEntity.ok(flightService.createFlight(request));
    }

    @GetMapping("/flights/{id}")
    public ResponseEntity<FlightResponse> getFlight(@PathVariable Long id) {
        return ResponseEntity.ok(flightService.getFlightById(id));
    }

    @GetMapping("/flights/search")
    public ResponseEntity<List<FlightResponse>> searchFlights(
        @RequestParam(required = false) String flightNumber,
        @RequestParam(required = false) String airlineName,
        @RequestParam(required = false) String departureStart,
        @RequestParam(required = false) String departureEnd
    ) {
        return ResponseEntity.ok(
            flightService.searchFlights(
                flightNumber,
                airlineName,
                departureStart,
                departureEnd
            )
        );
    }
}
