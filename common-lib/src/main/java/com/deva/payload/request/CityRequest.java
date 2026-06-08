package com.deva.payload.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CityRequest {

    @NotBlank(message = "City name is required.")
    @Size(min = 1, max = 50)
    private String name;

    @NotBlank(message = "City code is required.")
    @Size(min = 1, max = 50)
    private String cityCode;

    @NotBlank(message = "Country code is required.")
    @Size(min = 1, max = 50)
    private  String countryCode;

    @Size(min = 1, max = 50)
    private String timeZoneOffset;

    @NotBlank(message = "Country name is required.")
    @Size(min = 1, max = 50)
    private String countryName;

    @Size(min = 1, max = 50)
    private String regionCode;

}
