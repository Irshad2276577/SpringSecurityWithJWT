package com.security.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.security.Entity.Customer;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
// 1. Creating JWT Class
public class JWTService {
    // Secret key for HMAC256 algorithm
    private final String algorithmKey = "abchjsd-jsjajk-sjkjsd";
    // Token expiry duration set to 10 days (in milliseconds)
    private final int expiryDuration = 864000000;
    // Issuer of the token
    private final String issuer = "irshad";
    // Algorithm variable for JWT signing
    private Algorithm algorithm;

    // Constants for claim names
    private final static String USERNAME = "username";
    private final static String USERROLE = "role";

    // Set Algorithm
    @PostConstruct
    // Method to initialize the algorithm after bean creation
    private void PostConstruct() {
        algorithm = Algorithm.HMAC256(algorithmKey);
    }

    // Generate Token
    // Method to create a JWT token for a given customer
    public String generateToken(Customer customer) {
        return JWT.create().withIssuer(issuer)
                .withClaim(USERNAME, customer.getEmail()) // Add username claim
                .withClaim(USERROLE, customer.getRole())   // Add role claim
                .withExpiresAt(new Date(System.currentTimeMillis() + expiryDuration)) // Set expiration
                .sign(algorithm); // Sign the token
    }

    // Extract username from JWT token
    // Method to retrieve the username from the token
    public String getUsername(String token) {
        DecodedJWT verify = JWT.require(algorithm).withIssuer(issuer).build().verify(token);
        return verify.getClaim(USERNAME).asString(); // Return the username claim
    }

    // Extracting role from token
    // Uncomment the method below to enable role extraction from the token
    // public String getRole(String token) {
    //     DecodedJWT verify = JWT.require(algorithm).withIssuer(issuer).build().verify(token);
    //     return verify.getClaim(USERROLE).asString(); // Return the role claim
    // }
}
