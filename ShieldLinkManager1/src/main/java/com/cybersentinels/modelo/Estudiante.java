package com.cybersentinels.modelo;

public class Estudiante extends Usuario {
    private String matricula;

    public Estudiante(int id, String nombre, String usuario, String contrasena, String matricula) {
        super(id, nombre, usuario, contrasena, "Estudiante");
        this.matricula = matricula;
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }
}
