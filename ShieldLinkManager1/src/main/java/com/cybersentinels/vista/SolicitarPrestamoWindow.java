package com.cybersentinels.vista;

import com.cybersentinels.dao.HerramientaDAO;
import com.cybersentinels.dao.PrestamoDAO;
import com.cybersentinels.modelo.Herramienta;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import javax.swing.table.JTableHeader;
import java.awt.Font;
import java.awt.Color;

public class SolicitarPrestamoWindow {
    private JPanel panelPrincipal;
    private JTable tableHerramientas;
    private JButton btnSolicitarPrestamo;
    private JTextField txtBuscarNombre;
    private JButton btnBuscar;
    private JTextArea txtDescripcion;
    private JTextField txtFechaInicio;
    private JTextField txtFechaDevolucion;
    private JTextField txtCantidad;

    private final HerramientaDAO herramientaDAO;
    private final PrestamoDAO prestamoDAO;
    private final int usuarioId;
    private final String tipoUsuario;

    public SolicitarPrestamoWindow(int usuarioId, String tipoUsuario) {
        this.usuarioId = usuarioId;
        this.tipoUsuario = tipoUsuario;
        herramientaDAO = new HerramientaDAO();
        prestamoDAO = new PrestamoDAO();

        inicializarTabla();
        cargarHerramientasDisponibles();
        inicializarFechaInicio();

        // Acción de botones
        btnBuscar.addActionListener(e -> buscarHerramientaPorNombre());
        btnSolicitarPrestamo.addActionListener(e -> solicitarPrestamo());
    }
    private void inicializarFechaInicio() {
        // Obtener la fecha actual del sistema
        LocalDate fechaActual = LocalDate.now();
        // Formatear la fecha en el formato "yyyy-MM-dd"
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        // Asignar la fecha actual al campo de texto
        txtFechaInicio.setText(fechaActual.format(formatter));
    }

    private void inicializarTabla() {
        tableHerramientas.setModel(new DefaultTableModel(
                new Object[][]{},
                new String[]{"ID", "Nombre de Equipo", "Descripción", "Disponible", "Tipo", "Cantidad Disponible"}
        ));

        // Configuración del encabezado de la tabla
        JTableHeader header = tableHerramientas.getTableHeader();
        header.setReorderingAllowed(false);
        header.setFont(new Font("Arial", Font.BOLD, 14));
        header.setBackground(new Color(45, 42, 46));
        header.setForeground(new Color(252, 252, 250));
    }

    private void cargarHerramientasDisponibles() {
        DefaultTableModel model = (DefaultTableModel) tableHerramientas.getModel();
        model.setRowCount(0); // Limpiar la tabla

        List<Herramienta> herramientas = herramientaDAO.obtenerHerramientasPorEstado("Disponible");
        for (Herramienta herramienta : herramientas) {
            model.addRow(new Object[]{
                    herramienta.getId(),
                    herramienta.getNombre(),
                    herramienta.getDescripcion(),
                    herramienta.getEstado().equalsIgnoreCase("Disponible") ? "Sí" : "No",
                    herramienta.getTipo(),
                    herramienta.getCantidad()
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
        DefaultTableModel model = (DefaultTableModel) tableHerramientas.getModel();
        model.setRowCount(0); // Limpiar la tabla
        for (Herramienta herramienta : herramientas) {
            if (herramienta.getEstado().equalsIgnoreCase("Disponible")) {
                model.addRow(new Object[]{
                        herramienta.getId(),
                        herramienta.getNombre(),
                        herramienta.getDescripcion(),
                        herramienta.getEstado().equals("Disponible") ? "Sí" : "No",
                        herramienta.getTipo(),
                        herramienta.getCantidad()
                });
            }
        }
    }
    private void solicitarPrestamo() {
        int selectedRow = tableHerramientas.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(null, "Seleccione una herramienta para solicitar el préstamo.");
            return;
        }

        int herramientaId = (int) tableHerramientas.getValueAt(selectedRow, 0);
        String descripcion = txtDescripcion.getText().trim();

        if (descripcion.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Por favor, ingrese una descripción del uso.");
            return;
        }

        // Validar cantidad ingresada
        int cantidadSolicitada;
        try {
            cantidadSolicitada = Integer.parseInt(txtCantidad.getText().trim());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Por favor, ingrese un valor numérico válido para la cantidad.");
            return;
        }

        int cantidadDisponible = (int) tableHerramientas.getValueAt(selectedRow, 5);
        if (cantidadSolicitada <= 0 || cantidadSolicitada > cantidadDisponible) {
            JOptionPane.showMessageDialog(null, "La cantidad solicitada no es válida o supera la cantidad disponible.");
            return;
        }

        try {
            // Validar límite de préstamos para estudiantes y profesores
            int maxPrestamos = tipoUsuario.equalsIgnoreCase("Estudiante") ? 3 : 30;
            int herramientasAprobadas = prestamoDAO.obtenerCantidadHerramientasAprobadas(usuarioId);

            if (herramientasAprobadas >= maxPrestamos) {
                JOptionPane.showMessageDialog(null, "Ya alcanzaste el límite de herramientas aprobadas. Devuelve las herramientas antes de solicitar más.");
                return;
            }

            // Validar si hay préstamos activos o pendientes
            int prestamosActivosYAprobados = prestamoDAO.obtenerCantidadPrestamosActivosYAprobados(usuarioId);
            if (prestamosActivosYAprobados + cantidadSolicitada > maxPrestamos) {
                JOptionPane.showMessageDialog(null, "No puedes solicitar más herramientas. Has alcanzado el límite permitido.");
                return;
            }

            // Convertir las fechas de los campos de texto a LocalDate
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate fechaInicio = LocalDate.parse(txtFechaInicio.getText().trim(), formatter);
            LocalDate fechaDevolucion = LocalDate.parse(txtFechaDevolucion.getText().trim(), formatter);

            if (fechaDevolucion.isBefore(fechaInicio)) {
                JOptionPane.showMessageDialog(null, "La fecha de devolución no puede ser anterior a la fecha de inicio.");
                return;
            }

            // Solicitar el préstamo
            boolean prestamoExitoso = prestamoDAO.solicitarPrestamo(
                    herramientaId,
                    usuarioId,
                    descripcion,
                    fechaInicio,
                    fechaDevolucion,
                    cantidadSolicitada
            );

            if (prestamoExitoso) {
                JOptionPane.showMessageDialog(null, "Préstamo solicitado con éxito.");
                cargarHerramientasDisponibles(); // Refrescar la tabla
            } else {
                JOptionPane.showMessageDialog(null, "Error al solicitar el préstamo.");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Por favor, ingrese las fechas en el formato correcto (yyyy-MM-dd).");
        }
    }


    public JPanel getPanelPrincipal() {
        return panelPrincipal;
    }
}
