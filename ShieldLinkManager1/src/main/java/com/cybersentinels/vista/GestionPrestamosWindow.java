package com.cybersentinels.vista;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GestionPrestamosWindow {
    private JPanel panelPrestamos;
    private JTable tablePrestamos;
    private JButton btnAprobar;
    private JButton btnRechazar;

    public GestionPrestamosWindow() {
        btnAprobar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "Aprobar Préstamo - No implementado aún");
            }
        });

        btnRechazar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "Rechazar Préstamo - No implementado aún");
            }
        });
    }

    public JPanel getPanelPrestamos() {
        return panelPrestamos;
    }
}
