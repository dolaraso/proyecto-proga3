package com.cybersentinels.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class ReporteDAO {
    private Connection conexion;

    public ReporteDAO() {
        conexion = ConexionDB.conectar();
    }

    public int contarHerramientasDisponibles() {
        String sql = "SELECT COUNT(*) AS total FROM herramientas WHERE estado = 'Disponible'";
        try (Statement stmt = conexion.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            if (rs.next()) {
                return rs.getInt("total");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public int contarPrestamosActivos() {
        String sql = "SELECT COUNT(*) AS total FROM prestamos WHERE estado = 'Activo'";
        try (Statement stmt = conexion.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            if (rs.next()) {
                return rs.getInt("total");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public int contarHerramientasEnMantenimiento() {
        String sql = "SELECT COUNT(*) AS total FROM herramientas WHERE estado = 'Mantenimiento'";
        try (Statement stmt = conexion.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            if (rs.next()) {
                return rs.getInt("total");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }
}
