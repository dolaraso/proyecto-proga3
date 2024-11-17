package com.cybersentinels.modelo;

public class Tecnico extends Usuario {
    public Tecnico(int id, String nombre, String usuario, String contrasena) {
        super(id, nombre, usuario, contrasena, "Tecnico");
    }

    public void realizarMantenimiento() {
        // Lógica para realizar mantenimiento
    }
}
