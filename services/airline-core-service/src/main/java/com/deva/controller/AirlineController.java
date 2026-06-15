package com.deva.controller;

import com.deva.enums.AirlineStatus;
import com.deva.payload.request.AirlineRequest;
import com.deva.payload.response.AirlineDropdownItem;
import com.deva.payload.response.AirlineResponse;
import com.deva.payload.response.ApiResponse;
import com.deva.service.AirlineService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/airlines")
@Tag(name = "Airline Management", description = "Airline CRUD operations and management")
public class AirlineController {

    private final AirlineService airlineService;

    public AirlineController(AirlineService airlineService) {
        this.airlineService = airlineService;
    }

    @Operation(summary = "Create new airline", description = "Creates a new airline for the authenticated owner")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "201", description = "Airline created successfully",
                    content = @Content(schema = @Schema(implementation = AirlineResponse.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Invalid input"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    @PostMapping("/addAirline/{ownerId}")
    public ResponseEntity<AirlineResponse> addAirline(
            @RequestBody AirlineRequest airlineRequest,
            @Parameter(description = "Owner ID from header", required = true) @RequestHeader("X-User-Id") Long ownerId) {
        AirlineResponse airline = airlineService.createAirline(airlineRequest, ownerId);
        return new ResponseEntity<>(airline, HttpStatus.CREATED);
    }

    @Operation(summary = "Update airline", description = "Updates an existing airline by owner")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Airline updated successfully",
                    content = @Content(schema = @Schema(implementation = AirlineResponse.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Invalid input"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Airline not found")
    })
    @PutMapping("/updateAirline/{ownerId}")
    public ResponseEntity<AirlineResponse> updateAirline(
            @RequestBody AirlineRequest airlineRequest,
            @Parameter(description = "Owner ID", required = true) @PathVariable Long ownerId) throws Exception {
        AirlineResponse updatedAirline = airlineService.updateAirline(ownerId, airlineRequest);
        return new ResponseEntity<>(updatedAirline, HttpStatus.OK);
    }

    @Operation(summary = "Get airline by owner", description = "Retrieves airline owned by the specified owner")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Airline found",
                    content = @Content(schema = @Schema(implementation = AirlineResponse.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Airline not found")
    })
    @GetMapping("/getAirline/owner/{ownerId}")
    public ResponseEntity<AirlineResponse> getAirlineByOwner(
            @Parameter(description = "Owner ID", required = true) @PathVariable Long ownerId) throws Exception {
        AirlineResponse airlineByOwner = airlineService.getAirlineByOwner(ownerId);
        return new ResponseEntity<>(airlineByOwner, HttpStatus.OK);
    }

    @Operation(summary = "Get airline by ID", description = "Retrieves airline by its ID")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Airline found",
                    content = @Content(schema = @Schema(implementation = AirlineResponse.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Airline not found")
    })
    @GetMapping("/getAirline/{airlineId}")
    public ResponseEntity<AirlineResponse> getAirlineById(
            @Parameter(description = "Airline ID", required = true) @PathVariable Long airlineId) throws Exception {
        AirlineResponse airlineById = airlineService.getAirlineById(airlineId);
        return new ResponseEntity<>(airlineById, HttpStatus.OK);
    }

    @Operation(summary = "Get all airlines (paginated)", description = "Returns paginated list of all airlines")
    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "List of airlines",
            content = @Content(schema = @Schema(implementation = Page.class)))
    @GetMapping("/getAllAirlines")
    public ResponseEntity<Page<AirlineResponse>> getAllAirlines(Pageable pageable) {
        Page<AirlineResponse> airlinePage = airlineService.getAllAirlines(pageable);
        return new ResponseEntity<>(airlinePage, HttpStatus.OK);
    }

    @Operation(summary = "Change airline status (admin)", description = "Admin endpoint to change airline status")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Status changed successfully",
                    content = @Content(schema = @Schema(implementation = AirlineResponse.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Airline not found")
    })
    @PutMapping("/changeStatus/{airlineId}")
    public ResponseEntity<AirlineResponse> changeStatusById(
            @Parameter(description = "Airline ID", required = true) @PathVariable Long airlineId,
            @RequestBody AirlineStatus airlineStatus) {
        AirlineResponse airlineResponse = airlineService.changeStatusByAdmin(airlineId, airlineStatus);
        return new ResponseEntity<>(airlineResponse, HttpStatus.OK);
    }

    @Operation(summary = "Get airline dropdown", description = "Returns list of airlines for dropdown selection")
    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "List of airlines for dropdown",
            content = @Content(schema = @Schema(implementation = AirlineDropdownItem.class)))
    @GetMapping("/getAirlineDropdown")
    public ResponseEntity<List<AirlineDropdownItem>> getAirlineDropdown() {
        List<AirlineDropdownItem> airlineDropdown = airlineService.getAirlineDropdown();
        return new ResponseEntity<>(airlineDropdown, HttpStatus.OK);
    }

    @Operation(summary = "Delete airline", description = "Deletes an airline by owner")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Airline deleted successfully",
                    content = @Content(schema = @Schema(implementation = ApiResponse.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Airline not found")
    })
    @DeleteMapping("/deleteAirline")
    public ResponseEntity<ApiResponse> deleteAirline(
            @Parameter(description = "Airline ID", required = true) @RequestParam Long airlineId,
            @Parameter(description = "Owner ID", required = true) @RequestParam Long ownerId) {
        airlineService.deleteAirline(airlineId, ownerId);
        return new ResponseEntity<>(new ApiResponse("Airline deleted successfully"), HttpStatus.OK);
    }

}