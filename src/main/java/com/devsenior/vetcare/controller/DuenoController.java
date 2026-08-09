package com.devsenior.vetcare.controller;

import com.devsenior.vetcare.dto.DuenoRequest;
import com.devsenior.vetcare.dto.DuenoResponse;
import com.devsenior.vetcare.service.DuenoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/duenos")
public class DuenoController {
    private final DuenoService duenoService;

    public DuenoController(DuenoService duenoService) {
        this.duenoService = duenoService;
    }

    @GetMapping
    public ResponseEntity<List<DuenoResponse>> listarTodos() {
        return ResponseEntity.ok(duenoService.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<DuenoResponse> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(duenoService.buscarPorId(id));
    }

    @PostMapping
    public ResponseEntity<DuenoResponse> crear(@Valid @RequestBody DuenoRequest request) {
        DuenoResponse response = duenoService.crear(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DuenoResponse> actualizar(@PathVariable Long id, @Valid @RequestBody DuenoRequest request) {
        DuenoResponse response = duenoService.actualizar(id, request);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        duenoService.eliminar(id);
        return  ResponseEntity.noContent().build();
    }
}
