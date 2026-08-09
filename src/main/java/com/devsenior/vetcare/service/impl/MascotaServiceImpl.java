package com.devsenior.vetcare.service.impl;

import com.devsenior.vetcare.dto.MascotaRequest;
import com.devsenior.vetcare.dto.MascotaResponse;
import com.devsenior.vetcare.exception.RecursoNoEncontradoException;
import com.devsenior.vetcare.model.Dueno;
import com.devsenior.vetcare.model.Mascota;
import com.devsenior.vetcare.repository.DuenoRepository;
import com.devsenior.vetcare.repository.MascotaRepository;
import com.devsenior.vetcare.service.MascotaService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MascotaServiceImpl implements MascotaService {

    private final MascotaRepository mascotaRepository;
    private final DuenoRepository  duenoRepository;

    public MascotaServiceImpl(MascotaRepository mascotaRepository, DuenoRepository duenoRepository) {
        this.mascotaRepository = mascotaRepository;
        this.duenoRepository = duenoRepository;
    }

    @Override
    public List<MascotaResponse> listarTodos() {
        return mascotaRepository.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    @Override
    public MascotaResponse buscarPorId(Long id) {
        Mascota mascota = mascotaRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("No se encontró mascota con id: " + id));
        return toResponse(mascota);
    }

    @Override
    public MascotaResponse crear(MascotaRequest request) {
        Dueno dueno = duenoRepository.findById(request.duenoId())
                .orElseThrow(() -> new RecursoNoEncontradoException("No se encontró un dueño con id: " + request.duenoId()));
        Mascota mascota = toEntity(request, dueno);
//        Mascota mascota = new Mascota();
//        mascota.setNombre(request.nombre());
//        mascota.setEspecie(request.especie());
//        mascota.setRaza(request.raza());
//        mascota.setEdad(request.edad());
//        mascota.setDueno(dueno);

        Mascota guardada = mascotaRepository.save(mascota);
        return toResponse(guardada);
    }

    @Override
    public MascotaResponse actualizar(Long id, MascotaRequest request) {
        Mascota mascota = mascotaRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("No se encontró mascota con id: " + id));

        Dueno dueno = duenoRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("no existe dueño con el id: " + request.duenoId()));

        mascota.setNombre(request.nombre());
        mascota.setEspecie(request.especie());
        mascota.setRaza(request.raza());
        mascota.setEdad(request.edad());
        mascota.setDueno(dueno);

        Mascota actualizada = mascotaRepository.save(mascota);
        return toResponse(actualizada);
    }

    @Override
    public void eliminar(Long id) {
        if (!mascotaRepository.existsById(id)) {
            throw new RecursoNoEncontradoException("No existe una mascota con id " + id);
        }
        mascotaRepository.deleteById(id);
    }

    private Mascota toEntity(MascotaRequest request, Dueno dueno){
        Mascota mascota = new Mascota();

        mascota.setNombre(request.nombre());
        mascota.setEspecie(request.especie());
        mascota.setRaza(request.raza());
        mascota.setEdad(request.edad());
        mascota.setDueno(dueno);
        return mascota;
    }

    private MascotaResponse toResponse(Mascota mascota) {
        Dueno dueno = mascota.getDueno();
        String nombreCompleto = dueno.getNombre() + " " + dueno.getApellido();
        return new MascotaResponse(
                mascota.getId(),
                mascota.getNombre(),
                mascota.getEspecie(),
                mascota.getRaza(),
                mascota.getEdad(),
                dueno.getId(),
                nombreCompleto
        );
    }
}
