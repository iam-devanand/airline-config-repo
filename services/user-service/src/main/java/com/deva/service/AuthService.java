package com.deva.service;

import com.deva.payload.dto.UserDTO;
import com.deva.payload.request.LoginRequest;
import com.deva.payload.response.AuthResponse;

public interface AuthService {

    AuthResponse login(LoginRequest loginRequest);
    AuthResponse signUp(UserDTO userDTO) throws Exception;


}
