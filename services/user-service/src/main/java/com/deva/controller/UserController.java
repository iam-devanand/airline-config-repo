package com.deva.controller;

import com.deva.payload.dto.UserDTO;
import com.deva.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PutMapping("/updateUser/{userId}")
    public ResponseEntity<UserDTO> updateUser(@PathVariable Long userId, @RequestBody UserDTO userDTO) {
        UserDTO user = userService.updateUser(userId, userDTO);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @GetMapping("/getUser/{userId}")
    public ResponseEntity<UserDTO> getUser(@PathVariable Long userId) {
        UserDTO user = userService.getUserById(userId);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @GetMapping("/getAllUsers")
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        List<UserDTO> users = userService.getAllUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @DeleteMapping("/delete/userId")
    public ResponseEntity<String> deleteUser(@PathVariable Long userId) {
        userService.deleteUser();
        return new ResponseEntity<>("User deleted successfully.",HttpStatus.NO_CONTENT);
    }
}
