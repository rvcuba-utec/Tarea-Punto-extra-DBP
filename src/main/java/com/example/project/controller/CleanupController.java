package com.example.project.controller;

import com.example.project.repository.BookingRepository;
import com.example.project.repository.FlightRepository;
import com.example.project.repository.UserRepository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CleanupController {

    private final BookingRepository bookingRepository;
    private final FlightRepository flightRepository;
    private final UserRepository userRepository;

    public CleanupController(
        BookingRepository bookingRepository,
        FlightRepository flightRepository,
        UserRepository userRepository
    ) {
        this.bookingRepository = bookingRepository;
        this.flightRepository = flightRepository;
        this.userRepository = userRepository;
    }

    @DeleteMapping("/cleanup")
    @Transactional
    public String cleanup() {
        bookingRepository.deleteAll();
        flightRepository.deleteAll();
        userRepository.deleteAll();
        return "All data cleaned up";
    }
}
