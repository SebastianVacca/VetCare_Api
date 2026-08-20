package com.devsenior.vetcare.service.impl;

import com.devsenior.vetcare.dto.AuthResponse;
import com.devsenior.vetcare.dto.LoginRequest;
import com.devsenior.vetcare.dto.RegistroRequest;
import com.devsenior.vetcare.model.Usuario;
import com.devsenior.vetcare.repository.UsuarioRepository;
import com.devsenior.vetcare.service.AuthService;
import com.devsenior.vetcare.service.JwtService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthServiceImpl implements AuthService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthServiceImpl(UsuarioRepository usuarioRepository,
                           PasswordEncoder passwordEncoder,
                           JwtService jwtService,
                           AuthenticationManager authenticationManager) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }

    @Override
    public AuthResponse registrar(RegistroRequest request) {
        if (usuarioRepository.findByUsername(request.username()).isPresent())
            throw new RuntimeException("El usuario ya está registrado");

        Usuario usuario = new Usuario();
        usuario.setUsername(request.username());
        usuario.setPassword(passwordEncoder.encode(request.password()));
        usuario.setRol(request.rol());

        usuarioRepository.save(usuario);

        UserDetails userDetails = new User(usuario.getUsername(), usuario.getPassword(), List.of());
        String token = jwtService.generarToken(userDetails, usuario.getRol().name());

        return new AuthResponse(token);
    }

    @Override
    public AuthResponse login(LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.username(), request.password()));

        Usuario usuario = usuarioRepository.findByUsername(request.username())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        UserDetails userDetails = new User(usuario.getUsername(), usuario.getPassword(), List.of());
        String token = jwtService.generarToken(userDetails, usuario.getRol().name());

        return new AuthResponse(token);
    }
}
