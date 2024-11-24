package com.cybersentinels.controlador;

import com.cybersentinels.dao.HerramientaDAO;
import com.cybersentinels.modelo.Herramienta;

import java.util.List;

public class HerramientaControlador {
    private final HerramientaDAO herramientaDAO;

    public HerramientaControlador() {
        herramientaDAO = new HerramientaDAO();
    }

    public int obtenerIdHerramientaPorNombre(String nombre) {
        return herramientaDAO.obtenerIdHerramientaPorNombre(nombre);
    }

    public List<Herramienta> obtenerTodasLasHerramientas() {
        return herramientaDAO.obtenerHerramientas();
    }

    public boolean eliminarHerramientaPorId(int id) {
        return herramientaDAO.eliminarHerramienta(id);
    }
}
