package com.deva.service;

import com.deva.payload.request.CityRequest;
import com.deva.payload.response.CityResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CityService {

    CityResponse createCity(CityRequest request);
    CityResponse updateCity(Long cityId, CityRequest request);
    CityResponse getCityById(Long cityId);

    Page<CityResponse> getAllCity(Pageable pageable);
    Page<CityResponse> searchCities(Pageable pageable, String keyword);
    Page<CityResponse> getCitiesByCountryCode(Pageable pageable, String countryCode);

    boolean cityExists(String cityCode);

    void deleteCity(Long cityId);

}
