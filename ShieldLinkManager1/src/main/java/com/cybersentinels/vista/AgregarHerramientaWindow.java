package com.cybersentinels.vista;

import com.cybersentinels.dao.HerramientaDAO;
import com.cybersentinels.modelo.Herramienta;

import javax.swing.*;

public class AgregarHerramientaWindow {
    private JPanel panelPrincipal;
    private JTextField txtNombre;
    private JLabel Descripción;
    private JLabel Nombre;
    private JTextArea txtDescripcion;
    private JLabel Estado;
    private JComboBox<String> comboEstado;
    private JLabel Tipo;
    private JTextField txtTipo;
    private JButton btnGuardar;
    private JButton btnCancelar;

    private final HerramientaDAO herramientaDAO;

    public AgregarHerramientaWindow(HerramientaDAO herramientaDAO) {
        this.herramientaDAO = herramientaDAO;

        inicializarEstados();

        btnGuardar.addActionListener(e -> agregarHerramienta());
        btnCancelar.addActionListener(e -> cerrarVentana());
    }

    private void inicializarEstados() {
        comboEstado.addItem("Disponible");
        comboEstado.addItem("Prestada");
        comboEstado.addItem("Mantenimiento");
    }

    private void agregarHerramienta() {
        String nombre = txtNombre.getText().trim();
        String descripcion = txtDescripcion.getText().trim();
        String estado = (String) comboEstado.getSelectedItem();
        String tipo = txtTipo.getText().trim();

        if (nombre.isEmpty() || estado == null || tipo.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Por favor, complete todos los campos obligatorios.");
            return;
        }

        Herramienta herramienta = new Herramienta();
        herramienta.setNombre(nombre);
        herramienta.setDescripcion(descripcion);
        herramienta.setEstado(estado);
        herramienta.setTipo(tipo);

        boolean guardado = herramientaDAO.agregarHerramienta(herramienta);
        if (guardado) {
            JOptionPane.showMessageDialog(null, "Herramienta agregada con éxito.");
            cerrarVentana();
        } else {
            JOptionPane.showMessageDialog(null, "Error al agregar la herramienta.");
        }
    }

    private void cerrarVentana() {
        SwingUtilities.getWindowAncestor(panelPrincipal).dispose();
    }

    public JPanel getPanelPrincipal() {
        return panelPrincipal;
    }
}
