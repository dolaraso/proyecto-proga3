package com.cybersentinels.controlador;

import com.cybersentinels.dao.UsuarioDAO;
import com.cybersentinels.modelo.Usuario;

public class LoginControlador {
    private UsuarioDAO usuarioDAO;

    public LoginControlador() {
        usuarioDAO = new UsuarioDAO();
    }

    public Usuario validarCredenciales(String usuario, String contrasena) {
        return usuarioDAO.validarUsuario(usuario, contrasena);
    }

    public boolean registrarAcceso(int usuarioId, String rol) {
        return usuarioDAO.registrarAcceso(usuarioId, rol);
    }
}
