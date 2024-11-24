package com.cybersentinels.dao;

import com.cybersentinels.modelo.Herramienta;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MantenimientoDAO {
    private final Connection conexion;

    public MantenimientoDAO() {
        conexion = ConexionDB.conectar();
    }

    // Método para agregar una herramienta a mantenimiento
    public boolean agregarHerramientaAMantenimiento(int herramientaId, String motivo) {
        String sql = "INSERT INTO mantenimiento (herramienta_id, motivo, fecha_inicio, estado) VALUES (?, ?, NOW(), 'En Mantenimiento')";
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setInt(1, herramientaId);
            stmt.setString(2, motivo);
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Método para finalizar el mantenimiento de una herramienta
    public boolean finalizarMantenimiento(int herramientaId) {
        String sql = "UPDATE mantenimiento SET fecha_fin = NOW(), estado = 'Completado' WHERE herramienta_id = ? AND estado = 'En Mantenimiento'";
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setInt(1, herramientaId);
            stmt.executeUpdate();

            // Actualiza el estado de la herramienta a 'Disponible'
            actualizarEstadoHerramienta(herramientaId, "Disponible");

            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Método para obtener herramientas en mantenimiento
    public List<Herramienta> obtenerHerramientasEnMantenimiento() {
        List<Herramienta> herramientas = new ArrayList<>();
        String sql = "SELECT h.id, h.nombre, h.descripcion, h.estado, h.tipo, h.cantidad " +
                "FROM herramientas h " +
                "INNER JOIN mantenimiento m ON h.id = m.herramienta_id " +
                "WHERE m.estado = 'En Mantenimiento'";
        try (Statement stmt = conexion.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                herramientas.add(new Herramienta(
                        rs.getInt("id"),
                        rs.getString("nombre"),
                        rs.getString("descripcion"),
                        rs.getString("estado"),
                        rs.getString("tipo"),
                        rs.getInt("cantidad")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return herramientas;
    }

    // Método para obtener historial de mantenimiento
    public List<String> obtenerHistorialMantenimiento(int herramientaId) {
        List<String> historial = new ArrayList<>();
        String sql = "SELECT motivo, fecha_inicio, fecha_fin, estado FROM mantenimiento WHERE herramienta_id = ?";
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setInt(1, herramientaId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                String entrada = String.format("Motivo: %s | Inicio: %s | Fin: %s | Estado: %s",
                        rs.getString("motivo"),
                        rs.getTimestamp("fecha_inicio"),
                        rs.getTimestamp("fecha_fin"),
                        rs.getString("estado"));
                historial.add(entrada);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return historial;
    }

    // Método para actualizar el estado de una herramienta (reutilizado de HerramientaDAO)
    private boolean actualizarEstadoHerramienta(int id, String nuevoEstado) {
        String sql = "UPDATE herramientas SET estado = ? WHERE id = ?";
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setString(1, nuevoEstado);
            stmt.setInt(2, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Método para verificar si una herramienta está en mantenimiento
    public boolean estaEnMantenimiento(int herramientaId) {
        String sql = "SELECT COUNT(*) AS total FROM mantenimiento WHERE herramienta_id = ? AND estado = 'En Mantenimiento'";
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setInt(1, herramientaId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("total") > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Método para eliminar un registro de mantenimiento (si es necesario)
    public boolean eliminarRegistroMantenimiento(int mantenimientoId) {
        String sql = "DELETE FROM mantenimiento WHERE id = ?";
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setInt(1, mantenimientoId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Método para obtener el estado actual de una herramienta en mantenimiento
    public String obtenerEstadoMantenimiento(int herramientaId) {
        String sql = "SELECT estado FROM mantenimiento WHERE herramienta_id = ? AND estado = 'En Mantenimiento'";
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setInt(1, herramientaId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getString("estado");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Método para obtener la información completa del mantenimiento activo
    public List<String> obtenerInformacionMantenimientoActivo() {
        List<String> informacion = new ArrayList<>();
        String sql = "SELECT h.nombre, m.motivo, m.fecha_inicio " +
                "FROM herramientas h " +
                "INNER JOIN mantenimiento m ON h.id = m.herramienta_id " +
                "WHERE m.estado = 'En Mantenimiento'";
        try (Statement stmt = conexion.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                String detalle = String.format("Herramienta: %s | Motivo: %s | Inicio: %s",
                        rs.getString("nombre"),
                        rs.getString("motivo"),
                        rs.getTimestamp("fecha_inicio"));
                informacion.add(detalle);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return informacion;
    }
}
