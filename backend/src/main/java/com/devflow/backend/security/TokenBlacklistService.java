package com.devflow.backend.security;

import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class TokenBlacklistService {

    private final Set<String> blacklistedTokensIds =
            ConcurrentHashMap.newKeySet();

    public void blacklistToken(String tokenId) {
        blacklistedTokensIds.add(tokenId);
    }

    public boolean isBlacklisted(String tokenId) {
        return blacklistedTokensIds.contains(tokenId);
    }
}