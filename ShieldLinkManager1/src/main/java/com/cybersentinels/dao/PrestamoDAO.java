package com.cybersentinels.dao;

import com.cybersentinels.modelo.Prestamo;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PrestamoDAO {
    private final Connection conexion;

    public PrestamoDAO() {
        conexion = ConexionDB.conectar();
    }
    public boolean solicitarPrestamo(int herramientaId, int usuarioId) {
        String sql = "INSERT INTO prestamos (herramienta_id, usuario_id, fecha_prestamo, estado) VALUES (?, ?, NOW(), 'pendiente')";
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setInt(1, herramientaId);
            stmt.setInt(2, usuarioId);
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Método para agregar un nuevo préstamo
    public boolean agregarPrestamo(Prestamo prestamo) {
        String sql = "INSERT INTO prestamos (usuario_id, herramienta_id, fecha_prestamo, estado) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setInt(1, prestamo.getUsuarioId());
            stmt.setInt(2, prestamo.getHerramientaId());
            stmt.setDate(3, new java.sql.Date(prestamo.getFechaPrestamo().getTime()));
            stmt.setString(4, prestamo.getEstado());
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Método para obtener todos los préstamos
    public List<Prestamo> obtenerPrestamos() {
        List<Prestamo> prestamos = new ArrayList<>();
        String sql = "SELECT * FROM prestamos";
        try (Statement stmt = conexion.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                prestamos.add(new Prestamo(
                        rs.getInt("id"),
                        rs.getInt("usuario_id"),
                        rs.getInt("herramienta_id"),
                        rs.getDate("fecha_prestamo"),
                        rs.getString("estado")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return prestamos;
    }



    // Método para actualizar un préstamo existente
    public boolean actualizarPrestamo(Prestamo prestamo) {
        String sql = "UPDATE prestamos SET usuario_id = ?, herramienta_id = ?, fecha_prestamo = ?, estado = ? WHERE id = ?";
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setInt(1, prestamo.getUsuarioId());
            stmt.setInt(2, prestamo.getHerramientaId());
            stmt.setDate(3, new java.sql.Date(prestamo.getFechaPrestamo().getTime()));
            stmt.setString(4, prestamo.getEstado());
            stmt.setInt(5, prestamo.getId());
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Método para eliminar un préstamo por su ID
    public boolean eliminarPrestamo(int id) {
        String sql = "DELETE FROM prestamos WHERE id = ?";
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Método para obtener préstamos de un usuario específico
    public List<Prestamo> obtenerPrestamosPorUsuario(int usuarioId) {
        List<Prestamo> prestamos = new ArrayList<>();
        String sql = "SELECT * FROM prestamos WHERE usuario_id = ?";
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setInt(1, usuarioId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                prestamos.add(new Prestamo(
                        rs.getInt("id"),
                        rs.getInt("usuario_id"),
                        rs.getInt("herramienta_id"),
                        rs.getDate("fecha_prestamo"),
                        rs.getString("estado")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return prestamos;
    }


    // Método para obtener préstamos según su estado
    public List<Prestamo> obtenerPrestamosPorEstado(String estado) {
        List<Prestamo> prestamos = new ArrayList<>();
        String sql = "SELECT * FROM prestamos WHERE estado = ?";
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setString(1, estado);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                prestamos.add(new Prestamo(
                        rs.getInt("id"),
                        rs.getInt("usuario_id"),
                        rs.getInt("herramienta_id"),
                        rs.getDate("fecha_prestamo"),
                        rs.getString("estado")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return prestamos;
    }

    // **NUEVO MÉTODO**: Registrar un préstamo solicitado por un estudiante
    public boolean registrarPrestamoEstudiante(int herramientaId, int estudianteId) {
        String sql = "INSERT INTO prestamos (herramienta_id, usuario_id, fecha_prestamo, estado) VALUES (?, ?, NOW(), 'pendiente')";
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setInt(1, herramientaId);
            stmt.setInt(2, estudianteId);
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    public Prestamo obtenerPrestamoPorId(int id) {
        String sql = "SELECT * FROM prestamos WHERE id = ?";
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Prestamo(
                        rs.getInt("id"),
                        rs.getInt("usuario_id"),
                        rs.getInt("herramienta_id"),
                        rs.getDate("fecha_prestamo"),
                        rs.getString("estado")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null; // Retorna null si no se encuentra el préstamo
    }

    // **NUEVO MÉTODO**: Obtener préstamos pendientes de un estudiante
    public List<Prestamo> obtenerPrestamosPendientesPorUsuario(int usuarioId) {
        List<Prestamo> prestamos = new ArrayList<>();
        String sql = "SELECT * FROM prestamos WHERE usuario_id = ? AND estado = 'pendiente'";
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setInt(1, usuarioId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                prestamos.add(new Prestamo(
                        rs.getInt("id"),
                        rs.getInt("usuario_id"),
                        rs.getInt("herramienta_id"),
                        rs.getDate("fecha_prestamo"),
                        rs.getString("estado")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return prestamos;
    }

    // **NUEVO MÉTODO**: Actualizar el estado de un préstamo
    public boolean actualizarEstadoPrestamo(int prestamoId, String nuevoEstado) {
        String sql = "UPDATE prestamos SET estado = ? WHERE id = ?";
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setString(1, nuevoEstado);
            stmt.setInt(2, prestamoId);
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
