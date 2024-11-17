package com.cybersentinels.vista;

import com.cybersentinels.controlador.LoginControlador;
import com.cybersentinels.modelo.Usuario;

import javax.swing.*;

public class LoginWindow {
    private JComboBox<String> comboRoles;
    private JPanel panelPrincipal;
    private JTextField txtUsuario;
    private JPasswordField txtContrasena;
    private JButton btnIniciarSesion;

    private final LoginControlador loginControlador;

    public LoginWindow() {
        loginControlador = new LoginControlador();

        // Listener para Iniciar Sesión
        btnIniciarSesion.addActionListener(e -> iniciarSesion());
    }

    private void iniciarSesion() {
        String usuario = txtUsuario.getText();
        String contrasena = new String(txtContrasena.getPassword());
        String rolSeleccionado = (String) comboRoles.getSelectedItem();

        if (usuario.isEmpty() || contrasena.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Por favor, complete todos los campos.");
            return;
        }

        Usuario usuarioAutenticado = loginControlador.validarCredenciales(usuario, contrasena);

        if (usuarioAutenticado != null && rolSeleccionado != null && rolSeleccionado.equalsIgnoreCase(usuarioAutenticado.getRol())) {
            JOptionPane.showMessageDialog(null, "Inicio de sesión exitoso.");
            redirigirMenu(rolSeleccionado.toLowerCase(), usuarioAutenticado.getId());
        } else {
            JOptionPane.showMessageDialog(null, "Credenciales incorrectas o rol no coincide.");
        }
    }

    private void redirigirMenu(String rol, int usuarioId) {
        JFrame frame = new JFrame("Menu Principal");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Asegura cerrar correctamente

        switch (rol.toLowerCase().trim()) { // Maneja formatos de rol con .toLowerCase() y trim()
            case "administrador":
                try {
                    MenuAdministrador menuAdmin = new MenuAdministrador();
                    frame.setContentPane(menuAdmin.getPanelPrincipal());
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, "Error al cargar el menú administrador: " + e.getMessage());
                    return;
                }
                break;
            case "profesor":
                try {
                    MenuProfesor menuProfesor = new MenuProfesor();
                    frame.setContentPane(menuProfesor.getPanelPrincipal());
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, "Error al cargar el menú profesor: " + e.getMessage());
                    return;
                }
                break;
            case "estudiante":
                try {
                    MenuEstudiante menuEstudiante = new MenuEstudiante(usuarioId);
                    frame.setContentPane(menuEstudiante.getPanelPrincipal());
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, "Error al cargar el menú estudiante: " + e.getMessage());
                    return;
                }
                break;
            default:
                JOptionPane.showMessageDialog(null, "Rol desconocido.");
                return;
        }

        // Configuración del frame
        frame.pack(); // Ajusta el tamaño de la ventana
        frame.setLocationRelativeTo(null); // Centra la ventana
        frame.setVisible(true); // Muestra la ventana

        // Cierra la ventana de login
        SwingUtilities.getWindowAncestor(panelPrincipal).dispose();
    }



    public JPanel getPanelPrincipal() {
        return panelPrincipal;
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Login");
        LoginWindow loginWindow = new LoginWindow();

        if (loginWindow.getPanelPrincipal() != null) {
            frame.setContentPane(loginWindow.getPanelPrincipal());
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        } else {
            System.err.println("Error: panelPrincipal no está inicializado. Revisa la vinculación del formulario.");
        }
    }
}
