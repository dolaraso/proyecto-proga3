package com.cybersentinels.vista;

import com.cybersentinels.dao.UsuarioDAO;
import com.cybersentinels.modelo.Usuario;

import javax.swing.*;

public class AgregarUsuarioWindow {
    private JPanel panelPrincipal;
    private JTextField txtNombre;
    private JTextField txtUsuario;
    private JPasswordField txtContrasena;
    private JComboBox<String> comboRol;
    private JButton btnGuardar;
    private JButton btnCancelar;

    private final UsuarioDAO usuarioDAO;

    public AgregarUsuarioWindow() {
        usuarioDAO = new UsuarioDAO();

        btnGuardar.addActionListener(e -> guardarUsuario());
        btnCancelar.addActionListener(e -> cerrarVentana());
        inicializarRoles();
    }

    private void inicializarRoles() {
        comboRol.addItem("Administrador");
        comboRol.addItem("Usuario");
    }

    private void guardarUsuario() {
        String nombre = txtNombre.getText().trim();
        String usuario = txtUsuario.getText().trim();
        String contrasena = new String(txtContrasena.getPassword()).trim();
        String rol = (String) comboRol.getSelectedItem();

        if (nombre.isEmpty() || usuario.isEmpty() || contrasena.isEmpty() || rol == null) {
            JOptionPane.showMessageDialog(null, "Por favor, complete todos los campos.");
            return;
        }

        Usuario nuevoUsuario = new Usuario();
        nuevoUsuario.setNombre(nombre);
        nuevoUsuario.setUsuario(usuario);
        nuevoUsuario.setContrasena(contrasena); // Cambiado para usar 'contrasena'
        nuevoUsuario.setRol(rol);

        boolean guardado = usuarioDAO.agregarUsuario(nuevoUsuario);

        if (guardado) {
            JOptionPane.showMessageDialog(null, "Usuario agregado con éxito.");
            cerrarVentana();
        } else {
            JOptionPane.showMessageDialog(null, "Error al agregar el usuario.");
        }
    }

    private void cerrarVentana() {
        SwingUtilities.getWindowAncestor(panelPrincipal).dispose();
    }

    public JPanel getPanelPrincipal() {
        return panelPrincipal;
    }
}
