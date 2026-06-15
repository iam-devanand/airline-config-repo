package com.deva.controller;

import com.deva.payload.response.ApiResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(HomeController.class)
class HomeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void home_returnsServiceInfo() throws Exception {
        mockMvc.perform(get("/api/locations/home"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Hello everyone I'm Location Service of airline microservices"));
    }
}