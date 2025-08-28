package com.task.manager.payload;

public record AuthResponse(String accessToken, String refreshToken, String tokenType) {
}
