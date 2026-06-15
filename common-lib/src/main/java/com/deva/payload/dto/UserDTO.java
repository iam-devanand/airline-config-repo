package com.deva.payload.dto;

import com.deva.enums.UserRole;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDTO {

    private Long id;
    private String email;
    private String fullName;
    private String phoneNumber;
    private String password;
    private UserRole role;
    private LocalDateTime lastLogin;

}
