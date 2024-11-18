package com.cybersentinels.dao;

import com.cybersentinels.modelo.Prestamo;

import java.sql.*;
import java.text.SimpleDateFormat;
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

    public boolean agregarPrestamo(Prestamo prestamo) {
        String sql = "INSERT INTO prestamos (usuario_id, herramienta_id, fecha_prestamo, estado) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setInt(1, prestamo.getUsuarioId());
            stmt.setInt(2, prestamo.getHerramientaId());
            stmt.setDate(3, Date.valueOf(prestamo.getFechaPrestamo())); // Convierte String a SQL Date
            stmt.setString(4, prestamo.getEstado());
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Prestamo> obtenerPrestamos() {
        List<Prestamo> prestamos = new ArrayList<>();
        String sql = "SELECT * FROM prestamos";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); // Formato de fecha como String
        try (Statement stmt = conexion.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                prestamos.add(new Prestamo(
                        rs.getInt("id"),
                        rs.getInt("usuario_id"),
                        rs.getInt("herramienta_id"),
                        sdf.format(rs.getDate("fecha_prestamo")), // Convierte SQL Date a String
                        rs.getString("estado")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return prestamos;
    }

    public boolean actualizarPrestamo(Prestamo prestamo) {
        String sql = "UPDATE prestamos SET usuario_id = ?, herramienta_id = ?, fecha_prestamo = ?, estado = ? WHERE id = ?";
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setInt(1, prestamo.getUsuarioId());
            stmt.setInt(2, prestamo.getHerramientaId());
            stmt.setDate(3, Date.valueOf(prestamo.getFechaPrestamo())); // Convierte String a SQL Date
            stmt.setString(4, prestamo.getEstado());
            stmt.setInt(5, prestamo.getId());
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

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

    public List<Prestamo> obtenerPrestamosPorUsuario(int usuarioId) {
        List<Prestamo> prestamos = new ArrayList<>();
        String sql = "SELECT * FROM prestamos WHERE usuario_id = ?";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setInt(1, usuarioId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                prestamos.add(new Prestamo(
                        rs.getInt("id"),
                        rs.getInt("usuario_id"),
                        rs.getInt("herramienta_id"),
                        sdf.format(rs.getDate("fecha_prestamo")), // Convierte SQL Date a String
                        rs.getString("estado")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return prestamos;
    }

    public List<Prestamo> obtenerPrestamosPorEstado(String estado) {
        List<Prestamo> prestamos = new ArrayList<>();
        String sql = "SELECT * FROM prestamos WHERE estado = ?";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setString(1, estado);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                prestamos.add(new Prestamo(
                        rs.getInt("id"),
                        rs.getInt("usuario_id"),
                        rs.getInt("herramienta_id"),
                        sdf.format(rs.getDate("fecha_prestamo")), // Convierte SQL Date a String
                        rs.getString("estado")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return prestamos;
    }

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
