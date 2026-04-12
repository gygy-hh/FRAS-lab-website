package com.fraslab.site.service;

import com.fraslab.site.dto.auth.LoginRequest;
import com.fraslab.site.dto.auth.LoginResponse;
import com.fraslab.site.entity.AdminUser;
import com.fraslab.site.repository.AdminUserRepository;
import com.fraslab.site.security.JwtService;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final AdminUserRepository adminUserRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthService(AdminUserRepository adminUserRepository, PasswordEncoder passwordEncoder, JwtService jwtService) {
        this.adminUserRepository = adminUserRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    public LoginResponse login(LoginRequest request) {
        AdminUser adminUser = adminUserRepository.findByUsername(request.username())
                .orElseThrow(() -> new BadCredentialsException("用户名或密码错误"));
        if (!adminUser.isEnabled() || !passwordEncoder.matches(request.password(), adminUser.getPassword())) {
            throw new BadCredentialsException("用户名或密码错误");
        }
        String token = jwtService.generate(adminUser.getUsername());
        return new LoginResponse(token, jwtService.getExpireSeconds(), adminUser.getUsername());
    }
}
