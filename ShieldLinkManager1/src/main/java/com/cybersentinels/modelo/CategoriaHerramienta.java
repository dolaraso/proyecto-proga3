package com.cybersentinels.modelo;

public class CategoriaHerramienta {
    private int id;
    private String nombre;
    private String descripcion;

    // Constructor vacío
    public CategoriaHerramienta() {
    }

    // Constructor con parámetros
    public CategoriaHerramienta(int id, String nombre, String descripcion) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
    }

    // Getters y Setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    // Implementar el resto de getters y setters para nombre y descripcion

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }


    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
