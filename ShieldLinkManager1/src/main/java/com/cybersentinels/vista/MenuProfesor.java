package com.cybersentinels.vista;

import com.cybersentinels.dao.HerramientaDAO;
import com.cybersentinels.dao.PrestamoDAO;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class MenuProfesor {
    private JPanel panelPrincipal;
    private JButton btnGestionarHerramientas;
    private JButton btnVerReportes;
    private JButton btnSalir;

    // DAOs para interactuar con la base de datos
    private HerramientaDAO herramientaDAO;
    private PrestamoDAO prestamoDAO;

    public MenuProfesor() {
        // Inicializar DAOs
        herramientaDAO = new HerramientaDAO();
        prestamoDAO = new PrestamoDAO();

        // Acción para gestionar herramientas
        btnGestionarHerramientas.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gestionarHerramientas();
            }
        });

        // Acción para ver reportes de préstamos
        btnVerReportes.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                verReportes();
            }
        });

        // Acción para cerrar sesión
        btnSalir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cerrarSesion();
            }
        });
    }

    private void gestionarHerramientas() {
        try {
            // Obtener herramientas desde la base de datos
            List<String> herramientasDisponibles = herramientaDAO.obtenerNombresHerramientasDisponibles();

            if (herramientasDisponibles.isEmpty()) {
                JOptionPane.showMessageDialog(null, "No hay herramientas disponibles para gestionar.");
                return;
            }

            // Mostrar herramientas disponibles en un combo box para realizar acciones (como cambiar estado)
            String herramientaSeleccionada = (String) JOptionPane.showInputDialog(
                    null,
                    "Seleccione una herramienta para gestionar:",
                    "Gestión de Herramientas",
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    herramientasDisponibles.toArray(),
                    herramientasDisponibles.get(0)
            );

            if (herramientaSeleccionada != null) {
                int herramientaId = herramientaDAO.obtenerIdHerramientaPorNombre(herramientaSeleccionada);
                if (herramientaId != -1) {
                    JOptionPane.showMessageDialog(null, "Herramienta seleccionada: " + herramientaSeleccionada + " (ID: " + herramientaId + ")");
                    // Aquí se puede implementar lógica adicional para modificar la herramienta
                } else {
                    JOptionPane.showMessageDialog(null, "No se pudo obtener el ID de la herramienta seleccionada.");
                }
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Error al gestionar herramientas: " + ex.getMessage());
        }
    }

    private void verReportes() {
        try {
            // Obtener reportes de préstamos
            StringBuilder reporte = new StringBuilder();
            prestamoDAO.obtenerPrestamos().forEach(prestamo -> {
                reporte.append("ID Préstamo: ").append(prestamo.getId())
                        .append(", Usuario ID: ").append(prestamo.getUsuarioId())
                        .append(", Herramienta ID: ").append(prestamo.getHerramientaId())
                        .append(", Fecha: ").append(prestamo.getFechaPrestamo())
                        .append(", Estado: ").append(prestamo.getEstado())
                        .append("\n");
            });

            if (reporte.isEmpty()) {
                JOptionPane.showMessageDialog(null, "No hay reportes de préstamos disponibles.");
            } else {
                JOptionPane.showMessageDialog(null, "Reportes de Préstamos:\n" + reporte);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Error al obtener reportes de préstamos: " + ex.getMessage());
        }
    }

    private void cerrarSesion() {
        JFrame frame = new JFrame("Login");
        LoginWindow loginWindow = new LoginWindow();
        frame.setContentPane(loginWindow.getPanelPrincipal());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        // Cerrar el menú actual
        SwingUtilities.getWindowAncestor(panelPrincipal).dispose();
    }

    public JPanel getPanelPrincipal() {
        return panelPrincipal;
    }
}
