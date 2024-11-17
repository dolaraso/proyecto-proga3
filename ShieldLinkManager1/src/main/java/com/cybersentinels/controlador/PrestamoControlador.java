package com.cybersentinels.controlador;

import com.cybersentinels.dao.PrestamoDAO;
import com.cybersentinels.modelo.Prestamo;

import java.util.List;

public class PrestamoControlador {
    private final PrestamoDAO prestamoDAO;

    public PrestamoControlador() {
        prestamoDAO = new PrestamoDAO();
    }

    public boolean agregarPrestamo(Prestamo prestamo) {
        return prestamoDAO.agregarPrestamo(prestamo);
    }

    public List<Prestamo> obtenerPrestamos() {
        return prestamoDAO.obtenerPrestamos();
    }

    public boolean actualizarPrestamo(Prestamo prestamo) {
        return prestamoDAO.actualizarPrestamo(prestamo);
    }

    public boolean eliminarPrestamo(int id) {
        return prestamoDAO.eliminarPrestamo(id);
    }

    public List<Prestamo> obtenerPrestamosPorUsuario(int usuarioId) {
        return prestamoDAO.obtenerPrestamosPorUsuario(usuarioId);
    }
}
