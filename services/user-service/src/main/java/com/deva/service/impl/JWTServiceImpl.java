package com.deva.service.impl;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.deva.model.User;
import com.deva.service.JWTService;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.util.Date;

@Service
public class JWTServiceImpl implements JWTService {

    @Value("${jwt.algorithm.key}")
    private String algorithmKey;

    @Value("${jwt.issuer.name}")
    private String issuer;

    @Value("${jwt.expiry.duration}")
    private int expiration;

    private static final String USER_NAME = "username";
    private Algorithm algorithm;

    @PostConstruct
    public void init() throws UnsupportedEncodingException {
        if (algorithmKey == null || algorithmKey.trim().isEmpty()) {
            throw new IllegalStateException("JWT secret key must be configured via jwt.algorithm.key property");
        }
        algorithm = Algorithm.HMAC256(algorithmKey);
    }

    @Override
    public String generateToken(User user) {
        return JWT.create()
                .withClaim(USER_NAME, user.getEmail())
                .withIssuer(issuer)
                .withIssuedAt(new Date(System.currentTimeMillis()))
                .withExpiresAt(new Date(System.currentTimeMillis() + expiration))
                .sign(algorithm);
    }

    @Override
    public String extractUsername(String token) {
        try {
            DecodedJWT decodedJWT = JWT.require(algorithm).withIssuer(issuer).build().verify(token);
            return decodedJWT.getClaim(USER_NAME).asString();
        } catch (JWTVerificationException e) {
            throw new IllegalArgumentException("Invalid or expired JWT token", e);
        }
    }

}
