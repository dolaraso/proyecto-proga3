package com.cybersentinels.vista;

import com.cybersentinels.dao.UsuarioDAO;
import com.cybersentinels.modelo.Usuario;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class GestionUsuariosWindow {
    private JPanel panelPrincipal; // Panel principal de la ventana
    private JTable tableUsuarios; // Tabla para mostrar usuarios
    private JButton btnAgregar; // Botón para agregar un nuevo usuario
    private JButton btnModificar; // Botón para modificar un usuario existente
    private JButton btnEliminar; // Botón para eliminar un usuario
    private JTextField txtBuscarUsuario; // Campo de texto para buscar usuarios por ID
    private JButton btnBuscar; // Botón para buscar usuarios por ID
    private final UsuarioDAO usuarioDAO; // DAO para interactuar con la base de datos

    public GestionUsuariosWindow() {
        usuarioDAO = new UsuarioDAO(); // Inicializa el DAO
        inicializarComponentes(); // Configura los componentes de la interfaz
        cargarUsuariosEnTabla(usuarioDAO.obtenerUsuarios()); // Carga los datos iniciales en la tabla

        // Acción para el botón "Agregar"
        btnAgregar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                abrirVentanaAgregarUsuario();
            }
        });

        // Acción para el botón "Modificar"
        btnModificar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                abrirVentanaModificarUsuario();
            }
        });

        // Acción para el botón "Eliminar"
        btnEliminar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                eliminarUsuario();
            }
        });

        // Acción para el botón "Buscar"
        btnBuscar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                buscarUsuarioPorId();
            }
        });
    }

    /**
     * Configura los componentes de la tabla de usuarios.
     */
    private void inicializarComponentes() {
        tableUsuarios.setModel(new DefaultTableModel(
                new Object[][] {}, // Datos iniciales vacíos
                new String[] { "ID", "Nombre", "Usuario", "Rol" } // Columnas de la tabla
        ));
    }

    /**
     * Carga los usuarios obtenidos de la base de datos en la tabla.
     *
     * @param usuarios Lista de usuarios.
     */
    private void cargarUsuariosEnTabla(List<Usuario> usuarios) {
        DefaultTableModel model = (DefaultTableModel) tableUsuarios.getModel();
        model.setRowCount(0); // Limpia la tabla antes de cargar nuevos datos

        for (Usuario usuario : usuarios) {
            model.addRow(new Object[] { usuario.getId(), usuario.getNombre(), usuario.getUsuario(), usuario.getRol() });
        }
    }

    /**
     * Abre la ventana para agregar un nuevo usuario.
     */
    private void abrirVentanaAgregarUsuario() {
        JFrame frame = new JFrame("Nuevo Usuario");
        AgregarUsuarioWindow agregarUsuarioWindow = new AgregarUsuarioWindow();

        frame.setContentPane(agregarUsuarioWindow.getPanelPrincipal());
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null); // Centra la ventana en la pantalla
        frame.setVisible(true);

        // Recarga la tabla cuando se cierra la ventana de agregar
        frame.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosed(java.awt.event.WindowEvent e) {
                cargarUsuariosEnTabla(usuarioDAO.obtenerUsuarios());
            }
        });
    }

    /**
     * Abre la ventana para modificar un usuario seleccionado.
     */
    private void abrirVentanaModificarUsuario() {
        int selectedRow = tableUsuarios.getSelectedRow();

        // Verifica si hay una fila seleccionada
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(null, "Seleccione un usuario para modificar.");
            return;
        }

        // Obtiene el ID del usuario seleccionado
        int usuarioId = (int) tableUsuarios.getValueAt(selectedRow, 0);
        Usuario usuario = usuarioDAO.obtenerUsuarioPorId(usuarioId);

        if (usuario != null) {
            JFrame frame = new JFrame("Editar Usuario");
            ModificarUsuarioWindow modificarUsuarioWindow = new ModificarUsuarioWindow(usuario, usuarioDAO);

            frame.setContentPane(modificarUsuarioWindow.getPanelPrincipal());
            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            frame.pack();
            frame.setLocationRelativeTo(null); // Centra la ventana en la pantalla
            frame.setVisible(true);

            // Recarga la tabla cuando se cierra la ventana de modificación
            frame.addWindowListener(new java.awt.event.WindowAdapter() {
                @Override
                public void windowClosed(java.awt.event.WindowEvent e) {
                    cargarUsuariosEnTabla(usuarioDAO.obtenerUsuarios());
                }
            });
        } else {
            JOptionPane.showMessageDialog(null, "No se pudo obtener la información del usuario seleccionado.");
        }
    }

    /**
     * Elimina un usuario seleccionado de la base de datos.
     */
    private void eliminarUsuario() {
        int selectedRow = tableUsuarios.getSelectedRow();

        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(null, "Seleccione un usuario para eliminar.");
            return;
        }

        int usuarioId = (int) tableUsuarios.getValueAt(selectedRow, 0);
        int confirm = JOptionPane.showConfirmDialog(null, "¿Está seguro de que desea eliminar este usuario?");

        if (confirm == JOptionPane.YES_OPTION) {
            if (usuarioDAO.eliminarUsuario(usuarioId)) {
                JOptionPane.showMessageDialog(null, "Usuario eliminado con éxito.");
                cargarUsuariosEnTabla(usuarioDAO.obtenerUsuarios());
            } else {
                JOptionPane.showMessageDialog(null, "Error al eliminar el usuario.");
            }
        }
    }

    /**
     * Busca un usuario por su ID y lo muestra en la tabla.
     */
    private void buscarUsuarioPorId() {
        String idText = txtBuscarUsuario.getText().trim();

        if (idText.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Ingrese un ID para buscar.");
            return;
        }

        try {
            int usuarioId = Integer.parseInt(idText); // Convertir el texto a entero
            Usuario usuario = usuarioDAO.obtenerUsuarioPorId(usuarioId);

            if (usuario != null) {
                cargarUsuariosEnTabla(List.of(usuario)); // Muestra solo el usuario encontrado
            } else {
                JOptionPane.showMessageDialog(null, "No se encontró ningún usuario con el ID proporcionado.");
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(null, "El ID debe ser un número válido.");
        }
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
