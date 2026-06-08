package com.deva.payload.response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AirlineDropdownItem {

    private Long id;
    private String name;
    private String iataCode;
    private String icaoCode;
    private String logoUrl;
    private String country;


}
