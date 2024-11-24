package com.cybersentinels.vista;

import com.cybersentinels.dao.HerramientaDAO;
import com.cybersentinels.modelo.Herramienta;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.List;

public class GestionHerramientasWindow {
    private JPanel panelPrincipal;
    private JTable tableHerramientas;
    private JButton btnSeleccionar;
    private JButton btnMantenimiento;
    private JButton btnAgregar;
    private JButton btnModificar;
    private JButton btnEliminar;
    private JTextField txtBuscarNombre;
    private JButton btnBuscar;
    private JButton btnLevantarMantenimiento;

    private final HerramientaDAO herramientaDAO;
    private Herramienta herramientaSeleccionada;

    public GestionHerramientasWindow() {
        herramientaDAO = new HerramientaDAO();
        inicializarTabla();
        cargarHerramientasEnTabla();

        // Acciones de los botones
        btnSeleccionar.addActionListener(e -> seleccionarHerramienta());
        btnMantenimiento.addActionListener(e -> cambiarEstadoMantenimiento());
        btnLevantarMantenimiento.addActionListener(e -> levantarMantenimiento());
        btnAgregar.addActionListener(e -> abrirVentanaAgregarHerramienta());
        btnModificar.addActionListener(e -> abrirVentanaModificarHerramienta());
        btnEliminar.addActionListener(e -> eliminarHerramientaSeleccionada());
        btnBuscar.addActionListener(e -> buscarHerramientaPorNombre());
    }

    private void inicializarTabla() {
        tableHerramientas.setModel(new DefaultTableModel(
                new Object[][]{},
                new String[]{"ID", "Nombre", "Descripción", "Estado", "Tipo", "Cantidad"}
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
                    herramienta.getTipo(),
                    herramienta.getCantidad() // Mostrar cantidad
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
                        herramienta.getTipo(),
                        herramienta.getCantidad() // Mostrar cantidad
                });
            }
        }
    }

    private void seleccionarHerramienta() {
        int selectedRow = tableHerramientas.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(null, "Seleccione una herramienta en la tabla.");
            return;
        }

        int herramientaId = (int) tableHerramientas.getValueAt(selectedRow, 0);
        herramientaSeleccionada = herramientaDAO.obtenerHerramientaPorId(herramientaId);

        if (herramientaSeleccionada != null) {
            JOptionPane.showMessageDialog(null, "Herramienta seleccionada: " + herramientaSeleccionada.getNombre());
        } else {
            JOptionPane.showMessageDialog(null, "No se pudo seleccionar la herramienta.");
        }
    }

    private void cambiarEstadoMantenimiento() {
        if (herramientaSeleccionada == null) {
            JOptionPane.showMessageDialog(null, "Primero debe seleccionar una herramienta.");
            return;
        }

        herramientaSeleccionada.setEstado("Mantenimiento");
        boolean actualizado = herramientaDAO.actualizarHerramienta(herramientaSeleccionada);

        if (actualizado) {
            JOptionPane.showMessageDialog(null, "Estado de la herramienta cambiado a 'Mantenimiento'.");
            cargarHerramientasEnTabla(); // Refrescar la tabla
        } else {
            JOptionPane.showMessageDialog(null, "No se pudo actualizar el estado de la herramienta.");
        }

        herramientaSeleccionada = null; // Reiniciar selección
    }

    private void levantarMantenimiento() {
        if (herramientaSeleccionada == null) {
            JOptionPane.showMessageDialog(null, "Primero debe seleccionar una herramienta.");
            return;
        }

        if (!"Mantenimiento".equalsIgnoreCase(herramientaSeleccionada.getEstado())) {
            JOptionPane.showMessageDialog(null, "La herramienta seleccionada no está en mantenimiento.");
            return;
        }

        herramientaSeleccionada.setEstado("Disponible");
        boolean actualizado = herramientaDAO.actualizarHerramienta(herramientaSeleccionada);

        if (actualizado) {
            JOptionPane.showMessageDialog(null, "La herramienta ahora está disponible.");
            cargarHerramientasEnTabla(); // Refrescar la tabla
        } else {
            JOptionPane.showMessageDialog(null, "No se pudo levantar el mantenimiento de la herramienta.");
        }

        herramientaSeleccionada = null; // Reiniciar selección
    }

    private void abrirVentanaAgregarHerramienta() {
        JFrame frame = new JFrame("Agregar Herramienta");
        AgregarHerramientaWindow agregarHerramientaWindow = new AgregarHerramientaWindow(herramientaDAO);

        frame.setContentPane(agregarHerramientaWindow.getPanelPrincipal());
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        frame.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosed(java.awt.event.WindowEvent e) {
                cargarHerramientasEnTabla();
            }
        });
    }

    private void abrirVentanaModificarHerramienta() {
        int selectedRow = tableHerramientas.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(null, "Seleccione una herramienta para modificar.");
            return;
        }

        int herramientaId = (int) tableHerramientas.getValueAt(selectedRow, 0);
        Herramienta herramienta = herramientaDAO.obtenerHerramientaPorId(herramientaId);

        if (herramienta != null) {
            JFrame frame = new JFrame("Modificar Herramienta");
            ModificarHerramientaWindow modificarHerramientaWindow = new ModificarHerramientaWindow(herramienta, herramientaDAO);

            frame.setContentPane(modificarHerramientaWindow.getPanelPrincipal());
            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);

            frame.addWindowListener(new java.awt.event.WindowAdapter() {
                @Override
                public void windowClosed(java.awt.event.WindowEvent e) {
                    cargarHerramientasEnTabla();
                }
            });
        } else {
            JOptionPane.showMessageDialog(null, "No se pudo obtener la información de la herramienta seleccionada.");
        }
    }

    private void eliminarHerramientaSeleccionada() {
        int selectedRow = tableHerramientas.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(null, "Seleccione una herramienta para eliminar.");
            return;
        }

        int herramientaId = (int) tableHerramientas.getValueAt(selectedRow, 0);
        int confirm = JOptionPane.showConfirmDialog(null, "¿Está seguro de que desea eliminar la herramienta?");
        if (confirm == JOptionPane.YES_OPTION) {
            if (herramientaDAO.eliminarHerramienta(herramientaId)) {
                JOptionPane.showMessageDialog(null, "Herramienta eliminada con éxito.");
                cargarHerramientasEnTabla();
            } else {
                JOptionPane.showMessageDialog(null, "Error al eliminar la herramienta.");
            }
        }
    }

    public JPanel getPanelPrincipal() {
        return panelPrincipal;
    }
}
