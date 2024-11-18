package com.cybersentinels.vista;

import com.cybersentinels.dao.PrestamoDAO;
import com.cybersentinels.modelo.Prestamo;

import javax.swing.*;
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
                    if (prestamoDAO.actualizarEstadoPrestamo(prestamoId, "Aprobado")) {
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
                    if (prestamoDAO.actualizarEstadoPrestamo(prestamoId, "Rechazado")) {
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
        List<Prestamo> prestamos = prestamoDAO.obtenerPrestamos();
        String[] columnNames = {"ID", "Usuario ID", "Herramienta ID", "Fecha Préstamo", "Estado"};
        Object[][] data = new Object[prestamos.size()][5];

        for (int i = 0; i < prestamos.size(); i++) {
            Prestamo prestamo = prestamos.get(i);
            data[i][0] = prestamo.getId();
            data[i][1] = prestamo.getUsuarioId();
            data[i][2] = prestamo.getHerramientaId();
            data[i][3] = prestamo.getFechaPrestamo();
            data[i][4] = prestamo.getEstado();
        }

        tablePrestamos.setModel(new javax.swing.table.DefaultTableModel(data, columnNames));
    }

    /**
     * Devuelve el panel principal para su integración con otras ventanas.
     */
    public JPanel getPanelPrincipal() {
        return panelPrincipal;
    }
}
