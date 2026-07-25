package com.devsenior.vetcare.controller;

import com.devsenior.vetcare.model.Dueno;
import com.devsenior.vetcare.service.DuenoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/dueno")
public class DuenoController {
    private final DuenoService duenoService;

    public DuenoController(DuenoService duenoService) {
        this.duenoService = duenoService;
    }

    @GetMapping
    public ResponseEntity<List<Dueno>> listarTodos() {
        return ResponseEntity.ok(duenoService.listarTodos());
    }
}
