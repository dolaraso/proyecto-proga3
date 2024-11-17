package com.cybersentinels.modelo;

import java.sql.Date;

public class Prestamo {
    private int id;
    private int usuarioId;
    private int herramientaId;
    private Date fechaPrestamo;
    private String estado;

    public Prestamo(int id, int usuarioId, int herramientaId, Date fechaPrestamo, String estado) {
        this.id = id;
        this.usuarioId = usuarioId;
        this.herramientaId = herramientaId;
        this.fechaPrestamo = fechaPrestamo;
        this.estado = estado;
    }

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

    public Date getFechaPrestamo() {
        return fechaPrestamo;
    }

    public void setFechaPrestamo(Date fechaPrestamo) {
        this.fechaPrestamo = fechaPrestamo;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}
