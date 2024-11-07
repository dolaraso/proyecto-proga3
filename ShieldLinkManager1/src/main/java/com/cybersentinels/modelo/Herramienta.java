package com.cybersentinels.modelo;

import com.cybersentinels.controlador.CategoriaHerramienta;

import java.util.Date;

public class Herramienta {
    private int id;
    private String nombre;
    private String descripcion;
    private String estado; // Disponible, Prestada, En Mantenimiento
    private String ubicacion;
    private CategoriaHerramienta categoria;
    private Date fechaAdquisicion;

    // Constructor vacío
    public Herramienta() {
    }

    // Constructor con parámetros
    public Herramienta(int id, String nombre, String descripcion, String estado, String ubicacion, CategoriaHerramienta categoria, Date fechaAdquisicion) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.estado = estado;
        this.ubicacion = ubicacion;
        this.categoria = categoria;
        this.fechaAdquisicion = fechaAdquisicion;
    }

    // Getters y Setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    // ... Implementar el resto de getters y setters para cada atributo

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    // Implementar getters y setters para descripcion, estado, ubicacion, categoria, fechaAdquisicion

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }


    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }


    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }


    public CategoriaHerramienta getCategoria() {
        return categoria;
    }

    public void setCategoria(CategoriaHerramienta categoria) {
        this.categoria = categoria;
    }


    public Date getFechaAdquisicion() {
        return fechaAdquisicion;
    }

    public void setFechaAdquisicion(Date fechaAdquisicion) {
        this.fechaAdquisicion = fechaAdquisicion;
    }
}
