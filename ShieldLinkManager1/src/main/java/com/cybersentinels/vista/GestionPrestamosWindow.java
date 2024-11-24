package com.cybersentinels.vista;

import com.cybersentinels.dao.PrestamoDAO;
import com.cybersentinels.modelo.Prestamo;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class GestionPrestamosWindow {
    private JPanel panelPrincipal;
    private JTable tablePrestamos;
    private JButton btnAprobar;
    private JButton btnRechazar;

    private final PrestamoDAO prestamoDAO;

    public GestionPrestamosWindow() {
        prestamoDAO = new PrestamoDAO();
        cargarPrestamosEnTabla();

        // Acción para aprobar un préstamo
        btnAprobar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int filaSeleccionada = tablePrestamos.getSelectedRow();
                if (filaSeleccionada != -1) {
                    int prestamoId = (int) tablePrestamos.getValueAt(filaSeleccionada, 0);
                    if (prestamoDAO.actualizarEstadoPrestamo(prestamoId, "Activo")) { // Usa "Activo" para aprobar
                        JOptionPane.showMessageDialog(null, "Préstamo aprobado con éxito.");
                        cargarPrestamosEnTabla();
                    } else {
                        JOptionPane.showMessageDialog(null, "Error al aprobar el préstamo.");
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Seleccione un préstamo para aprobar.");
                }
            }
        });

        // Acción para rechazar un préstamo
        btnRechazar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int filaSeleccionada = tablePrestamos.getSelectedRow();
                if (filaSeleccionada != -1) {
                    int prestamoId = (int) tablePrestamos.getValueAt(filaSeleccionada, 0);
                    if (prestamoDAO.actualizarEstadoPrestamo(prestamoId, "Cancelado")) { // Usa "Cancelado" para rechazar
                        JOptionPane.showMessageDialog(null, "Préstamo rechazado con éxito.");
                        cargarPrestamosEnTabla();
                    } else {
                        JOptionPane.showMessageDialog(null, "Error al rechazar el préstamo.");
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Seleccione un préstamo para rechazar.");
                }
            }
        });
    }

    /**
     * Carga todos los préstamos en la tabla.
     */
    private void cargarPrestamosEnTabla() {
        List<Prestamo> prestamos = prestamoDAO.obtenerPrestamosConDetalle();
        String[] columnNames = {"ID", "Usuario ID", "Rol Usuario", "Herramienta ID", "Fecha Préstamo", "Fecha Devolución", "Estado", "Descripción"};
        Object[][] data = new Object[prestamos.size()][8];

        for (int i = 0; i < prestamos.size(); i++) {
            Prestamo prestamo = prestamos.get(i);
            data[i][0] = prestamo.getId();
            data[i][1] = prestamo.getUsuarioId();
            data[i][2] = prestamo.getUsuarioRol(); // Rol del usuario (estudiante o profesor)
            data[i][3] = prestamo.getHerramientaId();
            data[i][4] = prestamo.getFechaPrestamo();
            data[i][5] = prestamo.getFechaDevolucion();
            data[i][6] = prestamo.getEstado();
            data[i][7] = prestamo.getDescripcionUso();
        }

        tablePrestamos.setModel(new DefaultTableModel(data, columnNames));
    }

    /**
     * Devuelve el panel principal para su integración con otras ventanas.
     */
    public JPanel getPanelPrincipal() {
        return panelPrincipal;
    }
}
