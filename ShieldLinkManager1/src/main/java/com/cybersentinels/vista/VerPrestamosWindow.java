package com.cybersentinels.vista;

import com.cybersentinels.dao.PrestamoDAO;
import com.cybersentinels.modelo.Prestamo;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.List;

public class VerPrestamosWindow {
    private JPanel panelPrincipal;
    private JTable tablePrestamos;
    private JButton btnRegresarEquipos;

    private final PrestamoDAO prestamoDAO;
    private int usuarioId;

    public VerPrestamosWindow(int usuarioId) {
        this.usuarioId = usuarioId;
        prestamoDAO = new PrestamoDAO();
        inicializarTabla();
        cargarPrestamos();

        // Acción del botón Regresar Equipos
        btnRegresarEquipos.addActionListener(e -> regresarEquipo());
    }

    private void inicializarTabla() {
        tablePrestamos.setModel(new DefaultTableModel(
                new Object[][]{},
                new String[]{"ID", "Usuario ID", "Herramienta ID", "Fecha Préstamo", "Fecha Devolución", "Estado"}
        ));
    }

    private void cargarPrestamos() {
        DefaultTableModel model = (DefaultTableModel) tablePrestamos.getModel();
        model.setRowCount(0); // Limpiar la tabla

        List<Prestamo> prestamos = prestamoDAO.obtenerPrestamosPorUsuario(usuarioId);
        for (Prestamo prestamo : prestamos) {
            model.addRow(new Object[]{
                    prestamo.getId(),
                    prestamo.getUsuarioId(),
                    prestamo.getHerramientaId(),
                    prestamo.getFechaPrestamo(),
                    prestamo.getFechaDevolucion() != null ? prestamo.getFechaDevolucion() : "No devuelto",
                    prestamo.getEstado()
            });
        }
    }

    private void regresarEquipo() {
        int selectedRow = tablePrestamos.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(null, "Seleccione un préstamo para devolver el equipo.");
            return;
        }

        // Obtener los datos del préstamo seleccionado
        int prestamoId = (int) tablePrestamos.getValueAt(selectedRow, 0);
        int herramientaId = (int) tablePrestamos.getValueAt(selectedRow, 2);
        String estadoActual = (String) tablePrestamos.getValueAt(selectedRow, 5);

        // Validar que el préstamo esté en estado "Activo"
        if (!estadoActual.equalsIgnoreCase("Activo")) {
            JOptionPane.showMessageDialog(null, "Solo se pueden devolver equipos con estado 'Activo'.");
            return;
        }

        int confirmacion = JOptionPane.showConfirmDialog(
                null,
                "¿Está seguro de que desea devolver este equipo?",
                "Confirmar devolución",
                JOptionPane.YES_NO_OPTION
        );

        if (confirmacion == JOptionPane.YES_OPTION) {
            try {
                // Obtener la cantidad de equipos prestados
                int cantidadPrestada = prestamoDAO.obtenerCantidadPrestada(prestamoId);

                // Marcar el préstamo como completado
                boolean prestamoCompletado = prestamoDAO.completarPrestamo(prestamoId);

                if (prestamoCompletado) {
                    // Actualizar la cantidad disponible en herramientas
                    boolean cantidadActualizada = prestamoDAO.incrementarCantidadHerramienta(herramientaId, cantidadPrestada);

                    if (cantidadActualizada) {
                        JOptionPane.showMessageDialog(null, "Equipo devuelto exitosamente. Cantidad actualizada.");
                    } else {
                        JOptionPane.showMessageDialog(null, "Error al actualizar la cantidad en inventario.");
                    }

                    cargarPrestamos(); // Refrescar la tabla para reflejar los cambios
                } else {
                    JOptionPane.showMessageDialog(null, "Error al devolver el equipo.");
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
            }
        }
    }



    public JPanel getPanelPrincipal() {
        return panelPrincipal;
    }
}
