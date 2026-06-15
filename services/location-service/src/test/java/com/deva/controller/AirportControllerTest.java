package com.deva.controller;

import com.deva.payload.request.AirportRequest;
import com.deva.payload.response.AirportResponse;
import com.deva.service.AirportService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AirportController.class)
class AirportControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private AirportService airportService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void createAirport_returnsCreated() throws Exception {

        AirportRequest request = new AirportRequest();

        AirportResponse response = new AirportResponse();
        response.setId(1L);
        response.setName("Indira Gandhi International Airport");

        when(airportService.createAirport(any(AirportRequest.class)))
                .thenReturn(response);

        mockMvc.perform(post("/api/locations/airports/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.airportId").value(1L));
    }

    @Test
    void updateAirport_returnsUpdatedAirport() throws Exception {

        AirportRequest request = new AirportRequest();

        AirportResponse response = new AirportResponse();
        response.setId(1L);
        response.setName("Updated Airport");

        when(airportService.updateAirport(eq(1L), any(AirportRequest.class)))
                .thenReturn(response);

        mockMvc.perform(put("/api/locations/airports/update/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.airportName")
                        .value("Updated Airport"));
    }

    @Test
    void getAllAirports_returnsAirportList() throws Exception {

        AirportResponse airport = new AirportResponse();
        airport.setId(1L);
        airport.setName("Delhi Airport");

        when(airportService.getAllAirports())
                .thenReturn(List.of(airport));

        mockMvc.perform(get("/api/locations/airports/getAllAirports"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].airportId").value(1L))
                .andExpect(jsonPath("$[0].airportName")
                        .value("Delhi Airport"));
    }

    @Test
    void getAirportById_returnsAirport() throws Exception {

        AirportResponse response = new AirportResponse();
        response.setId(1L);
        response.setName("Mumbai Airport");

        when(airportService.getAirportById(1L))
                .thenReturn(response);

        mockMvc.perform(get("/api/locations/airports/getAirport/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.airportId").value(1L))
                .andExpect(jsonPath("$.airportName")
                        .value("Mumbai Airport"));
    }

    @Test
    void getAirportByCityId_returnsAirportList() throws Exception {

        AirportResponse response = new AirportResponse();
        response.setId(1L);
        response.setName("Patna Airport");

        when(airportService.getAirportsByCityId(1L))
                .thenReturn(List.of(response));

        mockMvc.perform(get("/api/locations/airports/getAirport/city/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].airportId").value(1L))
                .andExpect(jsonPath("$[0].airportName")
                        .value("Patna Airport"));
    }

    @Test
    void deleteAirport_returnsSuccessMessage() throws Exception {

        doNothing().when(airportService)
                .deleteAirportById(1L);

        mockMvc.perform(delete("/api/locations/airports/delete")
                        .param("airportId", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message")
                        .value("Airport deleted successfully"));
    }
}