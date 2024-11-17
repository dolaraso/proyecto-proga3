package com.cybersentinels.vista;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MenuProfesor {
    private JPanel panelProfesor;
    private JButton btnGestionarHerramientas;
    private JButton btnVerReportes;
    private JButton btnSalir;

    public MenuProfesor() {
        // Acción para "Gestionar Herramientas"
        btnGestionarHerramientas.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame frame = new JFrame("Gestión de Herramientas");
                frame.setContentPane(new GestionHerramientasWindow().getPanelHerramientas());
                frame.pack();
                frame.setVisible(true);
            }
        });

        // Acción para "Ver Reportes"
        btnVerReportes.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame frame = new JFrame("Reportes");
                frame.setContentPane(new ReportesWindow().getPanelReportes());
                frame.pack();
                frame.setVisible(true);
            }
        });

        // Acción para "Salir"
        btnSalir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "Sesión cerrada. ¡Hasta luego, Profesor!");
                System.exit(0);
            }
        });
    }

    public JPanel getPanelProfesor() {
        return panelProfesor;
    }
}
