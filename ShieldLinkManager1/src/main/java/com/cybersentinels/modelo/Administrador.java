package com.cybersentinels.modelo;

public class Administrador extends Usuario {
    public Administrador(int id, String nombre, String usuario, String contrasena) {
        super(id, nombre, usuario, contrasena, "Administrador");
    }

    public void gestionarUsuarios() {
        // Implementa la lógica para gestionar usuarios
    }
}
