package com.deva.repository;

import com.deva.model.Airport;
import com.deva.payload.response.AirportResponse;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AirportRepository extends JpaRepository<Airport, Long> {
    Optional<Airport> findByIataCode(String iataCode);
    List<Airport> findByCityId(Long cityId);
}