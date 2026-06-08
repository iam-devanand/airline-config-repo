package com.deva.payload.request;

import com.deva.embeddable.Address;
import com.deva.embeddable.GeoCode;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.ZoneId;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AirportRequest {

    @NotBlank(message = "IATA code is mandatory")
    @Size(min = 1, max = 3, message = "IATA code must be exactly 3 characters")
    private String iataCode;

    @NotBlank(message = "Airport name is mandatory")
    private String name;

    private ZoneId timeZone;

    @Valid
    private Address address;

    @NotNull(message = "City id is mandatory")
    private Long cityId;

    @Valid
    private GeoCode  geoCode;

}
