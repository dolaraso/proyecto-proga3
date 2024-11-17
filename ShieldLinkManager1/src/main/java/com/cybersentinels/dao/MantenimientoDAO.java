package com.cybersentinels.dao;

import com.cybersentinels.modelo.Herramienta;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MantenimientoDAO {
    private Connection conexion;

    public MantenimientoDAO() {
        conexion = ConexionDB.conectar();
    }

    public boolean programarMantenimiento(int herramientaId, String fechaProgramada) {
        String sql = "INSERT INTO mantenimientos (herramienta_id, fecha_programada) VALUES (?, ?)";
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setInt(1, herramientaId);
            stmt.setDate(2, Date.valueOf(fechaProgramada));
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Herramienta> obtenerHerramientasEnMantenimiento() {
        List<Herramienta> herramientas = new ArrayList<>();
        String sql = "SELECT h.* FROM herramientas h INNER JOIN mantenimientos m ON h.id = m.herramienta_id WHERE h.estado = 'Mantenimiento'";
        try (Statement stmt = conexion.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                herramientas.add(new Herramienta(
                        rs.getInt("id"),
                        rs.getString("nombre"),
                        rs.getString("descripcion"),
                        rs.getString("estado"),
                        rs.getString("tipo")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return herramientas;
    }

    public boolean finalizarMantenimiento(int herramientaId) {
        String sql = "DELETE FROM mantenimientos WHERE herramienta_id = ?";
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setInt(1, herramientaId);
            stmt.executeUpdate();

            String updateHerramienta = "UPDATE herramientas SET estado = 'Disponible' WHERE id = ?";
            try (PreparedStatement stmtHerramienta = conexion.prepareStatement(updateHerramienta)) {
                stmtHerramienta.setInt(1, herramientaId);
                stmtHerramienta.executeUpdate();
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
