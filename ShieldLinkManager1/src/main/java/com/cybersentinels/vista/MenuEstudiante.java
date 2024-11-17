package com.cybersentinels.vista;

import com.cybersentinels.dao.HerramientaDAO;
import com.cybersentinels.dao.PrestamoDAO;
import com.cybersentinels.modelo.Prestamo;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class MenuEstudiante {
    private JPanel panelPrincipal;
    private JButton btnSolicitarPrestamo;
    private JButton btnVerPrestamos;
    private JButton btnSalir;

    private final HerramientaDAO herramientaDAO;
    private final PrestamoDAO prestamoDAO;
    private final int estudianteId; // Este ID debe ser obtenido al iniciar sesión

    public MenuEstudiante(int estudianteId) {
        this.estudianteId = estudianteId;
        this.herramientaDAO = new HerramientaDAO();
        this.prestamoDAO = new PrestamoDAO();


        // Acción para solicitar un préstamo
        btnSolicitarPrestamo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                solicitarPrestamo();
            }
        });

        // Acción para ver préstamos
        btnVerPrestamos.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                verMisPrestamos();
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

    private void solicitarPrestamo() {
        try {
            // Obtener herramientas disponibles
            List<String> herramientasDisponibles = herramientaDAO.obtenerNombresHerramientasDisponibles();

            if (herramientasDisponibles.isEmpty()) {
                JOptionPane.showMessageDialog(null, "No hay herramientas disponibles.");
                return;
            }

            // Seleccionar herramienta
            String herramientaSeleccionada = (String) JOptionPane.showInputDialog(
                    null,
                    "Seleccione una herramienta:",
                    "Solicitar Préstamo",
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    herramientasDisponibles.toArray(),
                    herramientasDisponibles.get(0)
            );

            if (herramientaSeleccionada != null) {
                int herramientaId = herramientaDAO.obtenerIdHerramientaPorNombre(herramientaSeleccionada);
                boolean exito = prestamoDAO.solicitarPrestamo(herramientaId, estudianteId);

                if (exito) {
                    JOptionPane.showMessageDialog(null, "Préstamo solicitado exitosamente.");
                } else {
                    JOptionPane.showMessageDialog(null, "Error al solicitar préstamo.");
                }
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Error al solicitar préstamo: " + ex.getMessage());
        }
    }

    private void verMisPrestamos() {
        try {
            // Obtener lista de préstamos del estudiante
            List<Prestamo> prestamos = prestamoDAO.obtenerPrestamosPorUsuario(estudianteId);

            if (prestamos.isEmpty()) {
                JOptionPane.showMessageDialog(null, "No tienes préstamos registrados.");
                return;
            }

            // Convertir la lista de objetos Prestamo a un formato legible
            StringBuilder prestamosTexto = new StringBuilder();
            for (Prestamo prestamo : prestamos) {
                prestamosTexto.append("ID Préstamo: ").append(prestamo.getId())
                        .append(", Herramienta ID: ").append(prestamo.getHerramientaId())
                        .append(", Fecha: ").append(prestamo.getFechaPrestamo())
                        .append(", Estado: ").append(prestamo.getEstado())
                        .append("\n");
            }

            // Mostrar la lista de préstamos en un JOptionPane
            JOptionPane.showMessageDialog(null, "Mis Préstamos:\n" + prestamosTexto.toString());
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Error al consultar préstamos: " + ex.getMessage());
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

        SwingUtilities.getWindowAncestor(panelPrincipal).dispose();
    }

    public JPanel getPanelPrincipal() {
        return panelPrincipal;
    }
}
