package com.task.manager.service;

import com.task.manager.entity.RefreshToken;
import com.task.manager.entity.Role;
import com.task.manager.entity.User;
import com.task.manager.exception.ApiException;
import com.task.manager.exception.ErrorMessage;
import com.task.manager.payload.AuthResponse;
import com.task.manager.payload.LoginRequest;
import com.task.manager.payload.RegisterRequest;
import com.task.manager.repository.RefreshTokenRepository;
import com.task.manager.repository.UserRepository;
import com.task.manager.security.JwtUtils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Set;

@Service
public class AuthService {
    private final UserRepository userRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;
    private final PasswordEncoder passwordEncoder;

    public AuthService(UserRepository userRepository, RefreshTokenRepository refreshTokenRepository,
                       AuthenticationManager authenticationManager, JwtUtils jwtUtils,
                       PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.refreshTokenRepository = refreshTokenRepository;
        this.authenticationManager = authenticationManager;
        this.jwtUtils = jwtUtils;
        this.passwordEncoder = passwordEncoder;
    }

    public AuthResponse register(RegisterRequest req) {
        if (userRepository.existsByEmail(req.email())) {
            throw new ApiException(ErrorMessage.EMAIL_ALREADY_EXISTS);
        }
        User user = User.builder()
                .email(req.email())
                .fullName(req.fullName())
                .password(passwordEncoder.encode(req.password()))
                .roles(Set.of(Role.ROLE_USER))
                .provider("local")
                .build();
        userRepository.save(user);
        return createAndSaveTokens(user.getEmail(), user);
    }

    public AuthResponse login(LoginRequest req) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(req.email(), req.password()));
        User user = userRepository.findByEmail(req.email()).orElseThrow(() -> new ApiException(ErrorMessage.USER_NOT_FOUND));
        return createAndSaveTokens(user.getEmail(), user);
    }

    private AuthResponse createAndSaveTokens(String email, User user) {
        String access = jwtUtils.generateAccessToken(user);
        String refresh = jwtUtils.generateRefreshToken(email);

        RefreshToken rt = RefreshToken.builder()
                .token(refresh)
                .user(user)
                .expiresAt(Instant.now().plusMillis(jwtUtils.getRefreshExpirationMs()))
                .revoked(false)
                .build();
        refreshTokenRepository.save(rt);

        return new AuthResponse(access, refresh, "Bearer");
    }

    public AuthResponse refresh(String refreshToken) {
        RefreshToken rt = refreshTokenRepository.findByToken(refreshToken)
                .orElseThrow(() -> new ApiException(ErrorMessage.INVALID_TOKEN));


        if (rt.isRevoked() || rt.getExpiresAt().isBefore(Instant.now())) {
            throw new ApiException(ErrorMessage.INVALID_TOKEN);
        }

        String newAccess = jwtUtils.generateAccessToken(rt.getUser());
        return new AuthResponse(newAccess, refreshToken, "Bearer");
    }

    public void revokeRefreshTokensForUser(Long userId) {
        refreshTokenRepository.deleteByUserId(userId);
    }
}
