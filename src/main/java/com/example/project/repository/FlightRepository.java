package com.example.project.repository;

import com.example.project.model.Flight;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface FlightRepository extends JpaRepository<Flight, Long> {
    boolean existsByFlightNumber(String flightNumber);

    @Query(
        "SELECT f FROM Flight f WHERE " +
            "(:flightNumber IS NULL OR UPPER(f.flightNumber) LIKE UPPER(CONCAT('%',:flightNumber,'%'))) AND " +
            "(:airlineName IS NULL OR UPPER(f.airlineName) LIKE UPPER(CONCAT('%',:airlineName,'%'))) AND " +
            "(:departureStart IS NULL OR f.departureTime >= :departureStart) AND " +
            "(:departureEnd IS NULL OR f.departureTime <= :departureEnd)"
    )
    List<Flight> searchFlights(
        @Param("flightNumber") String flightNumber,
        @Param("airlineName") String airlineName,
        @Param("departureStart") LocalDateTime departureStart,
        @Param("departureEnd") LocalDateTime departureEnd
    );
}
