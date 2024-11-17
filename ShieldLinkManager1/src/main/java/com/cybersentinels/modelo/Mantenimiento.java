package com.cybersentinels.modelo;

public class Mantenimiento {
    private int id;
    private String fechaProgramada;
    private String estado;

    public Mantenimiento(int id, String fechaProgramada, String estado) {
        this.id = id;
        this.fechaProgramada = fechaProgramada;
        this.estado = estado;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFechaProgramada() {
        return fechaProgramada;
    }

    public void setFechaProgramada(String fechaProgramada) {
        this.fechaProgramada = fechaProgramada;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}
