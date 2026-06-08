package com.deva.controller;

import com.deva.payload.response.ApiResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    @GetMapping("/api/users/test")
    public ApiResponse home() {
        return new ApiResponse("Hey! everyone i am user-service of airline system");
    }

}
