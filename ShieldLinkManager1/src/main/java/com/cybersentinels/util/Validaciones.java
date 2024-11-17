package com.cybersentinels.util;

import java.util.regex.Pattern;

public class Validaciones {
    public static boolean validarEmail(String email) {
        String regex = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";
        return Pattern.matches(regex, email);
    }

    public static boolean validarNumero(String numero) {
        return numero.matches("\\d+"); // Solo permite números
    }

    public static boolean validarTexto(String texto) {
        return texto.matches("[a-zA-Z ]+"); // Solo permite letras y espacios
    }

    public static boolean validarLongitud(String texto, int longitudMinima, int longitudMaxima) {
        return texto.length() >= longitudMinima && texto.length() <= longitudMaxima;
    }

    public static boolean esCampoVacio(String texto) {
        return texto == null || texto.trim().isEmpty();
    }
}
