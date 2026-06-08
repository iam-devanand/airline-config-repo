package com.deva.payload.response;

import com.deva.embeddable.Support;
import com.deva.enums.AirlineStatus;
import com.deva.payload.dto.UserDTO;
import lombok.*;

import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AirlineResponse {

    private Long id;

    private String iataCode;
    private String icaoCode;

    private String name;
    private String alias;

    private String logoUrl;
    private String website;

    private AirlineStatus status;
    private String alliance;

    private Long ownerId;
    private UserDTO user;
    private Long updatedById;

    private CityResponse headquartersCity;
    private Support support;

    private Instant createdAt;
    private Instant updatedAt;

}
