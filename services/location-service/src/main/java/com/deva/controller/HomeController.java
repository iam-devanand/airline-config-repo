package com.deva.controller;

import com.deva.payload.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "Health Check", description = "Service health and test endpoints")
public class HomeController {

    @Operation(summary = "Service info", description = "Returns service information")
    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Service information")
    @GetMapping("/api/locations/home")
    public ApiResponse home() {
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setMessage("Hello everyone I'm Location Service of airline microservices");
        return apiResponse;
    }

}
