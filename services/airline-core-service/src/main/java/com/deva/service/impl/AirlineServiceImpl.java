package com.deva.service.impl;

import com.deva.enums.AirlineStatus;
import com.deva.mapper.AirlineMapper;
import com.deva.model.Airline;
import com.deva.payload.request.AirlineRequest;
import com.deva.payload.response.AirlineDropdownItem;
import com.deva.payload.response.AirlineResponse;
import com.deva.repository.AirlineRepository;
import com.deva.service.AirlineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AirlineServiceImpl implements AirlineService {

    @Autowired
    private AirlineRepository airlineRepository;

    @Override
    public AirlineResponse createAirline(AirlineRequest airlineRequest, Long ownerId) {
        Airline airline = AirlineMapper.toEntity(airlineRequest, ownerId);
        Airline savedAirline = airlineRepository.save(airline);
        return AirlineMapper.toResponse(savedAirline);
    }

    @Override
    public AirlineResponse updateAirline(Long ownerId, AirlineRequest airlineRequest) throws Exception {
        Airline airline = airlineRepository.findByOwnerId(ownerId).orElseThrow(
                () -> new Exception("Airline not found with ownerId: " + ownerId)
        );

        AirlineMapper.updateEntity(airlineRequest, airline);
        Airline updatedAirline = airlineRepository.save(airline);
        return AirlineMapper.toResponse(updatedAirline);
    }

    @Override
    public AirlineResponse getAirlineByOwner(Long ownerId) throws Exception {
        Airline airline = airlineRepository.findByOwnerId(ownerId).orElseThrow(
                () -> new Exception("Airline not found with ownerId: " + ownerId)
        );
        return AirlineMapper.toResponse(airline);
    }

    @Override
    public AirlineResponse getAirlineById(Long airlineId) throws Exception {
        Airline airline = airlineRepository.findById(airlineId).orElseThrow(
                () -> new Exception("Airline not found with airlineId: " + airlineId)
        );

        return AirlineMapper.toResponse(airline);
    }

    @Override
    public Page<AirlineResponse> getAllAirlines(Pageable pageable) {
        Page <Airline> airlinePage = airlineRepository.findAll(pageable);
        return airlinePage.map(AirlineMapper::toResponse);
    }

    @Override
    public void deleteAirline(Long airlineId, Long ownerId) {
        Airline airline = airlineRepository.findByOwnerId(ownerId).orElseThrow(
                () -> new RuntimeException("Airline not found with ownerId: " + ownerId)
        );

        airlineRepository.findById(airlineId).orElseThrow(
                () -> new RuntimeException("Airline not found with airlineId: " + airlineId)
        );

        airlineRepository.delete(airline);

    }

    @Override
    public AirlineResponse changeStatusByAdmin(Long airlineId, AirlineStatus airlineStatus) {
        Airline airline = airlineRepository.findById(airlineId).orElseThrow(
                () -> new RuntimeException("Airline not found with airlineId: " + airlineId)
        );
        airline.setStatus(airlineStatus);
        Airline updatedStatus = airlineRepository.save(airline);

        return AirlineMapper.toResponse(updatedStatus);
    }

    @Override
    public List<AirlineDropdownItem> getAirlineDropdown() {
        List<Airline> byStatus = airlineRepository.findByStatus(AirlineStatus.ACTIVE);
        return byStatus.stream().map((a)-> AirlineDropdownItem.builder()
                .id(a.getId())
                .name(a.getName())
                .iataCode(a.getIataCode())
                .logoUrl(a.getLogoUrl())
                .icaoCode(a.getIcaoCode())
                .build()).collect(Collectors.toList());

    }
}
