package com.deva.payload.response;

import com.deva.payload.dto.UserDTO;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuthResponse {

    private String jwtToken;
    private String message;
    private UserDTO user;
    private String title;

}
