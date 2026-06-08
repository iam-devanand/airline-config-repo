package com.deva.mapper;

import com.deva.model.Airport;
import com.deva.model.City;
import com.deva.payload.request.AirportRequest;
import com.deva.payload.response.AirportResponse;

public class AirportMapper {

    public static Airport toEntity(AirportRequest airportRequest) {
        if (airportRequest == null) {
            return null;
        }

        return Airport.builder()
                .name(airportRequest.getName())
                .iataCode(airportRequest.getIataCode())
                .address(airportRequest.getAddress())
                .geoCode(airportRequest.getGeoCode())
                .timeZone(airportRequest.getIataCode())
                .build();
    }

    public static AirportResponse toResponse(Airport airport) {
        if (airport == null) {
            return null;
        }

        return AirportResponse.builder()
                .id(airport.getId())
                .name(airport.getName())
                .iataCode(airport.getIataCode())
                .address(airport.getAddress())
                .geoCode(airport.getGeoCode())
                .city(CityMapper.toResponse(airport.getCity()))
                //.timeZone(airport.getTimeZone())
                .detailedName(airport.getDetailedName())
                .build();
    }

    public static void updateEntity(AirportRequest airportRequest, Airport existingAirport) {
        if (airportRequest == null || existingAirport == null) return;

        if (airportRequest.getIataCode() != null) {
            existingAirport.setIataCode(airportRequest.getIataCode());
        }

        if (airportRequest.getAddress() != null) {
            existingAirport.setAddress(airportRequest.getAddress());
        }

        if (airportRequest.getGeoCode() != null) {
            existingAirport.setGeoCode(airportRequest.getGeoCode());
        }

        if (airportRequest.getName() != null) {
            existingAirport.setName(airportRequest.getName());
        }

    }

}
