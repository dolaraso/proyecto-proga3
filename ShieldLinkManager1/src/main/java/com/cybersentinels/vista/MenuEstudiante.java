package com.cybersentinels.vista;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MenuEstudiante {
    private JPanel panelEstudiante;
    private JButton btnSolicitarPrestamo;
    private JButton btnVerPrestamos;
    private JButton btnSalir;

    public MenuEstudiante() {
        // Acción para "Solicitar Préstamo"
        btnSolicitarPrestamo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame frame = new JFrame("Solicitud de Préstamo");
                frame.setContentPane(new SolicitudPrestamoWindow().getPanelSolicitudPrestamo());
                frame.pack();
                frame.setVisible(true);
            }
        });

        // Acción para "Ver Préstamos"
        btnVerPrestamos.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame frame = new JFrame("Mis Préstamos");
                frame.setContentPane(new MisPrestamosWindow().getPanelMisPrestamos());
                frame.pack();
                frame.setVisible(true);
            }
        });

        // Acción para "Cerrar Sesión"
        btnSalir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "Sesión cerrada. ¡Hasta luego, Estudiante!");
                System.exit(0);
            }
        });
    }

    public JPanel getPanelEstudiante() {
        return panelEstudiante;
    }
}
