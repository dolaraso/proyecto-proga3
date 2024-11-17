package com.cybersentinels.vista;

import javax.swing.*;

public class ReportesWindow {
    private JPanel panelReportes;
    private JTable tableReportes;
    private JButton btnDescargarPDF;

    public ReportesWindow() {
        btnDescargarPDF.addActionListener(e -> JOptionPane.showMessageDialog(null, "Descargar Reporte en PDF - No implementado aún"));
    }

    public JPanel getPanelReportes() {
        return panelReportes;
    }
}
