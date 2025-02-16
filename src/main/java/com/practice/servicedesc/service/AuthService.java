package com.practice.servicedesc.service;

import com.practice.servicedesc.web.dto.auth.JwtResponse;
import com.practice.servicedesc.web.dto.auth.LoginRequest;
import com.practice.servicedesc.web.dto.auth.RefreshToken;

public interface AuthService {

    JwtResponse login(LoginRequest request);

    JwtResponse refresh(RefreshToken refreshToken);
}
