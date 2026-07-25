package com.devsenior.vetcare.repository;

import com.devsenior.vetcare.model.Veterinario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VeterinarioRepository extends JpaRepository<Veterinario, Long> {
}
