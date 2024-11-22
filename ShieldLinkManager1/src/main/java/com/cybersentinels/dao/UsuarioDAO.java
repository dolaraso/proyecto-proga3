package com.cybersentinels.dao;

import com.cybersentinels.modelo.Usuario;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDAO {
    private Connection conexion;

    public UsuarioDAO() {
        conexion = ConexionDB.conectar();
    }

    public Usuario validarUsuario(String usuario, String contrasena) {
        String sql = "SELECT * FROM usuarios WHERE usuario = ? AND contrasena = ?";
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setString(1, usuario);
            stmt.setString(2, contrasena);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Usuario(
                        rs.getInt("id"),
                        rs.getString("nombre"),
                        rs.getString("usuario"),
                        rs.getString("contrasena"),
                        rs.getString("rol"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean agregarUsuario(Usuario usuario) {
        int nuevoId = obtenerSiguienteId(); // Calcula el siguiente ID
        String sql = "INSERT INTO usuarios (id, nombre, usuario, contrasena, rol) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setInt(1, nuevoId); // Asigna el próximo ID manualmente
            stmt.setString(2, usuario.getNombre());
            stmt.setString(3, usuario.getUsuario());
            stmt.setString(4, usuario.getContrasena());
            stmt.setString(5, usuario.getRol());
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


    /**
     * Calcula el siguiente ID disponible en la tabla usuarios.
     *
     * @return El próximo ID disponible.
     */
    private int obtenerSiguienteId() {
        String sql = "SELECT id FROM usuarios ORDER BY id";
        try (Statement stmt = conexion.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            int esperado = 1; // Primer ID esperado
            while (rs.next()) {
                if (rs.getInt("id") != esperado) {
                    return esperado; // Retorna el primer ID faltante
                }
                esperado++;
            }
            return esperado; // Si no hay faltantes, retorna el siguiente número
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 1; // Por defecto, retorna 1 si no hay usuarios
    }



    public Usuario obtenerUsuarioPorId(int id) {
        String sql = "SELECT * FROM usuarios WHERE id = ?";
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Usuario(
                        rs.getInt("id"),
                        rs.getString("nombre"),
                        rs.getString("usuario"),
                        rs.getString("contrasena"),
                        rs.getString("rol"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null; // Retorna null si no encuentra el usuario
    }

    public List<Usuario> obtenerUsuarios() {
        List<Usuario> usuarios = new ArrayList<>();
        String sql = "SELECT * FROM usuarios";
        try (Statement stmt = conexion.createStatement();
                ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                usuarios.add(new Usuario(
                        rs.getInt("id"),
                        rs.getString("nombre"),
                        rs.getString("usuario"),
                        rs.getString("contrasena"),
                        rs.getString("rol")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return usuarios;
    }

    public boolean actualizarUsuario(Usuario usuario) {
        String sql = "UPDATE usuarios SET nombre = ?, usuario = ?, contrasena = ?, rol = ? WHERE id = ?";
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setString(1, usuario.getNombre());
            stmt.setString(2, usuario.getUsuario());
            stmt.setString(3, usuario.getContrasena());
            stmt.setString(4, usuario.getRol());
            stmt.setInt(5, usuario.getId());
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean eliminarUsuario(int id) {
        String sqlEliminar = "DELETE FROM usuarios WHERE id = ?";
        String sqlReiniciarAutoIncrement = "ALTER TABLE usuarios AUTO_INCREMENT = 1";
        try (PreparedStatement stmtEliminar = conexion.prepareStatement(sqlEliminar);
             Statement stmtReiniciar = conexion.createStatement()) {
            stmtEliminar.setInt(1, id);
            stmtEliminar.executeUpdate();
            stmtReiniciar.executeUpdate(sqlReiniciarAutoIncrement); // Reinicia el AUTO_INCREMENT
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


    public boolean registrarAcceso(int usuarioId, String rol) {
        String sql = "INSERT INTO logs (usuario_id, rol, fecha_acceso) VALUES (?, ?, NOW())";
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setInt(1, usuarioId);
            stmt.setString(2, rol);
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public Usuario buscarUsuarioPorId(int id) {
        String sql = "SELECT * FROM usuarios WHERE id = ?";
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Usuario(
                        rs.getInt("id"),
                        rs.getString("nombre"),
                        rs.getString("usuario"),
                        rs.getString("contrasena"),
                        rs.getString("rol"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    public boolean modificarUsuario(Usuario usuario) {
        String sql = "UPDATE usuarios SET nombre = ?, usuario = ?, contrasena = ?, rol = ? WHERE id = ?";
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setString(1, usuario.getNombre());
            stmt.setString(2, usuario.getUsuario());
            stmt.setString(3, usuario.getContrasena());
            stmt.setString(4, usuario.getRol());
            stmt.setInt(5, usuario.getId());

            int filasActualizadas = stmt.executeUpdate();
            return filasActualizadas > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


}
