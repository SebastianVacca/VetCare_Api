package com.devsenior.vetcare.dto;

import jakarta.validation.constraints.NotBlank;

public record LoginRequest(
        @NotBlank(message = "El username es obligatorio")
        String username,

        @NotBlank(message = "El password es oblligatorio")
        String password
) {}
