package com.cybersentinels.vista;

import com.cybersentinels.dao.HerramientaDAO;
import com.cybersentinels.modelo.Herramienta;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.List;

public class MenuEstudiante {
    private JPanel panelPrincipal;
    private JTable tableHerramientas;
    private JButton btnBuscar;
    private JTextField txtBuscarNombre;
    private JButton btnMultas;
    private JButton btnSolicitarPrestamo;
    private JButton btnSalir;
    private JButton btnVerPrestamos;

    private final HerramientaDAO herramientaDAO;
    private int usuarioId;

    public MenuEstudiante(int usuarioId) {
        this.usuarioId = usuarioId; // Guardamos el ID del usuario
        herramientaDAO = new HerramientaDAO();
        inicializarTabla();
        cargarHerramientasEnTabla();

        // Acción del botón buscar
        btnBuscar.addActionListener(e -> buscarHerramientaPorNombre());
        btnSalir.addActionListener(e -> cerrarSesion());
    }

    private void inicializarTabla() {
        tableHerramientas.setModel(new DefaultTableModel(
                new Object[][]{},
                new String[]{"ID", "Nombre", "Descripción", "Estado", "Tipo"}
        ));
    }

    private void cargarHerramientasEnTabla() {
        DefaultTableModel model = (DefaultTableModel) tableHerramientas.getModel();
        model.setRowCount(0); // Limpiar la tabla
        List<Herramienta> herramientas = herramientaDAO.obtenerHerramientas();
        for (Herramienta herramienta : herramientas) {
            model.addRow(new Object[]{
                    herramienta.getId(),
                    herramienta.getNombre(),
                    herramienta.getDescripcion(),
                    herramienta.getEstado(),
                    herramienta.getTipo()
            });
        }
    }

    private void buscarHerramientaPorNombre() {
        String nombre = txtBuscarNombre.getText().trim();
        if (nombre.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Por favor, ingrese un nombre para buscar.");
            return;
        }

        List<Herramienta> herramientas = herramientaDAO.buscarHerramientasPorNombre(nombre);
        if (herramientas.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No se encontraron herramientas con el nombre: " + nombre);
        } else {
            DefaultTableModel model = (DefaultTableModel) tableHerramientas.getModel();
            model.setRowCount(0); // Limpiar la tabla
            for (Herramienta herramienta : herramientas) {
                model.addRow(new Object[]{
                        herramienta.getId(),
                        herramienta.getNombre(),
                        herramienta.getDescripcion(),
                        herramienta.getEstado(),
                        herramienta.getTipo()
                });
            }
        }
    }

    private void cerrarSesion() {
        int opcion = JOptionPane.showConfirmDialog(null, "¿Seguro que desea cerrar sesión?", "Confirmar", JOptionPane.YES_NO_OPTION);
        if (opcion == JOptionPane.YES_OPTION) {
            JFrame frame = new JFrame("Login");
            frame.setContentPane(new LoginWindow().getPanelPrincipal());
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);

            SwingUtilities.getWindowAncestor(panelPrincipal).dispose();
        }
    }

    public JPanel getPanelPrincipal() {
        return panelPrincipal;
    }
}
