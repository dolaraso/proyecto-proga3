package com.cybersentinels.vista;

import com.cybersentinels.dao.HerramientaDAO;
import com.cybersentinels.dao.PrestamoDAO;
import com.cybersentinels.dao.ReporteDAO;
import com.cybersentinels.dao.UsuarioDAO;
import com.cybersentinels.modelo.Herramienta;
import com.cybersentinels.modelo.Prestamo;
import com.cybersentinels.modelo.Usuario;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class MenuAdministrador {
    private JPanel panelPrincipal;
    private JButton btnGestionUsuarios;
    private JButton btnGestionHerramientas;
    private JButton btnGestionPrestamos;
    private JButton btnGestionReportes;
    private JButton btnSalir;

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
        btnGestionUsuarios.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                abrirGestionUsuarios();
            }
        });

        // Acción para gestionar herramientas
        btnGestionHerramientas.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                abrirGestionHerramientas();
            }
        });

        // Acción para gestionar préstamos
        btnGestionPrestamos.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                abrirGestionPrestamos();
            }
        });

        // Acción para ver reportes
        btnGestionReportes.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                abrirGenerarReportes();
            }
        });

        // Acción para cerrar sesión
        btnSalir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cerrarSesion();
            }
        });
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


    public JPanel getPanelPrincipal() {
        return panelPrincipal;
    }
}
