package com.practice.servicedesc.service.impl;

import com.practice.servicedesc.entity.User;
import com.practice.servicedesc.security.JwtProvider;
import com.practice.servicedesc.service.AuthService;
import com.practice.servicedesc.service.UserService;
import com.practice.servicedesc.web.dto.auth.JwtResponse;
import com.practice.servicedesc.web.dto.auth.LoginRequest;
import com.practice.servicedesc.web.dto.auth.RefreshToken;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final JwtProvider jwtProvider;

    @Override
    public JwtResponse login(LoginRequest loginRequest) {
        JwtResponse jwtResponse = new JwtResponse();
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(), loginRequest.getPassword())
        );
        User user = userService.getByEmail(loginRequest.getUsername());
        jwtResponse.setId(user.getId());
        jwtResponse.setUsername(user.getEmail());
        jwtResponse.setAccessToken(jwtProvider.createAccessToken(
                user.getId(), user.getEmail(), user.getRole())
        );
        jwtResponse.setRefreshToken(jwtProvider.createRefreshToken(
                user.getId(), user.getEmail())
        );
        return jwtResponse;
    }

    @Override
    public JwtResponse refresh(RefreshToken refreshToken) {
        return jwtProvider.refreshUserToken(refreshToken.getRefreshToken());
    }

}
