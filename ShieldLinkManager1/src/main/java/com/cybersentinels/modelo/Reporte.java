package com.cybersentinels.modelo;

public class Reporte {
    private int id;
    private String titulo;
    private String descripcion;
    private String fechaGeneracion;

    public Reporte(int id, String titulo, String descripcion, String fechaGeneracion) {
        this.id = id;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.fechaGeneracion = fechaGeneracion;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getFechaGeneracion() {
        return fechaGeneracion;
    }

    public void setFechaGeneracion(String fechaGeneracion) {
        this.fechaGeneracion = fechaGeneracion;
    }

    public void generarReportePréstamos() {
        // Lógica para generar un reporte de préstamos
    }

    public void generarReporteHerramientas() {
        // Lógica para generar un reporte de herramientas disponibles y en mantenimiento
    }
}
