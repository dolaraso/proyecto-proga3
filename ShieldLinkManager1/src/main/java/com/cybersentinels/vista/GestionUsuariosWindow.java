package com.cybersentinels.vista;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GestionUsuariosWindow {
    private JPanel panelUsuarios;
    private JTable tableUsuarios;
    private JButton btnAgregar;
    private JButton btnModificar;
    private JButton btnEliminar;

    public GestionUsuariosWindow() {
        btnAgregar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "Agregar Usuario - No implementado aún");
            }
        });

        btnModificar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "Modificar Usuario - No implementado aún");
            }
        });

        btnEliminar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "Eliminar Usuario - No implementado aún");
            }
        });
    }

    public JPanel getPanelUsuarios() {
        return panelUsuarios;
    }
}
