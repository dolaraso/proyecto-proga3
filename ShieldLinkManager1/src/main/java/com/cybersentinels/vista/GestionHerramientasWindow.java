package com.cybersentinels.vista;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GestionHerramientasWindow {
    private JPanel panelPrincipal;
    private JTable tableHerramientas;
    private JButton btnAgregar;
    private JButton btnModificar;
    private JButton btnEliminar;
    private JButton btnMantenimiento;
    private JButton btnRegresar;

    public GestionHerramientasWindow() {
        // Acción del botón Agregar
        btnAgregar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                agregarHerramienta();
            }
        });

        // Acción del botón Modificar
        btnModificar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                modificarHerramienta();
            }
        });

        // Acción del botón Eliminar
        btnEliminar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                eliminarHerramienta();
            }
        });

        // Acción del botón Mantenimiento
        btnMantenimiento.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                realizarMantenimiento();
            }
        });

        // Acción del botón Regresar
        btnRegresar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                regresarMenu();
            }
        });
    }

    /**
     * Método para agregar una herramienta.
     */
    private void agregarHerramienta() {
        JOptionPane.showMessageDialog(null, "Agregar Herramienta: Funcionalidad en desarrollo.");
    }

    /**
     * Método para modificar una herramienta.
     */
    private void modificarHerramienta() {
        JOptionPane.showMessageDialog(null, "Modificar Herramienta: Funcionalidad en desarrollo.");
    }

    /**
     * Método para eliminar una herramienta.
     */
    private void eliminarHerramienta() {
        JOptionPane.showMessageDialog(null, "Eliminar Herramienta: Funcionalidad en desarrollo.");
    }

    /**
     * Método para realizar mantenimiento a herramientas.
     */
    private void realizarMantenimiento() {
        JOptionPane.showMessageDialog(null, "Mantenimiento de Herramientas: Funcionalidad en desarrollo.");
    }

    /**
     * Método para regresar al menú principal.
     */
    private void regresarMenu() {
        JOptionPane.showMessageDialog(null, "Regresar al Menú: Funcionalidad en desarrollo.");
    }

    /**
     * Retorna el panel principal para integrarlo con otras ventanas.
     */
    public JPanel getPanelPrincipal() {
        return panelPrincipal;
    }
}
