package com.cybersentinels.vista;

import com.cybersentinels.controlador.LoginControlador;
import com.cybersentinels.modelo.Usuario;

import javax.swing.*;

public class LoginWindow {
    private JComboBox<String> comboRoles; // Este campo parece faltar según el error
    private JPanel panelPrincipal;
    private JTextField txtUsuario;
    private JPasswordField txtContrasena;
    private JButton btnIniciarSesion;
    private JLabel lblUsuario;
    private JLabel lblContrasena;

    private LoginControlador loginControlador;

    public LoginWindow() {
        loginControlador = new LoginControlador();

        // Evento para el botón de iniciar sesión
        btnIniciarSesion.addActionListener(e -> iniciarSesion());
    }

    private void iniciarSesion() {
        String usuario = txtUsuario.getText();
        String contrasena = new String(txtContrasena.getPassword());

        // Validar credenciales
        Usuario usuarioAutenticado = loginControlador.validarCredenciales(usuario, contrasena);

        if (usuarioAutenticado != null) {
            JOptionPane.showMessageDialog(null, "Inicio de sesión exitoso.");
            loginControlador.registrarAcceso(usuarioAutenticado.getId(), usuarioAutenticado.getRol());
            redirigirMenu(usuarioAutenticado.getRol(), usuarioAutenticado.getId());
        } else {
            JOptionPane.showMessageDialog(null, "Credenciales incorrectas.");
        }
    }

    private void redirigirMenu(String rol, int usuarioId) {
        JFrame frame = new JFrame();
        switch (rol.toLowerCase()) {
            case "admin":
                MenuAdministrador menuAdmin = new MenuAdministrador();
                frame.setContentPane(menuAdmin.getPanelPrincipal());
                break;
            case "profesor":
                MenuProfesor menuProfesor = new MenuProfesor();
                frame.setContentPane(menuProfesor.getPanelPrincipal());
                break;
            case "estudiante":
                MenuEstudiante menuEstudiante = new MenuEstudiante(usuarioId);
                frame.setContentPane(menuEstudiante.getPanelPrincipal());
                break;
            default:
                JOptionPane.showMessageDialog(null, "Rol desconocido.");
                return;
        }

        // Configuración del frame
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        // Cerrar la ventana de login
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
