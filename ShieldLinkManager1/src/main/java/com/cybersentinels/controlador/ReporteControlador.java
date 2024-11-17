package com.cybersentinels.controlador;

import com.cybersentinels.dao.PrestamoDAO;
import com.cybersentinels.dao.HerramientaDAO;
import com.cybersentinels.modelo.Herramienta;
import com.cybersentinels.modelo.Prestamo;

import java.util.List;

public class ReporteControlador {
    private final PrestamoDAO prestamoDAO;
    private final HerramientaDAO herramientaDAO;

    public ReporteControlador() {
        prestamoDAO = new PrestamoDAO();
        herramientaDAO = new HerramientaDAO();
    }

    public List<Prestamo> obtenerPrestamosPorEstado(String estado) {
        return prestamoDAO.obtenerPrestamosPorEstado(estado);
    }

    public List<Herramienta> obtenerHerramientasPorEstado(String estado) {
        return herramientaDAO.obtenerHerramientasPorEstado(estado);
    }
}
