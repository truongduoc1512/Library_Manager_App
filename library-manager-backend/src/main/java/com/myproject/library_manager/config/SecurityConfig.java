package com.myproject.library_manager.config;

import com.myproject.library_manager.security.JwtAuthenticationFilter;
import com.myproject.library_manager.security.JwtUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {

    private final JwtUtils jwtUtils;

    public SecurityConfig(JwtUtils jwtUtils) {
        this.jwtUtils = jwtUtils;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())

            // Phân quyền cho API
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/api/auth/**").permitAll()   // Cho phép register/login/refresh
                .anyRequest().authenticated()                  // Các API khác yêu cầu JWT
            )

            // ❌ Tắt login form mặc định + http basic
            .formLogin(form -> form.disable())
            .httpBasic(httpBasic -> httpBasic.disable())

            // ✅ Stateless cho API
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

            // ✅ Nếu API chưa auth thì trả về 401 JSON thay vì redirect
            .exceptionHandling(ex -> ex
                .defaultAuthenticationEntryPointFor(
                    new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED),
                    request -> request.getRequestURI().startsWith("/api/")
                )
            )

            // ✅ Giữ OAuth2 cho Google login (chỉ dùng khi gọi /oauth2/authorization/google từ browser)
            .oauth2Login(oauth2 -> oauth2
                .loginPage("/oauth2/authorization/google?prompt=select_account")
                .defaultSuccessUrl("/login/success", true)
                .failureUrl("/login/failure")
            );

        // ✅ Thêm JWT filter
        http.addFilterBefore(new JwtAuthenticationFilter(jwtUtils),
                UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
