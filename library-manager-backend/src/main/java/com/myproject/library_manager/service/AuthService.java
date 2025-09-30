package com.myproject.library_manager.service;

import com.myproject.library_manager.dto.RegisterRequest;
import com.myproject.library_manager.model.User;
import com.myproject.library_manager.model.enums.AuthProvider;
import com.myproject.library_manager.model.enums.Role;
import com.myproject.library_manager.model.enums.UserStatus;
import com.myproject.library_manager.payload.LoginRequest;
import com.myproject.library_manager.payload.AuthResponse;
import com.myproject.library_manager.repository.UserRepository;
import com.myproject.library_manager.security.JwtUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;

    // ===== Đăng ký =====
    public User register(RegisterRequest request) {
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new RuntimeException("Email đã tồn tại!");
        }

        User user = User.builder()
                .email(request.getEmail())
                .fullName(request.getName()) // trong User là "name"
                .passWord(passwordEncoder.encode(request.getPassword())) // trong User là "passWord"
                .authProvider(AuthProvider.LOCAL) // mặc định LOCAL khi đăng ký tay
                .role(Role.STUDENT)              // mặc định là STUDENT, bạn có thể tùy chỉnh
                .status(UserStatus.ACTIVE)       // mặc định ACTIVE khi mới tạo
                .phoneNumber(request.getPhoneNumber())
                .avatarUrl(request.getAvatarUrl()) // có thể null
                .build();

        return userRepository.save(user);
    }

    // ===== Đăng nhập =====
    public AuthResponse login(LoginRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("User không tồn tại!"));

        if (user.getAuthProvider() != AuthProvider.LOCAL) {
            throw new RuntimeException("Tài khoản này chỉ có thể đăng nhập bằng Google!");
        }

        if (!passwordEncoder.matches(request.getPassword(), user.getPassWord())) {
            throw new RuntimeException("Sai mật khẩu!");
        }

        // Sinh Access Token
        String accessToken = jwtUtils.generateJwtToken(user.getEmail());

        // Sinh Refresh Token
        String refreshToken = jwtUtils.generateRefreshToken(user.getEmail());

        return AuthResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .email(user.getEmail())
                .name(user.getFullName())
                .avatarUrl(user.getAvatarUrl()) // có thể null
                .role(user.getRole().name())
                .status(user.getStatus().name())
                .build();
    }
}