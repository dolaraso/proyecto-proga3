package com.cybersentinels.modelo;

import java.time.LocalDate;

public class Prestamo {
    private int id;
    private int usuarioId;
    private int herramientaId;
    private LocalDate fechaPrestamo;
    private LocalDate fechaDevolucion;
    private String estado;
    private String descripcionUso;
    private int cantidadSolicitada;
    private String herramientaNombre;
    private String usuarioRol;

    // Constructor completo
    public Prestamo(int id, int usuarioId, int herramientaId, LocalDate fechaPrestamo, LocalDate fechaDevolucion, String estado, String descripcionUso, int cantidadSolicitada, String herramientaNombre, String usuarioRol) {
        this.id = id;
        this.usuarioId = usuarioId;
        this.herramientaId = herramientaId;
        this.fechaPrestamo = fechaPrestamo;
        this.fechaDevolucion = fechaDevolucion;
        this.estado = estado;
        this.descripcionUso = descripcionUso;
        this.cantidadSolicitada = cantidadSolicitada;
        this.herramientaNombre = herramientaNombre;
        this.usuarioRol = usuarioRol;
    }

    // Constructor para obtener préstamos con cantidad solicitada
    public Prestamo(int id, int usuarioId, int herramientaId, LocalDate fechaPrestamo, LocalDate fechaDevolucion, String estado, String descripcionUso, int cantidadSolicitada) {
        this.id = id;
        this.usuarioId = usuarioId;
        this.herramientaId = herramientaId;
        this.fechaPrestamo = fechaPrestamo;
        this.fechaDevolucion = fechaDevolucion;
        this.estado = estado;
        this.descripcionUso = descripcionUso;
        this.cantidadSolicitada = cantidadSolicitada;
    }

    // Constructor básico
    public Prestamo(int id, int usuarioId, int herramientaId, LocalDate fechaPrestamo, LocalDate fechaDevolucion, String estado, String descripcionUso) {
        this.id = id;
        this.usuarioId = usuarioId;
        this.herramientaId = herramientaId;
        this.fechaPrestamo = fechaPrestamo;
        this.fechaDevolucion = fechaDevolucion;
        this.estado = estado;
        this.descripcionUso = descripcionUso;
    }

    // Getters y Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(int usuarioId) {
        this.usuarioId = usuarioId;
    }

    public int getHerramientaId() {
        return herramientaId;
    }

    public void setHerramientaId(int herramientaId) {
        this.herramientaId = herramientaId;
    }

    public LocalDate getFechaPrestamo() {
        return fechaPrestamo;
    }

    public void setFechaPrestamo(LocalDate fechaPrestamo) {
        this.fechaPrestamo = fechaPrestamo;
    }

    public LocalDate getFechaDevolucion() {
        return fechaDevolucion;
    }

    public void setFechaDevolucion(LocalDate fechaDevolucion) {
        this.fechaDevolucion = fechaDevolucion;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getDescripcionUso() {
        return descripcionUso;
    }

    public void setDescripcionUso(String descripcionUso) {
        this.descripcionUso = descripcionUso;
    }

    public int getCantidadSolicitada() {
        return cantidadSolicitada;
    }

    public void setCantidadSolicitada(int cantidadSolicitada) {
        this.cantidadSolicitada = cantidadSolicitada;
    }

    public String getHerramientaNombre() {
        return herramientaNombre;
    }

    public void setHerramientaNombre(String herramientaNombre) {
        this.herramientaNombre = herramientaNombre;
    }

    public String getUsuarioRol() {
        return usuarioRol;
    }

    public void setUsuarioRol(String usuarioRol) {
        this.usuarioRol = usuarioRol;
    }
}
