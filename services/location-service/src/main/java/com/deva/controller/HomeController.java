package com.deva.controller;

import com.deva.payload.response.ApiResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    @GetMapping("/api/locations/home")
    public ApiResponse Home() {
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setMessage("Hello everyone I'm Location Service of airline microservices");
        return apiResponse;
    }

}
