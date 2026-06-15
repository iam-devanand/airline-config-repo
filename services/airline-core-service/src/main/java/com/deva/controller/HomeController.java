package com.deva.controller;

import com.deva.payload.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "Health Check", description = "Service health and test endpoints")
public class HomeController {

    @Operation(
            summary = "Service info",
            description = "Returns service information"
    )
    @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "200",
            description = "Service information"
    )
    @GetMapping("/api/airlines/home")
    public ApiResponse home() {
        return new ApiResponse(
                "Hey! everyone i am airline core service & I'll manage airlines, aircraft fleet, aircraft models, and operational inventory for the airline system."
        );
    }
}
