package com.cybersentinels.vista;

import com.cybersentinels.dao.HerramientaDAO;
import com.cybersentinels.modelo.Herramienta;

import javax.swing.*;

public class ModificarHerramientaWindow {
    private JPanel panelPrincipal;
    private JLabel Nombre;
    private JTextField txtNombre;
    private JLabel Descripción;
    private JTextArea txtDescripcion;
    private JLabel Estado;
    private JComboBox<String> comboEstado;
    private JLabel Tipo;
    private JTextField txtTipo;
    private JLabel Cantidad;
    private JTextField txtCantidad;
    private JButton btnGuardar;
    private JButton btnCancelar;

    private final HerramientaDAO herramientaDAO;
    private final Herramienta herramienta;

    public ModificarHerramientaWindow(Herramienta herramienta, HerramientaDAO herramientaDAO) {
        this.herramienta = herramienta;
        this.herramientaDAO = herramientaDAO;

        inicializarCampos();
        inicializarEstados();

        btnGuardar.addActionListener(e -> guardarCambios());
        btnCancelar.addActionListener(e -> cerrarVentana());
    }

    private void inicializarCampos() {
        txtNombre.setText(herramienta.getNombre());
        txtDescripcion.setText(herramienta.getDescripcion());
        comboEstado.setSelectedItem(herramienta.getEstado());
        txtTipo.setText(herramienta.getTipo());
        txtCantidad.setText(String.valueOf(herramienta.getCantidad())); // Inicializa la cantidad
    }

    private void inicializarEstados() {
        comboEstado.addItem("Disponible");
        comboEstado.addItem("Prestada");
        comboEstado.addItem("Mantenimiento");
    }

    private void guardarCambios() {
        String nuevoNombre = txtNombre.getText().trim();
        String nuevaDescripcion = txtDescripcion.getText().trim();
        String nuevoEstado = (String) comboEstado.getSelectedItem();
        String nuevoTipo = txtTipo.getText().trim();
        String cantidadStr = txtCantidad.getText().trim();

        // Validaciones
        if (nuevoNombre.isEmpty() || nuevoEstado == null || nuevoTipo.isEmpty() || cantidadStr.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Por favor, complete todos los campos obligatorios.");
            return;
        }

        int nuevaCantidad;
        try {
            nuevaCantidad = Integer.parseInt(cantidadStr);
            if (nuevaCantidad < 0) {
                JOptionPane.showMessageDialog(null, "La cantidad no puede ser negativa.");
                return;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "La cantidad debe ser un número entero.");
            return;
        }

        // Actualizar valores en la herramienta
        herramienta.setNombre(nuevoNombre);
        herramienta.setDescripcion(nuevaDescripcion);
        herramienta.setEstado(nuevoEstado);
        herramienta.setTipo(nuevoTipo);
        herramienta.setCantidad(nuevaCantidad); // Actualiza la cantidad

        boolean actualizado = herramientaDAO.actualizarHerramienta(herramienta);
        if (actualizado) {
            JOptionPane.showMessageDialog(null, "Herramienta actualizada con éxito.");
            cerrarVentana();
        } else {
            JOptionPane.showMessageDialog(null, "Error al actualizar la herramienta.");
        }
    }

    private void cerrarVentana() {
        SwingUtilities.getWindowAncestor(panelPrincipal).dispose();
    }

    public JPanel getPanelPrincipal() {
        return panelPrincipal;
    }
}
