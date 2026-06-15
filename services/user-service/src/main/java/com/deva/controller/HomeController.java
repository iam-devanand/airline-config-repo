package com.deva.controller;

import com.deva.payload.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "Health Check", description = "Service health and test endpoints")
public class HomeController {

    @Operation(summary = "Test endpoint", description = "Simple endpoint to verify service is running (requires authentication)")
    @SecurityRequirement(name = "bearerAuth")
    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Service is running")
    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "401", description = "Unauthorized - valid JWT required")
    @GetMapping("/api/users/test")
    public ApiResponse home() {
        return new ApiResponse("Hey! everyone i am user-service of airline system");
    }

}