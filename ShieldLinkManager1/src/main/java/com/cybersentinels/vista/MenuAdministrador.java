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
                gestionarUsuarios();
            }
        });

        // Acción para gestionar herramientas
        btnGestionHerramientas.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gestionarHerramientas();
            }
        });

        // Acción para gestionar préstamos
        btnGestionPrestamos.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gestionarPrestamos();
            }
        });

        // Acción para ver reportes
        btnGestionReportes.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                verReportes();
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

    /**
     * Verifica que todos los componentes estén correctamente inicializados.
     * Si algún botón no está vinculado, muestra un error para evitar NullPointerException.
     */
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

    private void gestionarUsuarios() {
        try {
            // Obtener usuarios desde la base de datos
            List<Usuario> usuarios = usuarioDAO.obtenerUsuarios();

            if (usuarios.isEmpty()) {
                JOptionPane.showMessageDialog(null, "No hay usuarios registrados.");
                return;
            }

            // Convertir la lista de usuarios a texto
            String usuariosTexto = convertirUsuariosAString(usuarios);
            JOptionPane.showMessageDialog(null, "Usuarios Registrados:\n" + usuariosTexto);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al gestionar usuarios: " + e.getMessage());
        }
    }

    private void gestionarHerramientas() {
        try {
            // Obtener herramientas desde la base de datos
            List<Herramienta> herramientas = herramientaDAO.obtenerHerramientas();

            if (herramientas.isEmpty()) {
                JOptionPane.showMessageDialog(null, "No hay herramientas registradas.");
                return;
            }

            // Convertir la lista de herramientas a texto
            String herramientasTexto = convertirHerramientasAString(herramientas);
            JOptionPane.showMessageDialog(null, "Herramientas Registradas:\n" + herramientasTexto);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al gestionar herramientas: " + e.getMessage());
        }
    }

    private void gestionarPrestamos() {
        try {
            // Obtener préstamos desde la base de datos
            List<Prestamo> prestamos = prestamoDAO.obtenerPrestamos();

            if (prestamos.isEmpty()) {
                JOptionPane.showMessageDialog(null, "No hay préstamos registrados.");
                return;
            }

            // Convertir la lista de préstamos a texto
            String prestamosTexto = convertirPrestamosAString(prestamos);
            JOptionPane.showMessageDialog(null, "Préstamos Registrados:\n" + prestamosTexto);

            // Opción para editar/eliminar un préstamo
            String[] opciones = {"Editar Préstamo", "Eliminar Préstamo"};
            int seleccion = JOptionPane.showOptionDialog(
                    null,
                    "Seleccione una acción para los préstamos:",
                    "Gestión de Préstamos",
                    JOptionPane.DEFAULT_OPTION,
                    JOptionPane.INFORMATION_MESSAGE,
                    null,
                    opciones,
                    opciones[0]
            );

            if (seleccion == 0) { // Editar Préstamo
                String idStr = JOptionPane.showInputDialog("Ingrese el ID del préstamo a editar:");
                int id = Integer.parseInt(idStr);
                Prestamo prestamo = prestamoDAO.obtenerPrestamoPorId(id);
                if (prestamo != null) {
                    String nuevoEstado = JOptionPane.showInputDialog("Ingrese el nuevo estado del préstamo:");
                    prestamo.setEstado(nuevoEstado);
                    boolean actualizado = prestamoDAO.actualizarPrestamo(prestamo);
                    if (actualizado) {
                        JOptionPane.showMessageDialog(null, "Préstamo actualizado correctamente.");
                    } else {
                        JOptionPane.showMessageDialog(null, "Error al actualizar el préstamo.");
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "No se encontró un préstamo con el ID proporcionado.");
                }
            } else if (seleccion == 1) { // Eliminar Préstamo
                String idStr = JOptionPane.showInputDialog("Ingrese el ID del préstamo a eliminar:");
                int id = Integer.parseInt(idStr);
                boolean eliminado = prestamoDAO.eliminarPrestamo(id);
                if (eliminado) {
                    JOptionPane.showMessageDialog(null, "Préstamo eliminado correctamente.");
                } else {
                    JOptionPane.showMessageDialog(null, "Error al eliminar el préstamo.");
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al gestionar préstamos: " + e.getMessage());
        }
    }

    private void verReportes() {
        try {
            // Generar reporte desde la base de datos
            String reporte = reporteDAO.generarReporte();

            if (reporte == null || reporte.isEmpty()) {
                JOptionPane.showMessageDialog(null, "No hay reportes disponibles.");
                return;
            }

            JOptionPane.showMessageDialog(null, "Reporte:\n" + reporte);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al obtener el reporte: " + e.getMessage());
        }
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

    // Métodos auxiliares para convertir listas a texto
    private String convertirUsuariosAString(List<Usuario> usuarios) {
        StringBuilder usuariosTexto = new StringBuilder();
        for (Usuario usuario : usuarios) {
            usuariosTexto.append("ID: ").append(usuario.getId())
                    .append(", Nombre: ").append(usuario.getNombre())
                    .append(", Rol: ").append(usuario.getRol())
                    .append("\n");
        }
        return usuariosTexto.toString();
    }

    private String convertirHerramientasAString(List<Herramienta> herramientas) {
        StringBuilder herramientasTexto = new StringBuilder();
        for (Herramienta herramienta : herramientas) {
            herramientasTexto.append("ID: ").append(herramienta.getId())
                    .append(", Nombre: ").append(herramienta.getNombre())
                    .append(", Estado: ").append(herramienta.getEstado())
                    .append("\n");
        }
        return herramientasTexto.toString();
    }

    private String convertirPrestamosAString(List<Prestamo> prestamos) {
        StringBuilder prestamosTexto = new StringBuilder();
        for (Prestamo prestamo : prestamos) {
            prestamosTexto.append("ID Préstamo: ").append(prestamo.getId())
                    .append(", Herramienta ID: ").append(prestamo.getHerramientaId())
                    .append(", Usuario ID: ").append(prestamo.getUsuarioId())
                    .append(", Fecha: ").append(prestamo.getFechaPrestamo())
                    .append(", Estado: ").append(prestamo.getEstado())
                    .append("\n");
        }
        return prestamosTexto.toString();
    }

    public JPanel getPanelPrincipal() {
        return panelPrincipal;
    }
}
