package com.cybersentinels.vista;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MenuAdministrador {
    private JPanel panelAdministrador;
    private JButton btnGestionUsuarios;
    private JButton btnGestionHerramientas;
    private JButton btnGestionPrestamos;
    private JButton btnGestionReportes;
    private JButton btnSalir;

    public MenuAdministrador() {
        // Acción para "Gestión de Usuarios"
        btnGestionUsuarios.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame frame = new JFrame("Gestión de Usuarios");
                frame.setContentPane(new GestionUsuariosWindow().getPanelUsuarios());
                frame.pack();
                frame.setVisible(true);
            }
        });

        // Acción para "Gestión de Herramientas"
        btnGestionHerramientas.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame frame = new JFrame("Gestión de Herramientas");
                frame.setContentPane(new GestionHerramientasWindow().getPanelHerramientas());
                frame.pack();
                frame.setVisible(true);
            }
        });

        // Acción para "Gestión de Préstamos"
        btnGestionPrestamos.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame frame = new JFrame("Gestión de Préstamos");
                frame.setContentPane(new GestionPrestamosWindow().getPanelPrestamos());
                frame.pack();
                frame.setVisible(true);
            }
        });

        // Acción para "Generar Reportes"
        btnGestionReportes.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame frame = new JFrame("Generación de Reportes");
                frame.setContentPane(new GenerarReportesWindow().getPanelReportes());
                frame.pack();
                frame.setVisible(true);
            }
        });

        // Acción para "Salir"
        btnSalir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "Sesión cerrada. ¡Hasta luego, Administrador!");
                System.exit(0);
            }
        });
    }

    public JPanel getPanelAdministrador() {
        return panelAdministrador;
    }
}
