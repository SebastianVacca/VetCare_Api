package com.devsenior.vetcare.service.impl;

import com.devsenior.vetcare.model.Dueno;
import com.devsenior.vetcare.repository.DuenoRepository;
import com.devsenior.vetcare.service.DuenoService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DuenoServiceImpl implements DuenoService {
    private final DuenoRepository duenoRepository;

    public DuenoServiceImpl(DuenoRepository duenoRepository) {
        this.duenoRepository = duenoRepository;
    }

    @Override
    public List<Dueno> listarTodos() {
        return duenoRepository.findAll();
    }
}
