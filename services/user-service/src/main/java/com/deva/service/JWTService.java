package com.deva.service;

import com.deva.model.User;

public interface JWTService {
    String generateToken(User user);

    String extractUsername(String token);
}
