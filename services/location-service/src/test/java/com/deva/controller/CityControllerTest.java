package com.deva.controller;

import com.deva.payload.request.CityRequest;
import com.deva.payload.response.ApiResponse;
import com.deva.payload.response.CityResponse;
import com.deva.service.CityService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CityController.class)
class CityControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private CityService cityService;

    @Test
    void createCity_returnsCreatedCity() throws Exception {
        CityRequest request = CityRequest.builder()
                .name("New York")
                .cityCode("JFK")
                .countryCode("US")
                .countryName("United States")
                .build();

        CityResponse response = CityResponse.builder()
                .id(1L)
                .name("New York")
                .cityCode("JFK")
                .countryCode("US")
                .countryName("United States")
                .build();

        when(cityService.createCity(any(CityRequest.class))).thenReturn(response);

        mockMvc.perform(post("/api/locations/cities/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "name": "New York",
                                    "cityCode": "JFK",
                                    "countryCode": "US",
                                    "countryName": "United States"
                                }
                                """))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("New York"))
                .andExpect(jsonPath("$.cityCode").value("JFK"));
    }

    @Test
    void updateCity_returnsUpdatedCity() throws Exception {
        CityRequest request = CityRequest.builder()
                .name("New York Updated")
                .cityCode("JFK")
                .countryCode("US")
                .countryName("United States")
                .build();

        CityResponse response = CityResponse.builder()
                .id(1L)
                .name("New York Updated")
                .cityCode("JFK")
                .countryCode("US")
                .countryName("United States")
                .build();

        when(cityService.updateCity(eq(1L), any(CityRequest.class))).thenReturn(response);

        mockMvc.perform(put("/api/locations/cities/update/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "name": "New York Updated",
                                    "cityCode": "JFK",
                                    "countryCode": "US",
                                    "countryName": "United States"
                                }
                                """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("New York Updated"));
    }

    @Test
    void getCityById_returnsCity() throws Exception {
        CityResponse response = CityResponse.builder()
                .id(1L)
                .name("New York")
                .cityCode("JFK")
                .countryCode("US")
                .countryName("United States")
                .build();

        when(cityService.getCityById(1L)).thenReturn(response);

        mockMvc.perform(get("/api/locations/cities/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("New York"));
    }

    @Test
    void getAllCities_returnsPagedCities() throws Exception {
        CityResponse city1 = CityResponse.builder()
                .id(1L)
                .name("New York")
                .cityCode("JFK")
                .countryCode("US")
                .countryName("United States")
                .build();

        CityResponse city2 = CityResponse.builder()
                .id(2L)
                .name("Los Angeles")
                .cityCode("LAX")
                .countryCode("US")
                .countryName("United States")
                .build();

        Page<CityResponse> page = new PageImpl<>(List.of(city1, city2), PageRequest.of(0, 20), 2);

        when(cityService.getAllCity(any())).thenReturn(page);

        mockMvc.perform(get("/api/locations/cities"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").isArray())
                .andExpect(jsonPath("$.content.length()").value(2))
                .andExpect(jsonPath("$.content[0].name").value("New York"));
    }

    @Test
    void deleteCity_returnsSuccessMessage() throws Exception {

        doNothing().when(cityService).deleteCity(1L);

        mockMvc.perform(delete("/api/locations/cities/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message")
                        .value("City deleted successfully."));
    }

    @Test
    void searchCities_returnsPagedCities() throws Exception {
        CityResponse city1 = CityResponse.builder()
                .id(1L)
                .name("New York")
                .cityCode("JFK")
                .countryCode("US")
                .countryName("United States")
                .build();

        Page<CityResponse> page = new PageImpl<>(List.of(city1), PageRequest.of(0, 20), 1);

        when(cityService.searchCities(any(), anyString())).thenReturn(page);

        mockMvc.perform(get("/api/locations/cities/search")
                        .param("keyword", "New"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").isArray())
                .andExpect(jsonPath("$.content.length()").value(1))
                .andExpect(jsonPath("$.content[0].name").value("New York"));
    }

    @Test
    void getCitiesByCountryCode_returnsPagedCities() throws Exception {
        CityResponse city1 = CityResponse.builder()
                .id(1L)
                .name("New York")
                .cityCode("JFK")
                .countryCode("US")
                .countryName("United States")
                .build();

        Page<CityResponse> page = new PageImpl<>(List.of(city1), PageRequest.of(0, 20), 1);

        when(cityService.getCitiesByCountryCode(any(), anyString())).thenReturn(page);

        mockMvc.perform(get("/api/locations/cities/country/US"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").isArray())
                .andExpect(jsonPath("$.content.length()").value(1))
                .andExpect(jsonPath("$.content[0].countryCode").value("US"));
    }

    @Test
    void cityCityExists_returnsBoolean() throws Exception {
        when(cityService.cityExists(anyString())).thenReturn(true);

        mockMvc.perform(get("/api/locations/cities/exists/JFK"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(true));
    }
}