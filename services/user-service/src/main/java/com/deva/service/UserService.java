package com.deva.service;

import com.deva.payload.dto.UserDTO;

import java.util.List;

public interface UserService {
    UserDTO updateUser(Long userId, UserDTO userDTO);

    UserDTO getUserById(Long userId);

    List<UserDTO> getAllUsers();

    void deleteUser();
}
