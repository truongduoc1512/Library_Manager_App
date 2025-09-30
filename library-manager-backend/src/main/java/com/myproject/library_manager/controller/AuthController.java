package com.myproject.library_manager.controller;

import com.myproject.library_manager.payload.LoginRequest;
import com.myproject.library_manager.security.JwtUtils;
import com.myproject.library_manager.payload.AuthResponse;
import com.myproject.library_manager.dto.RegisterRequest;
import com.myproject.library_manager.model.User;
import com.myproject.library_manager.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final JwtUtils jwtUtils;

    // ===== Đăng ký email/password =====
    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody RegisterRequest request) {
        User user = authService.register(request);
        return ResponseEntity.ok(user);
    }

    // ===== Đăng nhập email/password =====
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request) {
        AuthResponse response = authService.login(request);
        return ResponseEntity.ok(response);
    }

    // ===== Refresh Token =====
    @PostMapping("/refresh")
    public ResponseEntity<AuthResponse> refresh(@RequestParam String refreshToken) {
        if (jwtUtils.validateJwtToken(refreshToken)) {
            String email = jwtUtils.getEmailFromJwtToken(refreshToken);
            String newAccessToken = jwtUtils.generateJwtToken(email);

            AuthResponse response = AuthResponse.builder()
                    .accessToken(newAccessToken)
                    .refreshToken(refreshToken)
                    .email(email)
                    .build();

            return ResponseEntity.ok(response);
        } else {
            throw new RuntimeException("Refresh token không hợp lệ!");
        }
    }

    // ===== Đăng nhập Google =====
    @GetMapping("/login/google")
    public Map<String, Object> currentUser(
            org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken authentication
    ) {
        return authentication.getPrincipal().getAttributes();
    }
}