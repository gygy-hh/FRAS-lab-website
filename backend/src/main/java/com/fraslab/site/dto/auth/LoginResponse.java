package com.fraslab.site.dto.auth;

public record LoginResponse(
        String token,
        long expireSeconds,
        String username
) {
}
