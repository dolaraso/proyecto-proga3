package com.cybersentinels.util;

public class ExcepcionesPersonalizadas extends Exception {
    public ExcepcionesPersonalizadas(String mensaje) {
        super(mensaje);
    }

    public static class UsuarioNoEncontradoException extends ExcepcionesPersonalizadas {
        public UsuarioNoEncontradoException() {
            super("Usuario no encontrado en la base de datos.");
        }
    }

    public static class CredencialesInvalidasException extends ExcepcionesPersonalizadas {
        public CredencialesInvalidasException() {
            super("Las credenciales proporcionadas no son válidas.");
        }
    }

    public static class HerramientaNoDisponibleException extends ExcepcionesPersonalizadas {
        public HerramientaNoDisponibleException() {
            super("La herramienta seleccionada no está disponible.");
        }
    }
}

