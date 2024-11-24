package com.cybersentinels.vista;

import com.cybersentinels.dao.HerramientaDAO;
import com.cybersentinels.modelo.Herramienta;

import javax.swing.*;

public class AgregarHerramientaWindow {

    private JPanel panelPrincipal;
    private JLabel Nombre;
    private JLabel Descripción;
    private JLabel Estado;
    private JLabel Tipo;
    private JLabel Cantidad;

    private JTextField txtNombre;
    private JTextArea txtDescripcion;
    private JComboBox<String> comboEstado;
    private JTextField txtTipo;
    private JTextField txtCantidad;
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
        int cantidad;

        try {
            cantidad = Integer.parseInt(txtCantidad.getText().trim());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "La cantidad debe ser un número válido.");
            return;
        }

        if (nombre.isEmpty() || estado == null || tipo.isEmpty() || cantidad <= 0) {
            JOptionPane.showMessageDialog(null, "Por favor, complete todos los campos obligatorios.");
            return;
        }

        int siguienteId = herramientaDAO.getProximoId();

        Herramienta herramienta = new Herramienta(siguienteId, nombre, descripcion, estado, tipo, cantidad);

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
