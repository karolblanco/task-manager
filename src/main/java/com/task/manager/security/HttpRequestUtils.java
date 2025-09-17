package com.task.manager.security;

import jakarta.servlet.http.HttpServletRequest;

public class HttpRequestUtils {
    public static String extractToken(HttpServletRequest request) {
        String bearer = request.getHeader("Authorization");
        return (bearer != null && bearer.startsWith("Bearer ")) ? bearer.substring(7) : null;
    }
}
