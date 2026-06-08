package com.deva.service.impl;

import com.deva.mapper.CityMapper;
import com.deva.model.City;
import com.deva.payload.request.CityRequest;
import com.deva.payload.response.CityResponse;
import com.deva.repository.CityRepository;
import com.deva.service.CityService;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class CityServiceImpl implements CityService {

    @Autowired
    private CityRepository cityRepository;

    @Override
    public CityResponse createCity(CityRequest request) {
        if (cityRepository.existsByCityCode(request.getCityCode())) {
            throw  new ResponseStatusException(HttpStatus.BAD_REQUEST, "City code already exists");
        }

        City city = CityMapper.toEntity(request);
        City savedCity = cityRepository.save(city);
        return CityMapper.toResponse(savedCity);
    }

    @Override
    public CityResponse updateCity(Long cityId, CityRequest request) {
        City city = cityRepository.findById(cityId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "City not found"));

        if (cityRepository.existsByCityCode(request.getCityCode())) {
            throw  new ResponseStatusException(HttpStatus.BAD_REQUEST, "City code already exists");
        }

        City updatedCity = cityRepository.save(CityMapper.updateEntity(city, request));
        return CityMapper.toResponse(updatedCity);
    }

    @Override
    public CityResponse getCityById(Long cityId) {
        City city = cityRepository.findById(cityId).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "City not found with id: " + cityId)
        );

        return CityMapper.toResponse(city);
    }

    @Override
    public Page<CityResponse> getAllCity(Pageable pageable) {
        return cityRepository.findAll(pageable).map(CityMapper::toResponse);
    }

    @Override
    public Page<CityResponse> searchCities(Pageable pageable, String keyword) {
        return cityRepository.searchByKeyword(keyword, pageable).map(CityMapper::toResponse);
    }

    @Override
    public Page<CityResponse> getCitiesByCountryCode(Pageable pageable, String countryCode) {
        return cityRepository.findByCountryCodeIgnoreCase(countryCode, pageable).map(CityMapper::toResponse);
    }

    @Override
    public boolean cityExists(String cityCode) {
        return cityRepository.existsByCityCode(cityCode);
    }

    @Override
    public void deleteCity(Long cityId) {
        City city = cityRepository.findById(cityId).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "City not found with id: " + cityId)
        );

        cityRepository.delete(city);
    }
}
