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
        Herramienta herramienta = herramientaDAO.obtenerHerramientaPorId(id);
        if (herramienta != null) {
            // Lógica para manejar la herramienta encontrada
            System.out.println("Herramienta encontrada: " + herramienta.getNombre());
        } else {
            System.out.println("Herramienta no encontrada.");
        }
        return herramienta;
    }


    public boolean actualizarHerramienta(Herramienta herramienta) {
        return herramientaDAO.actualizarHerramienta(herramienta);
    }

    public List<Herramienta> obtenerHerramientasPorEstado(String estado) {
        return herramientaDAO.obtenerHerramientasPorEstado(estado);
    }
}
