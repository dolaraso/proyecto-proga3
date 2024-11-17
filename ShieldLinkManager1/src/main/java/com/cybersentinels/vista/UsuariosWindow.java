package com.cybersentinels.vista;

import com.cybersentinels.controlador.UsuarioControlador;
import com.cybersentinels.modelo.Usuario;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class UsuariosWindow {
    private JPanel panelUsuarios;
    private JTable tableUsuarios;
    private JButton btnAgregar;
    private JButton btnModificar;
    private JButton btnEliminar;
    private JButton btnRegresar;
    private UsuarioControlador usuarioControlador;
    private DefaultTableModel modeloTabla;

    public UsuariosWindow() {
        usuarioControlador = new UsuarioControlador();
        configurarTabla();
        cargarUsuarios();

        btnAgregar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                agregarUsuario();
            }
        });

        btnModificar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                editarUsuario();
            }
        });

        btnEliminar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                eliminarUsuario();
            }
        });
        btnAgregar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
    }

    private void configurarTabla() {
        modeloTabla = new DefaultTableModel(new String[]{"ID", "Nombre", "Usuario", "Rol"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Hacer las celdas no editables
            }
        };
        tableUsuarios.setModel(modeloTabla);
    }

    private void cargarUsuarios() {
        modeloTabla.setRowCount(0); // Limpiar la tabla
        List<Usuario> usuarios = usuarioControlador.obtenerTodosUsuarios();
        for (Usuario usuario : usuarios) {
            modeloTabla.addRow(new Object[]{
                    usuario.getId(),
                    usuario.getNombre(),
                    usuario.getUsuario(),
                    usuario.getRol()
            });
        }
    }

    private void agregarUsuario() {
        String nombre = JOptionPane.showInputDialog("Ingrese el nombre del usuario:");
        String usuario = JOptionPane.showInputDialog("Ingrese el nombre de usuario:");
        String contrasena = JOptionPane.showInputDialog("Ingrese la contraseña:");
        String rol = JOptionPane.showInputDialog("Ingrese el rol del usuario (Admin/Usuario):");

        if (usuarioControlador.agregarUsuario(nombre, usuario, contrasena, rol)) {
            JOptionPane.showMessageDialog(null, "Usuario agregado exitosamente.");
            cargarUsuarios();
        } else {
            JOptionPane.showMessageDialog(null, "Error al agregar el usuario.");
        }
    }

    private void editarUsuario() {
        int filaSeleccionada = tableUsuarios.getSelectedRow();
        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(null, "Seleccione un usuario para editar.");
            return;
        }

        int id = (int) modeloTabla.getValueAt(filaSeleccionada, 0);
        String nombre = JOptionPane.showInputDialog("Ingrese el nuevo nombre del usuario:", modeloTabla.getValueAt(filaSeleccionada, 1));
        String usuario = JOptionPane.showInputDialog("Ingrese el nuevo nombre de usuario:", modeloTabla.getValueAt(filaSeleccionada, 2));
        String contrasena = JOptionPane.showInputDialog("Ingrese la nueva contraseña:");
        String rol = JOptionPane.showInputDialog("Ingrese el nuevo rol del usuario (Admin/Usuario):", modeloTabla.getValueAt(filaSeleccionada, 3));

        if (usuarioControlador.actualizarUsuario(id, nombre, usuario, contrasena, rol)) {
            JOptionPane.showMessageDialog(null, "Usuario actualizado exitosamente.");
            cargarUsuarios();
        } else {
            JOptionPane.showMessageDialog(null, "Error al actualizar el usuario.");
        }
    }

    private void eliminarUsuario() {
        int filaSeleccionada = tableUsuarios.getSelectedRow();
        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(null, "Seleccione un usuario para eliminar.");
            return;
        }

        int id = (int) modeloTabla.getValueAt(filaSeleccionada, 0);
        int confirmacion = JOptionPane.showConfirmDialog(null, "¿Está seguro de eliminar este usuario?", "Confirmar eliminación", JOptionPane.YES_NO_OPTION);
        if (confirmacion == JOptionPane.YES_OPTION) {
            if (usuarioControlador.eliminarUsuario(id)) {
                JOptionPane.showMessageDialog(null, "Usuario eliminado exitosamente.");
                cargarUsuarios();
            } else {
                JOptionPane.showMessageDialog(null, "Error al eliminar el usuario.");
            }
        }
    }

    public JPanel getPanelUsuarios() {
        return panelUsuarios;
    }
}
