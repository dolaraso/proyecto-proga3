package com.cybersentinels.vista;

import javax.swing.*;

public class GenerarReportesWindow {
    private JPanel panelReportes;
    private JTable tableReportes;
    private JButton btnGenerarPDF;

    public GenerarReportesWindow() {
        btnGenerarPDF.addActionListener(e -> JOptionPane.showMessageDialog(null, "Generar Reporte en PDF - No implementado aún"));
    }

    public JPanel getPanelReportes() {
        return panelReportes;
    }
}
