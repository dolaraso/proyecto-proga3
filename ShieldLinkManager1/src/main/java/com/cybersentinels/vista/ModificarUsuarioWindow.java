package com.cybersentinels.vista;

import com.cybersentinels.dao.UsuarioDAO;
import com.cybersentinels.modelo.Usuario;

import javax.swing.*;

public class ModificarUsuarioWindow {

    private JPanel panelPrincipal;
    private JTextField txtNombre;
    private JTextField txtUsuario;
    private JPasswordField txtContrasena;
    private JComboBox<String> comboRol;
    private JButton btnGuardar;
    private JButton btnCancelar;

    private final UsuarioDAO usuarioDAO;
    private final Usuario usuario;

    /**
     * Constructor de la ventana ModificarUsuarioWindow.
     *
     * @param usuario El usuario que se desea modificar.
     * @param usuarioDAO El objeto DAO para realizar operaciones con la base de datos.
     */
    public ModificarUsuarioWindow(Usuario usuario, UsuarioDAO usuarioDAO) {
        this.usuario = usuario;
        this.usuarioDAO = usuarioDAO;

        inicializarRoles();
        inicializarCampos();

        // Eventos de los botones
        btnGuardar.addActionListener(e -> guardarCambios());
        btnCancelar.addActionListener(e -> cerrarVentana());
    }

    /**
     * Inicializa los valores del ComboBox de roles.
     */
    private void inicializarRoles() {
        comboRol.addItem("Administrador");
        comboRol.addItem("Usuario");
    }

    /**
     * Inicializa los campos de texto con los valores actuales del usuario.
     */
    private void inicializarCampos() {
        txtNombre.setText(usuario.getNombre());
        txtUsuario.setText(usuario.getUsuario());
        txtContrasena.setText(usuario.getContrasena());
        comboRol.setSelectedItem(usuario.getRol());
    }

    /**
     * Guarda los cambios realizados al usuario.
     */
    private void guardarCambios() {
        String nuevoNombre = txtNombre.getText().trim();
        String nuevoUsuario = txtUsuario.getText().trim();
        String nuevaContrasena = new String(txtContrasena.getPassword()).trim();
        String nuevoRol = (String) comboRol.getSelectedItem();

        // Validaciones
        if (nuevoNombre.isEmpty() || nuevoUsuario.isEmpty() || nuevaContrasena.isEmpty() || nuevoRol == null) {
            JOptionPane.showMessageDialog(null, "Por favor, complete todos los campos.");
            return;
        }

        // Actualizar los valores del usuario
        usuario.setNombre(nuevoNombre);
        usuario.setUsuario(nuevoUsuario);
        usuario.setContrasena(nuevaContrasena);
        usuario.setRol(nuevoRol);

        // Actualizar en la base de datos
        boolean actualizado = usuarioDAO.modificarUsuario(usuario);

        if (actualizado) {
            JOptionPane.showMessageDialog(null, "Usuario actualizado con éxito.");
            cerrarVentana();
        } else {
            JOptionPane.showMessageDialog(null, "Error al actualizar el usuario.");
        }
    }

    /**
     * Cierra la ventana actual.
     */
    private void cerrarVentana() {
        SwingUtilities.getWindowAncestor(panelPrincipal).dispose();
    }

    /**
     * Devuelve el panel principal de la ventana.
     *
     * @return JPanel principal.
     */
    public JPanel getPanelPrincipal() {
        return panelPrincipal;
    }
}
