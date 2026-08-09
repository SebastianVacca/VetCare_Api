package com.devsenior.vetcare.controller;

import com.devsenior.vetcare.dto.VeterinarioRequest;
import com.devsenior.vetcare.dto.VeterinarioResponse;
import com.devsenior.vetcare.service.VeterinarioService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/veterinarios")
public class VeterinarioController {

    private final VeterinarioService veterinarioService;

    public VeterinarioController(VeterinarioService veterinarioService) {
        this.veterinarioService = veterinarioService;
    }

    @GetMapping
    public ResponseEntity<List<VeterinarioResponse>> listarTodos() {
        return ResponseEntity.ok(veterinarioService.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<VeterinarioResponse> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(veterinarioService.buscarPorId(id));
    }

    @PostMapping
    public ResponseEntity<VeterinarioResponse> crear(@Valid @RequestBody VeterinarioRequest request) {
        VeterinarioResponse response = veterinarioService.crear(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<VeterinarioResponse> actualizar(@PathVariable Long id, @Valid @RequestBody VeterinarioRequest request) {
        VeterinarioResponse response = veterinarioService.actualizar(id, request);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        veterinarioService.eliminar(id);
        return  ResponseEntity.noContent().build();
    }
}
