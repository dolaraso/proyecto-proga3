package com.cybersentinels.vista;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainWindow {

    private JPanel panelPrincipal;
    private  JLabel lblBienvenida;
    private JButton btnUsuarios;
    private JButton btnHerramientas;
    private JButton btnPrestamos;
    private  JButton btnMantenimiento;
    private  JButton btnSalir;

    public MainWindow() {
        btnUsuarios.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame frame = new JFrame("Gestión de Usuarios");
                frame.setContentPane(new GestionUsuariosWindow().getPanelUsuarios());
                frame.pack();
                frame.setVisible(true);
            }
        });

        btnHerramientas.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame frame = new JFrame("Gestión de Herramientas");
                frame.setContentPane(new HerramientasWindow().getPanelHerramientas());
                frame.pack();
                frame.setVisible(true);
            }
        });

        btnPrestamos.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame frame = new JFrame("Gestión de Préstamos y Reportes");
                frame.setContentPane(new PrestamosWindow().getPanelPrestamos());
                frame.pack();
                frame.setVisible(true);
            }
        });
    }

    public JPanel getPanelPrincipal() {
        return panelPrincipal;
    }
}
