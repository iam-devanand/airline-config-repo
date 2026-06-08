package com.deva.controller;

import com.deva.enums.AirlineStatus;
import com.deva.payload.request.AirlineRequest;
import com.deva.payload.response.AirlineDropdownItem;
import com.deva.payload.response.AirlineResponse;
import com.deva.payload.response.ApiResponse;
import com.deva.service.AirlineService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/airlines")
public class AirlineController {

    private final AirlineService airlineService;

    public AirlineController(AirlineService airlineService) {
        this.airlineService = airlineService;
    }

//    AirlineResponse createAirline(AirlineRequest airlineRequest, Long ownerId);
//    AirlineResponse updateAirline(Long ownerId, AirlineRequest airlineRequest) throws Exception;
//    AirlineResponse getAirlineByOwner(Long ownerId) throws Exception;
//    AirlineResponse getAirlineById(Long airlineId) throws Exception;
//    Page<AirlineResponse> getAllAirlines(Pageable pageable);
//    void deleteAirline(Long airlineId,  Long ownerId);
//
//    AirlineResponse changeStatusByAdmin(Long airlineId, AirlineStatus airlineStatus);
//    List<AirlineDropdownItem> getAirlineDropdown();

    @PostMapping("/addAirline/{ownerId}")
    public ResponseEntity<AirlineResponse> addAirline(@RequestBody AirlineRequest airlineRequest, @RequestHeader("X-User-Id") Long ownerId) {
        AirlineResponse airline = airlineService.createAirline(airlineRequest, ownerId);
        return new  ResponseEntity<>(airline, HttpStatus.CREATED);
    }

    @PutMapping("/updateAirline/{ownerId}")
    public ResponseEntity<AirlineResponse> updateAirline(@RequestBody AirlineRequest airlineRequest, @PathVariable Long ownerId) throws Exception {
        AirlineResponse updatedAirline = airlineService.updateAirline(ownerId, airlineRequest);
        return new  ResponseEntity<>(updatedAirline, HttpStatus.OK);
    }

    @GetMapping("/getAirline/owner/{ownerId}")
    public ResponseEntity<AirlineResponse> getAirlineByOwner(@PathVariable Long ownerId) throws Exception {
        AirlineResponse airlineByOwner = airlineService.getAirlineByOwner(ownerId);
        return new  ResponseEntity<>(airlineByOwner, HttpStatus.OK);
    }

    @GetMapping("/getAirline/{airlineId}")
    public ResponseEntity<AirlineResponse> getAirlineById(@PathVariable Long airlineId) throws Exception {
        AirlineResponse airlineById = airlineService.getAirlineById(airlineId);
        return new  ResponseEntity<>(airlineById, HttpStatus.OK);
    }

    @GetMapping("/getAllAirlines")
    public ResponseEntity<Page<AirlineResponse>> getAllAirlines(Pageable pageable) {
        Page<AirlineResponse> airlinePage = airlineService.getAllAirlines(pageable);
        return new  ResponseEntity<>(airlinePage, HttpStatus.OK);
    }

    @PutMapping("/changeStatus/{airlineId}")
    public ResponseEntity<AirlineResponse> changeStatusById(@PathVariable Long airlineId, @RequestBody AirlineStatus airlineStatus) {
        AirlineResponse airlineResponse = airlineService.changeStatusByAdmin(airlineId, airlineStatus);
        return new  ResponseEntity<>(airlineResponse, HttpStatus.OK);
    }

    @GetMapping("/getAirlineDropdown")
    public ResponseEntity<List<AirlineDropdownItem>> getAirlineDropdown() {
        List<AirlineDropdownItem> airlineDropdown = airlineService.getAirlineDropdown();
        return new  ResponseEntity<>(airlineDropdown, HttpStatus.OK);
    }

    @DeleteMapping("/deleteAirline")
    public ResponseEntity<ApiResponse> deleteAirline(@RequestParam Long airlineId, @RequestParam Long ownerId) {
        airlineService.deleteAirline(airlineId, ownerId);
        return new ResponseEntity<>(new ApiResponse("Airline deleted successfully"), HttpStatus.OK);
    }
}
