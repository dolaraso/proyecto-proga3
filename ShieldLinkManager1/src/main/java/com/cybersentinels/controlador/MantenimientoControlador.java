package com.cybersentinels.controlador;

import com.cybersentinels.dao.HerramientaDAO;
import com.cybersentinels.modelo.Herramienta;

import java.util.List;

public class MantenimientoControlador {
    private final HerramientaDAO herramientaDAO;

    public MantenimientoControlador() {
        herramientaDAO = new HerramientaDAO();
    }

    public Herramienta obtenerHerramientaPorId(int id) {
        return herramientaDAO.obtenerHerramientaPorId(id);
    }

    public boolean actualizarHerramienta(Herramienta herramienta) {
        return herramientaDAO.actualizarHerramienta(herramienta);
    }

    public List<Herramienta> obtenerHerramientasPorEstado(String estado) {
        return herramientaDAO.obtenerHerramientasPorEstado(estado);
    }
}
