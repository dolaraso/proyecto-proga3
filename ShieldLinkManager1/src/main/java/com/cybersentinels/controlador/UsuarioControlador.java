package com.cybersentinels.controlador;

import com.cybersentinels.dao.UsuarioDAO;
import com.cybersentinels.modelo.Usuario;

import java.util.List;

public class UsuarioControlador {
    private UsuarioDAO usuarioDAO;

    public UsuarioControlador() {
        usuarioDAO = new UsuarioDAO();
    }

    public Usuario validarCredenciales(String usuario, String contrasena) {
        return usuarioDAO.validarUsuario(usuario, contrasena);
    }

    public boolean agregarUsuario(String nombre, String usuario, String contrasena, String rol) {
        Usuario nuevoUsuario = new Usuario(0, nombre, usuario, contrasena, rol);
        return usuarioDAO.agregarUsuario(nuevoUsuario);
    }

    public List<Usuario> obtenerTodosUsuarios() {
        return usuarioDAO.obtenerUsuarios();
    }

    public boolean actualizarUsuario(int id, String nombre, String usuario, String contrasena, String rol) {
        Usuario usuarioActualizado = new Usuario(id, nombre, usuario, contrasena, rol);
        return usuarioDAO.actualizarUsuario(usuarioActualizado);
    }

    public boolean eliminarUsuario(int id) {
        return usuarioDAO.eliminarUsuario(id);
    }

    public Usuario obtenerUsuarioPorId(int id) {
        return usuarioDAO.buscarUsuarioPorId(id);
    }
}
