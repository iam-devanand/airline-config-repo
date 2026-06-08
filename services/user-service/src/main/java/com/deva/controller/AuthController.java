package com.deva.controller;

import com.deva.enums.UserRole;
import com.deva.payload.dto.UserDTO;
import com.deva.payload.request.LoginRequest;
import com.deva.payload.response.AuthResponse;
import com.deva.service.AuthService;
import com.deva.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class AuthController {

    private final UserService userService;
    private final AuthService authService;

    public AuthController(UserService userService, AuthService authService) {
        this.userService = userService;
        this.authService = authService;
    }

    @PostMapping("/sign-up")
    public ResponseEntity<AuthResponse> signUp(@RequestBody UserDTO userDTO) throws Exception {
        userDTO.setRole(UserRole.ROLE_USER);
        AuthResponse response = authService.signUp(userDTO);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest loginRequest) throws Exception {
        AuthResponse response = authService.login(loginRequest);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
