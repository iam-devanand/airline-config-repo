package com.deva.controller;

import com.deva.payload.request.AirportRequest;
import com.deva.payload.response.AirportResponse;
import com.deva.payload.response.ApiResponse;
import com.deva.service.AirportService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/locations/airports")
public class AirportController {

    private final AirportService airportService;

    public AirportController(AirportService airportService) {
        this.airportService = airportService;
    }

    @PostMapping("/create")
    public ResponseEntity<AirportResponse> create(@Valid @RequestBody AirportRequest airportRequest) throws Exception {
        AirportResponse airportResponse = airportService.createAirport(airportRequest);
        return new  ResponseEntity<>(airportResponse, HttpStatus.CREATED);
    }

    @PutMapping("/update/{airportId}")
    public ResponseEntity<AirportResponse> updateAirport(@PathVariable Long airportId, @Valid @RequestBody AirportRequest airportRequest) throws Exception {
        AirportResponse airportResponse = airportService.updateAirport(airportId, airportRequest);
        return new  ResponseEntity<>(airportResponse, HttpStatus.OK);
    }

    @GetMapping("/getAllAirports")
    public ResponseEntity<List<AirportResponse>> getAllAirports() throws Exception {
        List<AirportResponse> airportResponseList = airportService.getAllAirports();
        return new  ResponseEntity<>(airportResponseList, HttpStatus.OK);
    }

    @GetMapping("/getAirport/{airportId}")
    public ResponseEntity<AirportResponse> getAirport(@PathVariable Long airportId) throws Exception {
        AirportResponse airportResponse = airportService.getAirportById(airportId);
        return new  ResponseEntity<>(airportResponse, HttpStatus.OK);
    }

    @GetMapping("/getAirport/city/{cityId}")
    public ResponseEntity<List<AirportResponse>> getAirportByCityId(@PathVariable Long cityId) throws Exception {
        List<AirportResponse> airportResponse = airportService.getAirportsByCityId(cityId);
        return new  ResponseEntity<>(airportResponse, HttpStatus.OK);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<ApiResponse> deleteAirport(@RequestParam Long airportId) throws Exception {
        airportService.deleteAirportById(airportId);
        return new ResponseEntity<>(new ApiResponse("Airport deleted successfully"), HttpStatus.OK);
    }
}
