package com.cybersentinels.modelo;

public class Prestamo {
    private int id;
    private int herramientaId;
    private int usuarioId;
    private String fechaPrestamo;
    private String estado;

    public Prestamo(int id, int herramientaId, int usuarioId, String fechaPrestamo, String estado) {
        this.id = id;
        this.herramientaId = herramientaId;
        this.usuarioId = usuarioId;
        this.fechaPrestamo = fechaPrestamo;
        this.estado = estado;
    }

    public Prestamo(int herramientaId, int usuarioId, String fechaPrestamo, String estado) {
        this(0, herramientaId, usuarioId, fechaPrestamo, estado);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getHerramientaId() {
        return herramientaId;
    }

    public void setHerramientaId(int herramientaId) {
        this.herramientaId = herramientaId;
    }

    public int getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(int usuarioId) {
        this.usuarioId = usuarioId;
    }

    public String getFechaPrestamo() {
        return fechaPrestamo;
    }

    public void setFechaPrestamo(String fechaPrestamo) {
        this.fechaPrestamo = fechaPrestamo;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}
