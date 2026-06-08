package com.deva.payload.request;

import com.deva.enums.AirlineStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AirlineRequest {

    @NotBlank(message = "iata code is mandatory and not should be blank.")
    @Size(min = 2, max = 2, message = "iata code must be exactly 2 characters.")
    private String iataCode;

    @NotBlank(message = "iaco code is mandatory and not should be blank.")
    @Size(min = 2, max = 2, message = "iaco code must be exactly 2 characters.")
    private String icaoCode;

    @NotBlank(message = "Airline name is mandatory and should not be blank.")
    private String name;

    @NotBlank
    private String alias;

    private AirlineStatus status;

    @NotBlank
    private String logoUrl;

    @NotBlank
    private String website;

    private String alliance;

    private Long headquartersCityId;

    private String supportEmail;
    private String supportPhone;
    private String supportHours;


}

