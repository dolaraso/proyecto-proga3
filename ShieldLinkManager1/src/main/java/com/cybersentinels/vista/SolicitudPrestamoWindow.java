package com.cybersentinels.vista;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SolicitudPrestamoWindow {
    private JPanel panelSolicitudPrestamo;
    private JComboBox<String> comboHerramientas;
    private JButton btnSolicitar;

    public SolicitudPrestamoWindow() {
        btnSolicitar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String herramientaSeleccionada = (String) comboHerramientas.getSelectedItem();
                JOptionPane.showMessageDialog(null, "Préstamo solicitado para: " + herramientaSeleccionada);
            }
        });
    }

    public JPanel getPanelSolicitudPrestamo() {
        return panelSolicitudPrestamo;
    }
}
