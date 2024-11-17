package com.cybersentinels.vista;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginWindow {
    private JPanel panelLogin;
    private JComboBox<String> comboRoles;
    private JTextField txtUsuario;
    private JPasswordField txtContrasena;
    private JButton btnIniciarSesion;

    public LoginWindow() {
        btnIniciarSesion.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String rolSeleccionado = (String) comboRoles.getSelectedItem();
                String usuario = txtUsuario.getText();
                String contrasena = new String(txtContrasena.getPassword());

                // Validación de credenciales (Simulada)
                if (validarCredenciales(usuario, contrasena, rolSeleccionado)) {
                    abrirMenuPrincipal(rolSeleccionado);
                } else {
                    JOptionPane.showMessageDialog(null, "Usuario o contraseña incorrectos.");
                }
            }
        });
    }

    private boolean validarCredenciales(String usuario, String contrasena, String rol) {
        // Aquí conectas la lógica de validación con la base de datos.
        // Simulación de validación:
        return "admin".equals(usuario) && "admin123".equals(contrasena) && "Administrador".equals(rol);
    }

    private void abrirMenuPrincipal(String rol) {
        JFrame frame = new JFrame("Menú Principal - " + rol);
        switch (rol) {
            case "Administrador":
                frame.setContentPane(new MenuAdministrador().getPanelAdministrador());
                break;
            case "Estudiante":
                frame.setContentPane(new MenuEstudiante().getPanelEstudiante());
                break;
            case "Profesor":
                frame.setContentPane(new MenuProfesor().getPanelProfesor());
                break;
        }
        frame.pack();
        frame.setVisible(true);
    }

    public JPanel getPanelLogin() {
        return panelLogin;
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Login");
        frame.setContentPane(new LoginWindow().getPanelLogin());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
