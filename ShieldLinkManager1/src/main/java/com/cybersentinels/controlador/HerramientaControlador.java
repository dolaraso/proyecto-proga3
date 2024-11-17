package com.cybersentinels.controlador;

import com.cybersentinels.dao.HerramientaDAO;
import com.cybersentinels.modelo.Herramienta;

import java.util.List;

public class HerramientaControlador {
    private final HerramientaDAO herramientaDAO;

    public HerramientaControlador() {
        herramientaDAO = new HerramientaDAO();
    }

    public boolean agregarHerramienta(Herramienta herramienta) {
        return herramientaDAO.agregarHerramienta(herramienta);
    }

    public List<Herramienta> obtenerHerramientas() {
        return herramientaDAO.obtenerHerramientas();
    }

    public boolean eliminarHerramienta(int id) {
        return herramientaDAO.eliminarHerramienta(id);
    }
}
