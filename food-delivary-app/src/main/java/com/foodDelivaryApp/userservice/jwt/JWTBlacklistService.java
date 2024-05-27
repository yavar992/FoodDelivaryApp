package com.foodDelivaryApp.userservice.jwt;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class JWTBlacklistService {

    // Map to store blacklisted tokens and their expiry times
    private final Map<String, LocalDateTime> blacklist = new ConcurrentHashMap<>();

    // Method to add a token to the blacklist
    public void blacklistToken(String token, LocalDateTime expiryTime) {
        blacklist.put(token, expiryTime);
    }

    // Method to check if a token is blacklisted
    public boolean isTokenBlacklisted(String token) {
        LocalDateTime expiryTime = blacklist.get(token);
        if (expiryTime == null) {
            return false;
        }
        // Remove expired tokens from the blacklist
        if (expiryTime.isBefore(LocalDateTime.now())) {
            blacklist.remove(token);
            return false;
        }
        return true;
    }

}
