package com.cybersentinels.modelo;

import java.util.Date;

public class Prestamo {
    private int id;
    private int usuarioId;
    private int herramientaId;
    private Date fechaPrestamo;
    private String estado;

    // Constructor
    public Prestamo(int id, int usuarioId, int herramientaId, Date fechaPrestamo, String estado) {
        this.id = id;
        this.usuarioId = usuarioId;
        this.herramientaId = herramientaId;
        this.fechaPrestamo = fechaPrestamo;
        this.estado = estado;
    }

    // Getters
    public int getId() {
        return id;
    }

    public int getUsuarioId() {
        return usuarioId;
    }
    public void setEstado(String estado) {
        this.estado = estado;
    }

    public int getHerramientaId() {
        return herramientaId;
    }

    public Date getFechaPrestamo() {
        return fechaPrestamo;
    }

    public String getEstado() {
        return estado;
    }
}
