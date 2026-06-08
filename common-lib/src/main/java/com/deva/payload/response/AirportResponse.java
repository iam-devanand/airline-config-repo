package com.deva.payload.response;

import com.deva.embeddable.Address;
import com.deva.embeddable.GeoCode;
import lombok.*;

import java.time.ZoneId;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AirportResponse {

    private Long id;
    private String iataCode;
    private String name;
    private String detailedName;
    private ZoneId timeZone;
    private Address address;
    private GeoCode geoCode;

    private CityResponse city;

}
