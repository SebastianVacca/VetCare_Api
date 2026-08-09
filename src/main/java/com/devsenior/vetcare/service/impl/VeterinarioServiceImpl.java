package com.devsenior.vetcare.service.impl;

import com.devsenior.vetcare.dto.VeterinarioRequest;
import com.devsenior.vetcare.dto.VeterinarioResponse;
import com.devsenior.vetcare.exception.RecursoNoEncontradoException;
import com.devsenior.vetcare.model.Veterinario;
import com.devsenior.vetcare.repository.VeterinarioRepository;
import com.devsenior.vetcare.service.VeterinarioService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VeterinarioServiceImpl implements VeterinarioService {

    private final VeterinarioRepository veterinarioRepository;

    public VeterinarioServiceImpl(VeterinarioRepository veterinarioRepository) {
        this.veterinarioRepository = veterinarioRepository;
    }

    @Override
    public List<VeterinarioResponse> listarTodos() {
        return veterinarioRepository.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    @Override
    public VeterinarioResponse buscarPorId(Long id) {
        Veterinario veterinario = veterinarioRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("No se encontró el veterinario con id: " + id));
        return toResponse(veterinario);
    }

    @Override
    public VeterinarioResponse crear(VeterinarioRequest request) {
        Veterinario veterinario = toEntity(request);
        Veterinario guardado = veterinarioRepository.save(veterinario);
        return toResponse(guardado);
    }

    @Override
    public VeterinarioResponse actualizar(Long id, VeterinarioRequest request) {
        Veterinario veterinario = veterinarioRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("No se encontró el veterinario con id: " + id));

        veterinario.setNombre(request.nombre());
        veterinario.setEspecialidad(request.especialidad());
        veterinario.setTarjetaProfesional(request.tarjetaProfesional());

        Veterinario actualizado = veterinarioRepository.save(veterinario);
        return toResponse(actualizado);
    }

    @Override
    public void eliminar(Long id) {
        if (!veterinarioRepository.existsById(id))
            throw new RecursoNoEncontradoException("no existe veterinario con id: " + id);

        veterinarioRepository.deleteById(id);
    }

    private Veterinario toEntity(VeterinarioRequest request) {
        Veterinario veterinario = new Veterinario();

        veterinario.setNombre(request.nombre());
        veterinario.setEspecialidad(request.especialidad());
        veterinario.setTarjetaProfesional(request.tarjetaProfesional());

        return veterinario;
    }

    private VeterinarioResponse toResponse(Veterinario veterinario) {
        return new VeterinarioResponse(
                veterinario.getId(),
                veterinario.getNombre(),
                veterinario.getEspecialidad(),
                veterinario.getTarjetaProfesional()
        );
    }
}
