package com.cybersentinels.vista;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GestionHerramientasWindow {
    private JPanel panelHerramientas;
    private JTable tableHerramientas;
    private JButton btnAgregar;
    private JButton btnModificar;
    private JButton btnEliminar;
    private JButton btnMantenimiento;

    public GestionHerramientasWindow() {
        btnAgregar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "Agregar Herramienta - No implementado aún");
            }
        });

        btnModificar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "Modificar Herramienta - No implementado aún");
            }
        });

        btnEliminar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "Eliminar Herramienta - No implementado aún");
            }
        });

        btnMantenimiento.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "Programar Mantenimiento - No implementado aún");
            }
        });
    }

    public JPanel getPanelHerramientas() {
        return panelHerramientas;
    }
}
