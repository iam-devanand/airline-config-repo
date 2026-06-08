package com.deva.service.impl;

import com.deva.mapper.AirportMapper;
import com.deva.model.Airport;
import com.deva.model.City;
import com.deva.payload.request.AirportRequest;
import com.deva.payload.response.AirportResponse;
import com.deva.repository.AirportRepository;
import com.deva.repository.CityRepository;
import com.deva.service.AirportService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AirportServiceImpl implements AirportService {

    private final AirportRepository airportRepository;
    private final CityRepository cityRepository;

    public AirportServiceImpl(AirportRepository airportRepository,
                              CityRepository cityRepository) {
        this.airportRepository = airportRepository;
        this.cityRepository = cityRepository;
    }

    @Override
    public AirportResponse createAirport(AirportRequest airportRequest) throws Exception {
        if (airportRepository.findByIataCode(airportRequest.getIataCode()).isPresent()) {
            throw new Exception("Airport with IATA code already exists");
        }
        City city = cityRepository.findById(airportRequest.getCityId()).orElseThrow(
                () -> new Exception("City with id " + airportRequest.getCityId() + " does not exist")
        );

        Airport airport = AirportMapper.toEntity(airportRequest);
        airport.setCity(city);
        Airport savedAirport = airportRepository.save(airport);
        return AirportMapper.toResponse(savedAirport);
    }

    @Override
    public AirportResponse updateAirport(Long airportId, AirportRequest airportRequest) throws Exception {
        Airport exsistingAirport = airportRepository.findById(airportId).orElseThrow(
                () -> new Exception("Airport does not exists with given Id: " + airportId)
        );

        if (airportRequest.getIataCode() != null && !exsistingAirport.getIataCode().equals(airportRequest.getIataCode())
            && airportRepository.findByIataCode(airportRequest.getIataCode()).isPresent()
        ) {
            throw new Exception("Airport with IATA code already exists");
        }

        AirportMapper.updateEntity(airportRequest, exsistingAirport);
        Airport updatedAirport = airportRepository.save(exsistingAirport);
        return AirportMapper.toResponse(updatedAirport);
    }

    @Override
    public AirportResponse getAirportById(Long id) throws Exception {
        Airport airport = airportRepository.findById(id).orElseThrow(
                () -> new Exception("Airport not exists with provided Id: " + id)
        );

        return AirportMapper.toResponse(airport);
    }

    @Override
    public List<AirportResponse> getAllAirports() {
        List<Airport> airports = airportRepository.findAll();
        return airports.stream().map(AirportMapper::toResponse).collect(Collectors.toList());
    }

    @Override
    public void deleteAirportById(Long id) throws Exception {
        Airport airport = airportRepository.findById(id).orElseThrow(
                () -> new Exception("Airport not exists with provided Id: " + id)
        );

        airportRepository.delete(airport);
    }

    @Override
    public List<AirportResponse> getAirportsByCityId(Long cityId) throws Exception {
        List<Airport> airportResponseList = airportRepository.findByCityId(cityId);
        return airportResponseList.stream().map(AirportMapper::toResponse).collect(Collectors.toList());

    }
}
