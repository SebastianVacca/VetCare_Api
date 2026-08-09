package com.devsenior.vetcare.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record CitaRequest(
        @NotNull(message = "La fecha es obligatoria")
        LocalDate fecha,

        @NotBlank(message = "El motivo es obligatorio")
        String motivo,

        String estado,
        Double costo,

        @NotNull(message = "el id de la mascota es obligatorio")
        Long mascotaId,

        @NotNull(message = "El id del veterinario es obligatorio")
        Long veterinarioId
) {}
