package com.monal.driveEase.Services;

import com.monal.driveEase.DTOs.Request.LoginRequest;
import com.monal.driveEase.DTOs.Request.RegisterRequest;
import com.monal.driveEase.DTOs.Response.AuthResponse;

public interface AuthService {

    AuthResponse register(RegisterRequest request);

    AuthResponse login(LoginRequest request);
}
