package com.devsenior.vetcare.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "citas")
public class Cita {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDate fecha;

    @Column(nullable = false)
    private String motivo;

    private String estado;
    private Double costo;

    @ManyToOne
    @JoinColumn(name = "mascota.id", nullable = false)
    @JsonIgnore
    private Mascota mascota;

    @ManyToOne
    @JoinColumn(name = "veterinario.id")
    private Veterinario veterinario;

    public Cita() {
    }

    public Cita(Long id, LocalDate fecha, String motivo, String estado, Double costo, Mascota mascotas, Veterinario veterinario) {
        this.id = id;
        this.fecha = fecha;
        this.motivo = motivo;
        this.estado = estado;
        this.costo = costo;
        this.mascota = mascotas;
        this.veterinario = veterinario;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Double getCosto() {
        return costo;
    }

    public void setCosto(Double costo) {
        this.costo = costo;
    }

    public Mascota getMascota() {
        return mascota;
    }

    public void setMascota(Mascota mascota) {
        this.mascota = mascota;
    }

    public Veterinario getVeterinario() {
        return veterinario;
    }

    public void setVeterinario(Veterinario veterinario) {
        this.veterinario = veterinario;
    }
}
