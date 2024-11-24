package com.cybersentinels.vista;

import javax.swing.*;

public class MenuProfesor {
    private JPanel panelPrincipal;
    private JButton btnSolicitarPrestamo;
    private JButton btnVerPrestamos;
    private JButton btnSalir;

    private final int usuarioId;
    private final String tipoUsuario;

    public MenuProfesor(int usuarioId, String tipoUsuario) {
        this.usuarioId = usuarioId;
        this.tipoUsuario = tipoUsuario;

        inicializarComponentes();

        // Configuración de acciones para los botones
        btnSolicitarPrestamo.addActionListener(e -> abrirSolicitarPrestamo());
        btnVerPrestamos.addActionListener(e -> abrirVerPrestamos());
        btnSalir.addActionListener(e -> cerrarSesion());
    }

    private void inicializarComponentes() {
        System.out.println("Profesor ID: " + usuarioId + ", Tipo: " + tipoUsuario);
        // Si es necesario, puedes cargar configuraciones específicas para el profesor aquí
    }

    private void abrirSolicitarPrestamo() {
        JFrame frame = new JFrame("Solicitar Préstamo");
        SolicitarPrestamoWindow solicitarPrestamoWindow = new SolicitarPrestamoWindow(usuarioId, tipoUsuario);
        frame.setContentPane(solicitarPrestamoWindow.getPanelPrincipal());
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private void abrirVerPrestamos() {
        JFrame frame = new JFrame("Ver Préstamos");
        VerPrestamosWindow verPrestamosWindow = new VerPrestamosWindow(usuarioId);
        frame.setContentPane(verPrestamosWindow.getPanelPrincipal());
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private void cerrarSesion() {
        int opcion = JOptionPane.showConfirmDialog(
                null,
                "¿Seguro que desea cerrar sesión?",
                "Confirmar Cerrar Sesión",
                JOptionPane.YES_NO_OPTION
        );
        if (opcion == JOptionPane.YES_OPTION) {
            JFrame frame = new JFrame("Login");
            frame.setContentPane(new LoginWindow().getPanelPrincipal());
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);

            SwingUtilities.getWindowAncestor(panelPrincipal).dispose();
        }
    }

    public JPanel getPanelPrincipal() {
        return panelPrincipal;
    }
}
