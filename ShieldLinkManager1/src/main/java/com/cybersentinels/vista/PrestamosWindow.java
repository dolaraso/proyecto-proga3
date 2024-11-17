package com.cybersentinels.vista;

import javax.swing.*;

public class PrestamosWindow {
    private JPanel panelPrestamos;
    private JButton btnSolicitarPrestamo;
    private JButton btnDevolverPrestamo;
    private JTable tablaPrestamos;

    public PrestamosWindow() {
        // Inicialización de eventos de botones
        btnSolicitarPrestamo.addActionListener(e -> solicitarPrestamo());
        btnDevolverPrestamo.addActionListener(e -> devolverPrestamo());
    }

    private void solicitarPrestamo() {
        JOptionPane.showMessageDialog(null, "Funcionalidad para solicitar préstamo no implementada aún.");
    }

    private void devolverPrestamo() {
        JOptionPane.showMessageDialog(null, "Funcionalidad para devolver préstamo no implementada aún.");
    }

    public JPanel getPanelPrestamos() {
        return panelPrestamos;
    }
}
