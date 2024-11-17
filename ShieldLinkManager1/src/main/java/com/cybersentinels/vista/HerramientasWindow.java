package com.cybersentinels.vista;

import javax.swing.*;

public class HerramientasWindow {
    private JPanel panelHerramientas;
    private JButton btnAgregar;
    private JButton btnEditar;
    private JButton btnEliminar;
    private JTable tablaHerramientas;
    private JTable table1;
    private JPanel panel1;
    private JButton button1;
    private JButton button2;
    private JButton button3;

    public HerramientasWindow() {
        // Inicialización de eventos de botones
        btnAgregar.addActionListener(e -> agregarHerramienta());
        btnEditar.addActionListener(e -> editarHerramienta());
        btnEliminar.addActionListener(e -> eliminarHerramienta());
    }

    private void agregarHerramienta() {
        JOptionPane.showMessageDialog(null, "Funcionalidad para agregar herramienta no implementada aún.");
    }

    private void editarHerramienta() {
        JOptionPane.showMessageDialog(null, "Funcionalidad para editar herramienta no implementada aún.");
    }

    private void eliminarHerramienta() {
        JOptionPane.showMessageDialog(null, "Funcionalidad para eliminar herramienta no implementada aún.");
    }

    public JPanel getPanelHerramientas() {
        return panelHerramientas;
    }
}