package com.cybersentinels.vista;

import com.cybersentinels.dao.HerramientaDAO;
import com.cybersentinels.dao.PrestamoDAO;
import com.cybersentinels.dao.UsuarioDAO;
import com.cybersentinels.modelo.Herramienta;
import com.cybersentinels.modelo.Prestamo;
import com.cybersentinels.modelo.Usuario;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.print.*;
import java.util.List;

public class GenerarReportesWindow {
    private JPanel panelPrincipal;
    private JTable tableReportes;
    private JButton btnDescargarPDF;

    private final HerramientaDAO herramientaDAO;
    private final UsuarioDAO usuarioDAO;
    private final PrestamoDAO prestamoDAO;

    public GenerarReportesWindow() {
        herramientaDAO = new HerramientaDAO();
        usuarioDAO = new UsuarioDAO();
        prestamoDAO = new PrestamoDAO();

        panelPrincipal = new JPanel(new BorderLayout());
        tableReportes = new JTable();
        btnDescargarPDF = new JButton("Descargar PDF");

        panelPrincipal.add(new JScrollPane(tableReportes), BorderLayout.CENTER);
        panelPrincipal.add(btnDescargarPDF, BorderLayout.SOUTH);

        btnDescargarPDF.addActionListener(e -> generarReportePDF());
        cargarDatosEnTabla();
    }

    private void cargarDatosEnTabla() {
        List<Herramienta> herramientas = herramientaDAO.obtenerHerramientas();
        List<Usuario> usuarios = usuarioDAO.obtenerUsuarios();
        List<Prestamo> prestamos = prestamoDAO.obtenerPrestamos();

        String[] columnNames = {"Tipo", "Nombre", "Descripción"};
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);

        for (Herramienta herramienta : herramientas) {
            model.addRow(new Object[]{"Herramienta", herramienta.getNombre(), herramienta.getDescripcion()});
        }
        for (Usuario usuario : usuarios) {
            model.addRow(new Object[]{"Usuario", usuario.getNombre(), usuario.getRol()});
        }
        for (Prestamo prestamo : prestamos) {
            model.addRow(new Object[]{"Préstamo", prestamo.getDescripcionUso(), prestamo.getEstado()});
        }

        tableReportes.setModel(model);
    }

    private void generarReportePDF() {
        PrinterJob printerJob = PrinterJob.getPrinterJob();

        printerJob.setPrintable((graphics, pageFormat, pageIndex) -> {
            if (pageIndex > 0) {
                return Printable.NO_SUCH_PAGE;
            }

            Graphics2D g2d = (Graphics2D) graphics;
            g2d.translate(pageFormat.getImageableX(), pageFormat.getImageableY());

            // Título del reporte
            g2d.drawString("Reporte Completo", 100, 50);

            // Obtener modelo de tabla
            DefaultTableModel model = (DefaultTableModel) tableReportes.getModel();
            int y = 100;

            // Dibujar encabezados de la tabla
            g2d.drawString("Tipo", 50, y);
            g2d.drawString("Nombre", 150, y);
            g2d.drawString("Descripción", 300, y);
            y += 20;

            // Dibujar filas de datos
            for (int i = 0; i < model.getRowCount(); i++) {
                g2d.drawString(model.getValueAt(i, 0).toString(), 50, y);
                g2d.drawString(model.getValueAt(i, 1).toString(), 150, y);
                g2d.drawString(model.getValueAt(i, 2).toString(), 300, y);
                y += 20;
            }

            return Printable.PAGE_EXISTS;
        });

        if (printerJob.printDialog()) {
            try {
                printerJob.print();
                JOptionPane.showMessageDialog(null, "Reporte enviado a imprimir.");
            } catch (PrinterException e) {
                JOptionPane.showMessageDialog(null, "Error al generar el reporte: " + e.getMessage());
            }
        }
    }

    public JPanel getPanelReportes() {
        return panelPrincipal;
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Reportes");
        GenerarReportesWindow generarReportesWindow = new GenerarReportesWindow();
        frame.setContentPane(generarReportesWindow.getPanelReportes());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
