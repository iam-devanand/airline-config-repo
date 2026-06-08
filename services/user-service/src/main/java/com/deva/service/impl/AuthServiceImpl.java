package com.deva.service.impl;

import com.deva.enums.UserRole;
import com.deva.model.User;
import com.deva.payload.dto.UserDTO;
import com.deva.payload.request.LoginRequest;
import com.deva.payload.response.AuthResponse;
import com.deva.repository.UserRepository;
import com.deva.service.AuthService;
import com.deva.service.JWTService;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final JWTService jwtService;

    public AuthServiceImpl(UserRepository userRepository, JWTService jwtService) {
        this.userRepository = userRepository;
        this.jwtService = jwtService;
    }

    @Override
    public AuthResponse login(LoginRequest loginRequest) {
        User user = userRepository.findByEmail(loginRequest.getEmail());
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }

        boolean checkedPassword = BCrypt.checkpw(loginRequest.getPassword(), user.getPassword());
        if (checkedPassword) {
            String token = jwtService.generateToken(user);

            return AuthResponse.builder()
                    .title("Welcome Back " + user.getFullName())
                    .message("Login successful")
                    .jwtToken(token)
                    .user(UserDTO.builder()
                            .id(user.getId())
                            .email(user.getEmail())
                            .password(user.getPassword())
                            .phoneNumber(user.getMobileNumber())
                            .role(user.getUserRole())
                            .lastLogin(LocalDateTime.now())
                            .fullName(user.getFullName())
                            .build())
                    .build();
        }else {
            throw new BadCredentialsException("Bad credentials");
        }

    }

    @Override
    public AuthResponse signUp(UserDTO userDTO) throws Exception {
        User user = userRepository.findByEmail(userDTO.getEmail());
        if (user != null) {
            throw new Exception("Email already registered.");
        }

        if (userDTO.getRole() == UserRole.ROLE_SYSTEM_ADMIN){
            throw new Exception("You are not allowed to perform signUp with system admin!");
        }

        String password = BCrypt.hashpw(userDTO.getPassword(), BCrypt.gensalt(8));

        User newUser = User.builder()
                .email(userDTO.getEmail())
                .fullName(userDTO.getFullName())
                .password(password)
                .mobileNumber(userDTO.getPhoneNumber())
                .userRole(userDTO.getRole())
                .lastLogin(LocalDateTime.now())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        User savedUser = userRepository.save(newUser);

        return AuthResponse.builder()
                .message("User registered successfully!")
                .title("Welcome " + savedUser.getFullName())
                .jwtToken(jwtService.generateToken(savedUser))
                .user(UserDTO.builder()
                        .id(savedUser.getId())
                        .email(savedUser.getEmail())
                        .fullName(savedUser.getFullName())
                        .password(savedUser.getPassword())
                        .role(savedUser.getUserRole())
                        .phoneNumber(String.valueOf(savedUser.getMobileNumber()))
                        .build())

                .build();
    }

}
