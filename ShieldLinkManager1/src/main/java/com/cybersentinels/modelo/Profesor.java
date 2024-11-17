package com.cybersentinels.modelo;

public class Profesor extends Usuario {
    private String departamento;

    public Profesor(int id, String nombre, String usuario, String contrasena, String departamento) {
        super(id, nombre, usuario, contrasena, "Profesor");
        this.departamento = departamento;
    }

    public String getDepartamento() {
        return departamento;
    }

    public void setDepartamento(String departamento) {
        this.departamento = departamento;
    }

    public void gestionarPréstamos() {
        // Lógica específica para gestionar préstamos como profesor
    }

    public void asignarTareas() {
        // Lógica específica para asignar tareas relacionadas a herramientas
    }
}
