package com.deva.service;

import com.deva.payload.request.AirportRequest;
import com.deva.payload.response.AirportResponse;

import java.util.List;

public interface AirportService {

    AirportResponse createAirport(AirportRequest airportRequest) throws Exception;
    AirportResponse updateAirport(Long airportId, AirportRequest airportRequest) throws Exception;
    AirportResponse getAirportById(Long id) throws Exception;
    List<AirportResponse> getAllAirports();
    void deleteAirportById(Long id) throws Exception;
    List<AirportResponse> getAirportsByCityId(Long cityId) throws Exception;


}
