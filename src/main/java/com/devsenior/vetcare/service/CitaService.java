package com.devsenior.vetcare.service;

import com.devsenior.vetcare.dto.CitaRequest;
import com.devsenior.vetcare.dto.CitaResponse;

import java.util.List;

public interface CitaService {
    List<CitaResponse> listarTodos();
    CitaResponse buscarPorId(Long id);
    CitaResponse crear(CitaRequest request);
    CitaResponse actualizar(Long id, CitaRequest request);
    void eliminar(Long id);
}
