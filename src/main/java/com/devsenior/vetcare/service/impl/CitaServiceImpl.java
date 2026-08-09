package com.devsenior.vetcare.service.impl;

import com.devsenior.vetcare.dto.CitaRequest;
import com.devsenior.vetcare.dto.CitaResponse;
import com.devsenior.vetcare.exception.RecursoNoEncontradoException;
import com.devsenior.vetcare.model.Cita;
import com.devsenior.vetcare.model.Mascota;
import com.devsenior.vetcare.model.Veterinario;
import com.devsenior.vetcare.repository.CitaRepository;
import com.devsenior.vetcare.repository.MascotaRepository;
import com.devsenior.vetcare.repository.VeterinarioRepository;
import com.devsenior.vetcare.service.CitaService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CitaServiceImpl implements CitaService {

    private final CitaRepository citaRepository;
    private final VeterinarioRepository veterinarioRepository;
    private final MascotaRepository mascotaRepository;

    public CitaServiceImpl(CitaRepository citaRepository, VeterinarioRepository veterinarioRepository, MascotaRepository mascotaRepository) {
        this.citaRepository = citaRepository;
        this.veterinarioRepository = veterinarioRepository;
        this.mascotaRepository = mascotaRepository;
    }

    @Override
    public List<CitaResponse> listarTodos() {
        return citaRepository.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    @Override
    public CitaResponse buscarPorId(Long id) {
        Cita cita = citaRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("No se encuentra una cita con id: " + id));
        return toResponse(cita);
    }

    @Override
    public CitaResponse crear(CitaRequest request) {
        Mascota mascota = mascotaRepository.findById(request.mascotaId())
                .orElseThrow(() -> new RecursoNoEncontradoException("no se encuentra una mascota con el id: " + request.mascotaId()));

        Veterinario veterinario = veterinarioRepository.findById(request.veterinarioId())
                .orElseThrow(() -> new RecursoNoEncontradoException("No se encuentra un veterinario con el id: " + request.veterinarioId()));

        Cita cita = toEntity(request, mascota, veterinario);
        Cita creada = citaRepository.save(cita);
        return toResponse(creada);
    }

    @Override
    public CitaResponse actualizar(Long id, CitaRequest request) {
        Cita cita = citaRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("No se encuentra una cita con id: " + id));

        Mascota mascota = mascotaRepository.findById(request.mascotaId())
                .orElseThrow(() -> new RecursoNoEncontradoException("no se encuentra una mascota con el id: " + request.mascotaId()));

        Veterinario veterinario = veterinarioRepository.findById(request.veterinarioId())
                .orElseThrow(() -> new RecursoNoEncontradoException("No se encuentra un veterinario con el id: " + request.veterinarioId()));

        cita.setFecha(request.fecha());
        cita.setMotivo(request.motivo());
        cita.setEstado(request.estado());
        cita.setCosto(request.costo());
        cita.setMascota(mascota);
        cita.setVeterinario(veterinario);

        Cita actualizada = citaRepository.save(cita);
        return toResponse(actualizada);
    }

    @Override
    public void eliminar(Long id) {
        if (!citaRepository.existsById(id))
            throw new RecursoNoEncontradoException("No se encuentra una cita con id: " + id);

        citaRepository.deleteById(id);
    }

    private CitaResponse toResponse(Cita cita) {
        Mascota mascota = cita.getMascota();
        Veterinario veterinario = cita.getVeterinario();
        return new CitaResponse(
                cita.getId(),
                cita.getFecha(),
                cita.getMotivo(),
                cita.getEstado(),
                cita.getCosto(),
                mascota.getId(),
                mascota.getNombre(),
                veterinario.getId(),
                veterinario.getNombre()
        );
    }

    private Cita toEntity(CitaRequest request, Mascota mascota, Veterinario veterinario) {
        Cita cita = new Cita();

        cita.setFecha(request.fecha());
        cita.setMotivo(request.motivo());
        cita.setEstado(request.estado());
        cita.setCosto(request.costo());
        cita.setMascota(mascota);
        cita.setVeterinario(veterinario);

        return cita;
    }
}
