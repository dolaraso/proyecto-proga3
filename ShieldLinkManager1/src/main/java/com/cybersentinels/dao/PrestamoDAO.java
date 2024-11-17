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
}
