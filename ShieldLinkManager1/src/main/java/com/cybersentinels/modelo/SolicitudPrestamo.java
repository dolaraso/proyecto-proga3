package com.cybersentinels.modelo;

public class SolicitudPrestamo {
    private int id;
    private int usuarioId;
    private int herramientaId;
    private String fechaSolicitud;

    public SolicitudPrestamo(int id, int usuarioId, int herramientaId, String fechaSolicitud) {
        this.id = id;
        this.usuarioId = usuarioId;
        this.herramientaId = herramientaId;
        this.fechaSolicitud = fechaSolicitud;
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

    public String getFechaSolicitud() {
        return fechaSolicitud;
    }

    public void setFechaSolicitud(String fechaSolicitud) {
        this.fechaSolicitud = fechaSolicitud;
    }
}
