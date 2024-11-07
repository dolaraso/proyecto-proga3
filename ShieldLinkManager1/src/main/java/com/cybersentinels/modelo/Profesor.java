package com.cybersentinels.modelo;

import java.util.Date;
import java.util.List;

public class Profesor extends Usuario {

    // Constructor
    public Profesor(int id, String nombre, String apellido, String email, String nombreUsuario, String contraseña) {
        super(id, nombre, apellido, email, nombreUsuario, contraseña, Rol.Profesor);
    }

    // Métodos específicos del Profesor
    public void solicitarEquipoParaClase(Date fecha, java.sql.Time hora, String aula, List<Equipo> equipos) {
        // Implementación para solicitar equipos para una clase
    }

    public void verEstadoSolicitudes() {
        // Implementación para ver el estado de sus solicitudes
    }
}
