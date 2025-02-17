package com.practice.servicedesc.controller.api;

import com.practice.servicedesc.service.AuthService;
import com.practice.servicedesc.web.dto.auth.JwtResponse;
import com.practice.servicedesc.web.dto.auth.LoginRequest;
import com.practice.servicedesc.web.dto.auth.RefreshToken;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Validated
public class AuthApiController {

    private final AuthService authService;

    @PostMapping("/login")
    public JwtResponse login(@Validated @RequestBody LoginRequest loginRequest) {
        return authService.login(loginRequest);
    }

    @PostMapping("/refresh")
    public JwtResponse refresh(@Validated @RequestBody RefreshToken refreshToken) {
        return authService.refresh(refreshToken);
    }

}
