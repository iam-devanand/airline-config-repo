package com.deva.controller;

import com.deva.payload.request.AirportRequest;
import com.deva.payload.response.AirportResponse;
import com.deva.payload.response.ApiResponse;
import com.deva.service.AirportService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/locations/airports")
@Tag(name = "Airport Management", description = "Airport CRUD operations")
public class AirportController {

    private final AirportService airportService;

    public AirportController(AirportService airportService) {
        this.airportService = airportService;
    }

    @Operation(summary = "Create new airport", description = "Creates a new airport")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "201", description = "Airport created successfully",
                    content = @Content(schema = @Schema(implementation = AirportResponse.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Invalid input")
    })
    @PostMapping("/create")
    public ResponseEntity<AirportResponse> create(@Valid @RequestBody AirportRequest airportRequest) throws Exception {
        AirportResponse airportResponse = airportService.createAirport(airportRequest);
        return new ResponseEntity<>(airportResponse, HttpStatus.CREATED);
    }

    @Operation(summary = "Update airport", description = "Updates an existing airport")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Airport updated successfully",
                    content = @Content(schema = @Schema(implementation = AirportResponse.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Airport not found")
    })
    @PutMapping("/update/{airportId}")
    public ResponseEntity<AirportResponse> updateAirport(
            @Parameter(description = "Airport ID", required = true) @PathVariable Long airportId,
            @Valid @RequestBody AirportRequest airportRequest) throws Exception {
        AirportResponse airportResponse = airportService.updateAirport(airportId, airportRequest);
        return new ResponseEntity<>(airportResponse, HttpStatus.OK);
    }

    @Operation(summary = "Get all airports", description = "Returns list of all airports")
    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "List of airports",
            content = @Content(schema = @Schema(implementation = AirportResponse.class)))
    @GetMapping("/getAllAirports")
    public ResponseEntity<List<AirportResponse>> getAllAirports() throws Exception {
        List<AirportResponse> airportResponseList = airportService.getAllAirports();
        return new ResponseEntity<>(airportResponseList, HttpStatus.OK);
    }

    @Operation(summary = "Get airport by ID", description = "Retrieves an airport by its ID")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Airport found",
                    content = @Content(schema = @Schema(implementation = AirportResponse.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Airport not found")
    })
    @GetMapping("/getAirport/{airportId}")
    public ResponseEntity<AirportResponse> getAirport(
            @Parameter(description = "Airport ID", required = true) @PathVariable Long airportId) throws Exception {
        AirportResponse airportResponse = airportService.getAirportById(airportId);
        return new ResponseEntity<>(airportResponse, HttpStatus.OK);
    }

    @Operation(summary = "Get airports by city ID", description = "Returns list of airports in a city")
    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "List of airports in city",
            content = @Content(schema = @Schema(implementation = AirportResponse.class)))
    @GetMapping("/getAirport/city/{cityId}")
    public ResponseEntity<List<AirportResponse>> getAirportByCityId(
            @Parameter(description = "City ID", required = true) @PathVariable Long cityId) throws Exception {
        List<AirportResponse> airportResponse = airportService.getAirportsByCityId(cityId);
        return new ResponseEntity<>(airportResponse, HttpStatus.OK);
    }

    @Operation(summary = "Delete airport", description = "Deletes an airport by ID")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Airport deleted successfully",
                    content = @Content(schema = @Schema(implementation = ApiResponse.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Airport not found")
    })
    @DeleteMapping("/delete")
    public ResponseEntity<ApiResponse> deleteAirport(
            @Parameter(description = "Airport ID", required = true) @RequestParam Long airportId) throws Exception {
        airportService.deleteAirportById(airportId);
        return new ResponseEntity<>(new ApiResponse("Airport deleted successfully"), HttpStatus.OK);
    }

}
