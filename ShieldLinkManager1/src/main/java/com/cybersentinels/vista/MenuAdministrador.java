package com.cybersentinels.vista;

import com.cybersentinels.dao.HerramientaDAO;
import com.cybersentinels.dao.PrestamoDAO;
import com.cybersentinels.dao.ReporteDAO;
import com.cybersentinels.dao.UsuarioDAO;
import com.cybersentinels.modelo.Herramienta;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.util.List;

public class MenuAdministrador {
    private JPanel panelPrincipal;
    private JButton btnGestionUsuarios;
    private JButton btnGestionHerramientas;
    private JButton btnGestionPrestamos;
    private JButton btnGestionReportes;
    private JButton btnSalir;
    private JButton btnCreditos;

    private final UsuarioDAO usuarioDAO;
    private final HerramientaDAO herramientaDAO;
    private final PrestamoDAO prestamoDAO;
    private final ReporteDAO reporteDAO;

    public MenuAdministrador() {
        // Verificar inicialización de componentes
        verificarComponentes();

        // Inicializar DAOs
        usuarioDAO = new UsuarioDAO();
        herramientaDAO = new HerramientaDAO();
        prestamoDAO = new PrestamoDAO();
        reporteDAO = new ReporteDAO();

        // Acción para gestionar usuarios
        btnGestionUsuarios.addActionListener(e -> abrirGestionUsuarios());

        // Acción para gestionar herramientas
        btnGestionHerramientas.addActionListener(e -> abrirGestionHerramientas());

        // Acción para gestionar préstamos
        btnGestionPrestamos.addActionListener(e -> abrirGestionPrestamos());

        // Acción para ver reportes
        btnGestionReportes.addActionListener(e -> abrirGenerarReportes());

        // Acción para el botón Créditos
        btnCreditos.addActionListener(e -> abrirCreditosWindow());

        // Acción para cerrar sesión
        btnSalir.addActionListener(e -> cerrarSesion());
    }

    private void verificarComponentes() {
        if (btnGestionUsuarios == null) {
            throw new IllegalStateException("El botón 'btnGestionUsuarios' no está inicializado. Revisa la vinculación en el archivo .form.");
        }
        if (btnGestionHerramientas == null) {
            throw new IllegalStateException("El botón 'btnGestionHerramientas' no está inicializado. Revisa la vinculación en el archivo .form.");
        }
        if (btnGestionPrestamos == null) {
            throw new IllegalStateException("El botón 'btnGestionPrestamos' no está inicializado. Revisa la vinculación en el archivo .form.");
        }
        if (btnGestionReportes == null) {
            throw new IllegalStateException("El botón 'btnGestionReportes' no está inicializado. Revisa la vinculación en el archivo .form.");
        }
        if (btnSalir == null) {
            throw new IllegalStateException("El botón 'btnSalir' no está inicializado. Revisa la vinculación en el archivo .form.");
        }
        if (btnCreditos == null) {
            throw new IllegalStateException("El botón 'btnCreditos' no está inicializado. Revisa la vinculación en el archivo .form.");
        }
    }

    private void abrirGestionUsuarios() {
        JFrame frame = new JFrame("Gestión de Usuarios");
        GestionUsuariosWindow gestionUsuariosWindow = new GestionUsuariosWindow();
        frame.setContentPane(gestionUsuariosWindow.getPanelPrincipal());
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private void abrirGestionHerramientas() {
        JFrame frame = new JFrame("Gestión de Herramientas");
        GestionHerramientasWindow gestionHerramientasWindow = new GestionHerramientasWindow();
        frame.setContentPane(gestionHerramientasWindow.getPanelPrincipal());
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        // Mostrar cantidad total de herramientas
        mostrarCantidadHerramientas();
    }

    private void abrirGestionPrestamos() {
        JFrame frame = new JFrame("Gestión de Préstamos");
        GestionPrestamosWindow gestionPrestamosWindow = new GestionPrestamosWindow();
        frame.setContentPane(gestionPrestamosWindow.getPanelPrincipal());
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private void abrirCreditosWindow() {
        JFrame frame = new JFrame("Créditos");
        CreditosWindow creditosWindow = new CreditosWindow(); // Crear instancia de la ventana Créditos
        frame.setContentPane(creditosWindow.getPanelPrincipal()); // Establecer el panel principal
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Configurar el cierre de la ventana
        frame.pack();
        frame.setLocationRelativeTo(null); // Centrar ventana
        frame.setVisible(true); // Mostrar la ventana
    }

    private void abrirGenerarReportes() {
        JFrame frame = new JFrame("Generar Reportes");
        GenerarReportesWindow generarReportesWindow = new GenerarReportesWindow();
        frame.setContentPane(generarReportesWindow.getPanelPrincipal());
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private void cerrarSesion() {
        JFrame frame = new JFrame("Login");
        LoginWindow loginWindow = new LoginWindow();
        frame.setContentPane(loginWindow.getPanelPrincipal());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        SwingUtilities.getWindowAncestor(panelPrincipal).dispose();
    }

    private void mostrarCantidadHerramientas() {
        List<Herramienta> herramientas = herramientaDAO.obtenerHerramientas();
        int totalCantidad = herramientas.stream()
                .mapToInt(Herramienta::getCantidad)
                .sum();

        JOptionPane.showMessageDialog(null, "Cantidad total de herramientas disponibles: " + totalCantidad);
    }

    public JPanel getPanelPrincipal() {
        return panelPrincipal;
    }
}
