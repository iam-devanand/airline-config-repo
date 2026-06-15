package com.deva.controller;

import com.deva.payload.request.CityRequest;
import com.deva.payload.response.ApiResponse;
import com.deva.payload.response.CityResponse;
import com.deva.service.CityService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "City Management", description = "City CRUD operations and search")
public class CityController {

    private final CityService cityService;

    public CityController(CityService cityService) {
        this.cityService = cityService;
    }

    @Operation(summary = "Create new city", description = "Creates a new city")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "201", description = "City created successfully",
                    content = @Content(schema = @Schema(implementation = CityResponse.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Invalid input")
    })
    @PostMapping("/create")
    public ResponseEntity<CityResponse> createCity(@Valid @RequestBody CityRequest cityRequest) {
        CityResponse cityResponse = cityService.createCity(cityRequest);
        return new ResponseEntity<>(cityResponse, HttpStatus.CREATED);
    }

    @Operation(summary = "Update city", description = "Updates an existing city")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "City updated successfully",
                    content = @Content(schema = @Schema(implementation = CityResponse.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "City not found")
    })
    @PutMapping("/update/{cityId}")
    public ResponseEntity<CityResponse> updateCity(
            @Valid @RequestBody CityRequest cityRequest,
            @Parameter(description = "City ID", required = true) @PathVariable Long cityId) {
        CityResponse cityResponse = cityService.updateCity(cityId, cityRequest);
        return new ResponseEntity<>(cityResponse, HttpStatus.OK);
    }

    @Operation(summary = "Get city by ID", description = "Retrieves a city by its ID")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "City found",
                    content = @Content(schema = @Schema(implementation = CityResponse.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "City not found")
    })
    @GetMapping("/{cityId}")
    public ResponseEntity<CityResponse> getCityById(
            @Parameter(description = "City ID", required = true) @PathVariable Long cityId) {
        CityResponse cityResponse = cityService.getCityById(cityId);
        return new ResponseEntity<>(cityResponse, HttpStatus.OK);
    }

    @Operation(summary = "Get all cities (paginated)", description = "Returns paginated list of all cities with sorting")
    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "List of cities",
            content = @Content(schema = @Schema(implementation = Page.class)))
    @GetMapping
    public ResponseEntity<Page<CityResponse>> getAllCities(
            @Parameter(description = "Page number (0-based)") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Page size") @RequestParam(defaultValue = "20") int size,
            @Parameter(description = "Sort field") @RequestParam(defaultValue = "name") String sortBy,
            @Parameter(description = "Sort direction (asc/desc)") @RequestParam(defaultValue = "asc") String sortDirection
    ) {
        Sort sort = Sort.by(Sort.Direction.fromString(sortDirection), sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<CityResponse> allCity = cityService.getAllCity(pageable);
        return new ResponseEntity<>(allCity, HttpStatus.OK);
    }

    @Operation(summary = "Delete city", description = "Deletes a city by ID")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "City deleted successfully",
                    content = @Content(schema = @Schema(implementation = ApiResponse.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "City not found")
    })
    @DeleteMapping("/{cityId}")
    public ResponseEntity<ApiResponse> deleteCity(
            @Parameter(description = "City ID", required = true) @PathVariable Long cityId) {
        cityService.deleteCity(cityId);
        return new ResponseEntity<>(new ApiResponse("City deleted successfully."), HttpStatus.OK);
    }

    @Operation(summary = "Search cities", description = "Search cities by keyword")
    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "List of matching cities",
            content = @Content(schema = @Schema(implementation = Page.class)))
    @GetMapping("/search")
    public ResponseEntity<Page<CityResponse>> searchCities(
            @Parameter(description = "Search keyword", required = true) @RequestParam String keyword,
            @Parameter(description = "Page number (0-based)") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Page size") @RequestParam(defaultValue = "20") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        Page<CityResponse> cityResponses = cityService.searchCities(pageable, keyword);
        return new ResponseEntity<>(cityResponses, HttpStatus.OK);
    }

    @Operation(summary = "Get cities by country code", description = "Returns paginated list of cities in a country")
    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "List of cities in country",
            content = @Content(schema = @Schema(implementation = Page.class)))
    @GetMapping("/country/{countryCode}")
    public ResponseEntity<Page<CityResponse>> getCitiesByCountryCode(
            @Parameter(description = "ISO country code", required = true) @PathVariable String countryCode,
            @Parameter(description = "Page number (0-based)") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Page size") @RequestParam(defaultValue = "20") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        Page<CityResponse> cityResponses = cityService.getCitiesByCountryCode(pageable, countryCode);
        return new ResponseEntity<>(cityResponses, HttpStatus.OK);
    }

    @Operation(summary = "Check if city exists", description = "Checks if a city code exists")
    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Existence check result",
            content = @Content(schema = @Schema(implementation = Boolean.class)))
    @GetMapping("/exists/{cityCode}")
    public ResponseEntity<Boolean> cityCityExists(
            @Parameter(description = "City code (IATA)", required = true) @PathVariable String cityCode) {
        boolean isExists = cityService.cityExists(cityCode.toUpperCase());
        return new ResponseEntity<>(isExists, HttpStatus.OK);
    }

}
