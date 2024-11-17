package com.cybersentinels.controlador;

import java.util.HashMap;
import java.util.Map;

public class CategoriaHerramienta {
    private Map<String, String> categorias;

    public CategoriaHerramienta() {
        categorias = new HashMap<>();
        categorias.put("Electrónica", "Herramientas electrónicas como multímetros y osciloscopios.");
        categorias.put("Manual", "Herramientas manuales como destornilladores y martillos.");
    }

    public String obtenerDescripcionCategoria(String tipo) {
        return categorias.getOrDefault(tipo, "Categoría no encontrada.");
    }

    public void agregarCategoria(String tipo, String descripcion) {
        categorias.put(tipo, descripcion);
    }
}
