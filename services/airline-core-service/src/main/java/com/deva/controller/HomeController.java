package com.deva.controller;

import com.deva.payload.response.ApiResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    @GetMapping("/api/airlines/home")
    public ApiResponse home() {
        return new ApiResponse("Hey! everyone i am airline core service & I'll manage " +
                "airlines, aircraft fleet, aircraft models, and operational inventory for the airline system.");
    }

}
