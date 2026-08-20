package com.devsenior.vetcare.service;

import com.devsenior.vetcare.dto.AuthResponse;
import com.devsenior.vetcare.dto.LoginRequest;
import com.devsenior.vetcare.dto.RegistroRequest;

public interface AuthService {
    AuthResponse registrar(RegistroRequest request);
    AuthResponse login(LoginRequest request);
}
