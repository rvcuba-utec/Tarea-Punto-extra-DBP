package com.example.project.service;

import com.example.project.dto.BookingResponse;
import com.example.project.exception.BadRequestException;
import com.example.project.exception.ConflictException;
import com.example.project.model.Booking;
import com.example.project.model.Flight;
import com.example.project.model.User;
import com.example.project.repository.BookingRepository;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BookingService {

    private final BookingRepository bookingRepository;
    private final FlightService flightService;
    private final ModelMapper modelMapper;

    public BookingService(
        BookingRepository bookingRepository,
        FlightService flightService,
        ModelMapper modelMapper
    ) {
        this.bookingRepository = bookingRepository;
        this.flightService = flightService;
        this.modelMapper = modelMapper;
    }

    @Transactional
    public BookingResponse bookFlight(User user, Long flightId) {
        Flight flight = flightService.getFlightEntity(flightId);

        if (flight.getDepartureTime().isBefore(LocalDateTime.now())) {
            throw new BadRequestException(
                "Cannot book a flight that has already departed"
            );
        }

        if (flight.getAvailableSeats() <= 0) {
            throw new ConflictException("No seats available");
        }

        List<Booking> userBookings = bookingRepository.findByUserId(
            user.getId()
        );
        for (Booking b : userBookings) {
            Flight bookedFlight = b.getFlight();
            if (
                flight
                    .getDepartureTime()
                    .isBefore(bookedFlight.getArrivalTime()) &&
                flight.getArrivalTime().isAfter(bookedFlight.getDepartureTime())
            ) {
                throw new ConflictException(
                    "You already have a booking that overlaps with this flight"
                );
            }
        }

        flight.setAvailableSeats(flight.getAvailableSeats() - 1);
        flightService.saveFlight(flight);

        Booking booking = new Booking();
        booking.setUser(user);
        booking.setFlight(flight);
        booking.setBookingDate(LocalDateTime.now());
        booking = bookingRepository.save(booking);

        generateEmailFile(booking, user, flight);

        return new BookingResponse(
            booking.getId(),
            user.getFirstName() + " " + user.getLastName(),
            flight.getFlightNumber(),
            booking.getBookingDate(),
            flight.getDepartureTime(),
            flight.getArrivalTime()
        );
    }

    public BookingResponse getBooking(Long bookingId, User user) {
        Booking booking = bookingRepository
            .findById(bookingId)
            .orElseThrow(() -> new BadRequestException("Booking not found"));
        if (!booking.getUser().getId().equals(user.getId())) {
            throw new BadRequestException("Unauthorized access");
        }
        Flight flight = booking.getFlight();
        return new BookingResponse(
            booking.getId(),
            user.getFirstName() + " " + user.getLastName(),
            flight.getFlightNumber(),
            booking.getBookingDate(),
            flight.getDepartureTime(),
            flight.getArrivalTime()
        );
    }

    private void generateEmailFile(Booking booking, User user, Flight flight) {
        String fileName = "flight_booking_email_" + booking.getId() + ".txt";
        File dir = new File("bookings");
        if (!dir.exists()) dir.mkdirs();
        File file = new File(dir, fileName);
        try (FileWriter writer = new FileWriter(file)) {
            writer.write("Booking ID: " + booking.getId() + "\n");
            writer.write(
                "Customer: " +
                    user.getFirstName() +
                    " " +
                    user.getLastName() +
                    "\n"
            );
            writer.write("Flight: " + flight.getFlightNumber() + "\n");
            writer.write(
                "Departure: " +
                    flight
                        .getDepartureTime()
                        .format(DateTimeFormatter.ISO_LOCAL_DATE_TIME) +
                    "\n"
            );
            writer.write(
                "Arrival: " +
                    flight
                        .getArrivalTime()
                        .format(DateTimeFormatter.ISO_LOCAL_DATE_TIME) +
                    "\n"
            );
            writer.write(
                "Booking Date: " +
                    booking
                        .getBookingDate()
                        .format(DateTimeFormatter.ISO_LOCAL_DATE_TIME) +
                    "\n"
            );
        } catch (IOException e) {
            throw new RuntimeException("Could not write booking file", e);
        }
    }
}
