package com.cybersentinels.vista;

import com.cybersentinels.dao.HerramientaDAO;

import javax.swing.*;

public class MenuEstudiante {
    private JPanel panelPrincipal;
    private JButton btnMultas;
    private JButton btnSolicitarPrestamo;
    private JButton btnSalir;
    private JButton btnVerPrestamos;

    private final HerramientaDAO herramientaDAO;
    private final int usuarioId;
    private final String tipoUsuario; // Atributo para almacenar el tipo de usuario

    public MenuEstudiante(int usuarioId, String tipoUsuario) {
        this.usuarioId = usuarioId; // Guardamos el ID del usuario
        this.tipoUsuario = tipoUsuario; // Guardamos el tipo de usuario (Estudiante o Profesor)
        herramientaDAO = new HerramientaDAO();

        // Configuración de botones
        configurarBotones();
    }

    private void configurarBotones() {
        btnSolicitarPrestamo.addActionListener(e -> abrirSolicitarPrestamo());
        btnVerPrestamos.addActionListener(e -> abrirVerPrestamos());
        btnSalir.addActionListener(e -> cerrarSesion());
    }

    private void abrirSolicitarPrestamo() {
        try {
            JFrame frame = new JFrame("Solicitar Préstamo");
            // Pasamos el usuarioId y el tipoUsuario a la ventana de solicitar préstamo
            SolicitarPrestamoWindow solicitarPrestamoWindow = new SolicitarPrestamoWindow(usuarioId, tipoUsuario);
            frame.setContentPane(solicitarPrestamoWindow.getPanelPrincipal());
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        } catch (Exception e) {
            mostrarError("Error al abrir la ventana de Solicitar Préstamo: " + e.getMessage());
        }
    }

    private void abrirVerPrestamos() {
        try {
            JFrame frame = new JFrame("Ver Mis Préstamos");
            VerPrestamosWindow verPrestamosWindow = new VerPrestamosWindow(usuarioId);
            frame.setContentPane(verPrestamosWindow.getPanelPrincipal());
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        } catch (Exception e) {
            mostrarError("Error al abrir la ventana de Ver Préstamos: " + e.getMessage());
        }
    }



    private void cerrarSesion() {
        int opcion = JOptionPane.showConfirmDialog(
                null,
                "¿Seguro que desea cerrar sesión?",
                "Confirmar",
                JOptionPane.YES_NO_OPTION
        );

        if (opcion == JOptionPane.YES_OPTION) {
            try {
                JFrame frame = new JFrame("Login");
                frame.setContentPane(new LoginWindow().getPanelPrincipal());
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.pack();
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);

                SwingUtilities.getWindowAncestor(panelPrincipal).dispose();
            } catch (Exception e) {
                mostrarError("Error al cerrar sesión: " + e.getMessage());
            }
        }
    }

    private void mostrarError(String mensaje) {
        JOptionPane.showMessageDialog(
                null,
                mensaje,
                "Error",
                JOptionPane.ERROR_MESSAGE
        );
    }

    public JPanel getPanelPrincipal() {
        return panelPrincipal;
    }
}
