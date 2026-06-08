package com.deva.controller;

import com.deva.payload.request.CityRequest;
import com.deva.payload.response.ApiResponse;
import com.deva.payload.response.CityResponse;
import com.deva.service.CityService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/locations/cities")
public class CityController {

    private final CityService cityService;

    public CityController(CityService cityService) {
        this.cityService = cityService;
    }

    @PostMapping("/create")
    public ResponseEntity<CityResponse> createCity(@Valid @RequestBody CityRequest cityRequest) {
        CityResponse cityResponse = cityService.createCity(cityRequest);
        return new ResponseEntity<>(cityResponse, HttpStatus.CREATED);
    }

    @PutMapping("/update/{cityId}")
    public ResponseEntity<CityResponse> updateCity(@Valid @RequestBody CityRequest cityRequest, @PathVariable Long cityId) {
        CityResponse cityResponse = cityService.updateCity(cityId, cityRequest);
        return new ResponseEntity<>(cityResponse, HttpStatus.OK);
    }

    @GetMapping("/{cityId}")
    public ResponseEntity<CityResponse> getCityById(@PathVariable Long cityId) {
        CityResponse cityResponse = cityService.getCityById(cityId);
        return new ResponseEntity<>(cityResponse, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<Page<CityResponse>> getAllCities(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(defaultValue = "name") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDirection
    ) {
        Sort sort = Sort.by(Sort.Direction.fromString(sortDirection), sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<CityResponse> allCity = cityService.getAllCity(pageable);
        return new ResponseEntity<>(allCity, HttpStatus.OK);
    }

    @DeleteMapping("/{cityId}")
    public ResponseEntity<ApiResponse> deleteCity(@PathVariable Long cityId) {
        cityService.deleteCity(cityId);
        return new ResponseEntity<>(new ApiResponse("City deleted successfully."), HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<Page<CityResponse>> searchCities(
            @RequestParam String keyword,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        Page<CityResponse> cityResponses = cityService.searchCities(pageable, keyword);
        return new ResponseEntity<>(cityResponses, HttpStatus.OK);
    }

    @GetMapping("/country/{countryCode}")
    public ResponseEntity<Page<CityResponse>> getCitiesByCountryCode(
            @PathVariable String countryCode,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        Page<CityResponse> cityResponses = cityService.getCitiesByCountryCode(pageable, countryCode);
        return new ResponseEntity<>(cityResponses, HttpStatus.OK);
    }

    @GetMapping("/exists/{cityCode}")
    public ResponseEntity<Boolean> cityCityExists(@PathVariable String cityCode) {
        boolean isExists = cityService.cityExists(cityCode.toUpperCase());
        return new ResponseEntity<>(isExists, HttpStatus.OK);
    }

}
