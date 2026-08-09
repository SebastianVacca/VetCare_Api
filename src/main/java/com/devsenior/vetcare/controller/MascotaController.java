package com.devsenior.vetcare.controller;

import com.devsenior.vetcare.dto.MascotaRequest;
import com.devsenior.vetcare.dto.MascotaResponse;
import com.devsenior.vetcare.service.MascotaService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/mascotas")
public class MascotaController {

    private final MascotaService mascotaService;

    public MascotaController(MascotaService mascotaService) {
        this.mascotaService = mascotaService;
    }

    @GetMapping
    public ResponseEntity<List<MascotaResponse>> listarTodos() {
        return ResponseEntity.ok(mascotaService.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<MascotaResponse> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(mascotaService.buscarPorId(id));
    }

    @PostMapping
    public ResponseEntity<MascotaResponse> crear(@Valid @RequestBody MascotaRequest request) {
        MascotaResponse response = mascotaService.crear(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MascotaResponse> actualizar(@PathVariable Long id, @Valid @RequestBody MascotaRequest request) {
        MascotaResponse response = mascotaService.actualizar(id, request);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        mascotaService.eliminar(id);
        return  ResponseEntity.noContent().build();
    }
}
