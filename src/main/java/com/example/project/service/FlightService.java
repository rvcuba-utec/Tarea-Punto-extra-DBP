package com.example.project.service;

import com.example.project.dto.FlightCreateRequest;
import com.example.project.dto.FlightResponse;
import com.example.project.exception.BadRequestException;
import com.example.project.exception.ConflictException;
import com.example.project.model.Flight;
import com.example.project.repository.FlightRepository;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.stream.Collectors;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class FlightService {

    private final FlightRepository flightRepository;
    private final ModelMapper modelMapper;

    public FlightService(
        FlightRepository flightRepository,
        ModelMapper modelMapper
    ) {
        this.flightRepository = flightRepository;
        this.modelMapper = modelMapper;
    }

    public FlightResponse createFlight(FlightCreateRequest request) {
        LocalDateTime departure;
        LocalDateTime arrival;
        try {
            departure = LocalDateTime.parse(
                request.getDepartureTime(),
                DateTimeFormatter.ISO_LOCAL_DATE_TIME
            );
            arrival = LocalDateTime.parse(
                request.getArrivalTime(),
                DateTimeFormatter.ISO_LOCAL_DATE_TIME
            );
        } catch (DateTimeParseException e) {
            throw new BadRequestException(
                "Date times must be in ISO format (yyyy-MM-ddTHH:mm:ss)"
            );
        }

        if (!departure.isBefore(arrival)) {
            throw new BadRequestException("Departure must be before arrival");
        }

        if (flightRepository.existsByFlightNumber(request.getFlightNumber())) {
            throw new ConflictException("Flight number already exists");
        }

        Flight flight = modelMapper.map(request, Flight.class);
        flight.setDepartureTime(departure);
        flight.setArrivalTime(arrival);

        flight = flightRepository.save(flight);
        return modelMapper.map(flight, FlightResponse.class);
    }

    public List<FlightResponse> searchFlights(
        String flightNumber,
        String airlineName,
        String departureStart,
        String departureEnd
    ) {
        LocalDateTime start = null;
        LocalDateTime end = null;
        if (departureStart != null && !departureStart.isEmpty()) {
            try {
                start = LocalDateTime.parse(
                    departureStart,
                    DateTimeFormatter.ISO_LOCAL_DATE_TIME
                );
            } catch (DateTimeParseException e) {
                throw new BadRequestException("Invalid departureStart format");
            }
        }
        if (departureEnd != null && !departureEnd.isEmpty()) {
            try {
                end = LocalDateTime.parse(
                    departureEnd,
                    DateTimeFormatter.ISO_LOCAL_DATE_TIME
                );
            } catch (DateTimeParseException e) {
                throw new BadRequestException("Invalid departureEnd format");
            }
        }

        List<Flight> flights = flightRepository.searchFlights(
            flightNumber,
            airlineName,
            start,
            end
        );
        return flights
            .stream()
            .map(flight -> modelMapper.map(flight, FlightResponse.class))
            .collect(Collectors.toList());
    }

    public FlightResponse getFlightById(Long id) {
        Flight flight = flightRepository
            .findById(id)
            .orElseThrow(() -> new BadRequestException("Flight not found"));
        return modelMapper.map(flight, FlightResponse.class);
    }

    public Flight getFlightEntity(Long id) {
        return flightRepository
            .findById(id)
            .orElseThrow(() -> new BadRequestException("Flight not found"));
    }

    public void saveFlight(Flight flight) {
        flightRepository.save(flight);
    }
}
