package com.myproject.library_manager.payload;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuthResponse {
    private String accessToken;
    private String refreshToken;
    private String email;
    private String name;         // User.name
    private String avatarUrl;    // User.avatarUrl
    private String role;         // User.role
    private String status;       // User.status
}