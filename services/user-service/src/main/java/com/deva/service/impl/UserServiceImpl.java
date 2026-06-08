package com.deva.service.impl;

import com.deva.model.User;
import com.deva.payload.dto.UserDTO;
import com.deva.repository.UserRepository;
import com.deva.service.UserService;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Override
    public UserDTO updateUser(Long userId, UserDTO userDTO) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new RuntimeException("User not found with id " + userId)
        );

        user.setFullName(userDTO.getFullName());
        user.setEmail(userDTO.getEmail());
        user.setPassword(BCrypt.hashpw(userDTO.getPassword(), BCrypt.gensalt()));
        user.setMobileNumber(user.getMobileNumber());
        user.setUpdatedAt(LocalDateTime.now());
        user.setUserRole(userDTO.getRole());

        User updatedUser = userRepository.save(user);

        return UserDTO.builder()
                .id(updatedUser.getId())
                .email(updatedUser.getEmail())
                .fullName(updatedUser.getFullName())
                .password(updatedUser.getPassword())
                .phoneNumber(updatedUser.getMobileNumber())
                .role(updatedUser.getUserRole())
                .lastLogin(updatedUser.getLastLogin())
                .build();
    }

    @Override
    public UserDTO getUserById(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new RuntimeException("User not found with id " + userId)
        );

        return UserDTO.builder()
                .id(user.getId())
                .email(user.getEmail())
                .fullName(user.getFullName())
                .password(user.getPassword())
                .phoneNumber(user.getMobileNumber())
                .role(user.getUserRole())
                .lastLogin(user.getLastLogin())
                .build();
    }

    @Override
    public List<UserDTO> getAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream().map(user -> UserDTO.builder()
                .id(user.getId())
                .email(user.getEmail())
                .fullName(user.getFullName())
                .phoneNumber(user.getMobileNumber())
                .role(user.getUserRole())
                .lastLogin(user.getLastLogin())
                .password(user.getPassword())
                .build()).collect(Collectors.toList());
    }

    @Override
    public void deleteUser() {
        User user = userRepository.findById(1L).orElseThrow(
                () -> new RuntimeException("User not found with id " + 1L)
        );

        userRepository.delete(user);
    }
}
