package com.cybersentinels.vista;

import com.cybersentinels.dao.UsuarioDAO;
import com.cybersentinels.modelo.Usuario;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class GestionUsuariosWindow {
    private JPanel panelPrincipal;
    private JTable tableUsuarios;
    private JButton btnAgregar;
    private JButton btnModificar;

    private final UsuarioDAO usuarioDAO;

    public GestionUsuariosWindow() {
        usuarioDAO = new UsuarioDAO();
        cargarUsuariosEnTabla();

        // Acción para agregar un usuario
        btnAgregar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                agregarUsuario();
            }
        });

        // Acción para modificar un usuario
        btnModificar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                modificarUsuario();
            }
        });
    }

    /**
     * Carga los usuarios desde la base de datos en la tabla.
     */
    private void cargarUsuariosEnTabla() {
        List<Usuario> usuarios = usuarioDAO.obtenerUsuarios();
        String[] columnNames = {"ID", "Nombre", "Usuario", "Rol"};
        Object[][] data = new Object[usuarios.size()][4];

        for (int i = 0; i < usuarios.size(); i++) {
            Usuario usuario = usuarios.get(i);
            data[i][0] = usuario.getId();
            data[i][1] = usuario.getNombre();
            data[i][2] = usuario.getUsuario();
            data[i][3] = usuario.getRol();
        }

        tableUsuarios.setModel(new DefaultTableModel(data, columnNames));
    }

    /**
     * Permite agregar un nuevo usuario.
     */
    private void agregarUsuario() {
        String nombre = JOptionPane.showInputDialog("Ingrese el nombre del usuario:");
        String usuario = JOptionPane.showInputDialog("Ingrese el nombre de usuario:");
        String contrasena = JOptionPane.showInputDialog("Ingrese la contraseña:");
        String rol = JOptionPane.showInputDialog("Ingrese el rol del usuario:");
        if (nombre != null && !nombre.isEmpty() &&
                usuario != null && !usuario.isEmpty() &&
                contrasena != null && !contrasena.isEmpty() &&
                rol != null && !rol.isEmpty()) {
            Usuario nuevoUsuario = new Usuario(0, nombre, usuario, contrasena, rol);
            if (usuarioDAO.agregarUsuario(nuevoUsuario)) {
                JOptionPane.showMessageDialog(null, "Usuario agregado correctamente.");
                cargarUsuariosEnTabla();
            } else {
                JOptionPane.showMessageDialog(null, "Error al agregar el usuario.");
            }
        } else {
            JOptionPane.showMessageDialog(null, "Los campos no pueden estar vacíos.");
        }
    }

    /**
     * Permite modificar un usuario existente.
     */
    private void modificarUsuario() {
        int filaSeleccionada = tableUsuarios.getSelectedRow();
        if (filaSeleccionada != -1) {
            int id = (int) tableUsuarios.getValueAt(filaSeleccionada, 0);
            String nuevoNombre = JOptionPane.showInputDialog("Ingrese el nuevo nombre del usuario:",
                    tableUsuarios.getValueAt(filaSeleccionada, 1));
            String nuevoRol = JOptionPane.showInputDialog("Ingrese el nuevo rol del usuario:",
                    tableUsuarios.getValueAt(filaSeleccionada, 3));

            if (nuevoNombre != null && nuevoRol != null && !nuevoNombre.isEmpty() && !nuevoRol.isEmpty()) {
                Usuario usuario = usuarioDAO.obtenerUsuarioPorId(id);
                if (usuario != null) {
                    usuario.setNombre(nuevoNombre);
                    usuario.setRol(nuevoRol);
                    if (usuarioDAO.actualizarUsuario(usuario)) {
                        JOptionPane.showMessageDialog(null, "Usuario modificado correctamente.");
                        cargarUsuariosEnTabla();
                    } else {
                        JOptionPane.showMessageDialog(null, "Error al modificar el usuario.");
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Usuario no encontrado.");
                }
            }
        } else {
            JOptionPane.showMessageDialog(null, "Seleccione un usuario para modificar.");
        }
    }

    /**
     * Retorna el panel principal para integrar con otras ventanas.
     */
    public JPanel getPanelPrincipal() {
        return panelPrincipal;
    }
}
