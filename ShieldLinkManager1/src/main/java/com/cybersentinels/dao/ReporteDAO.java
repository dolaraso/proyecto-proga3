package com.cybersentinels.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class ReporteDAO {
    private final Connection conexion;

    // Constructor para inicializar la conexión
    public ReporteDAO() {
        this.conexion = ConexionDB.conectar();
    }

    // Método para generar un reporte general de préstamos
    public String generarReporte() {
        StringBuilder reporte = new StringBuilder();
        String sql = "SELECT prestamos.id AS id_prestamo, usuarios.nombre AS usuario, herramientas.nombre AS herramienta, " +
                "prestamos.fecha_prestamo AS fecha, prestamos.estado AS estado " +
                "FROM prestamos " +
                "JOIN usuarios ON prestamos.usuario_id = usuarios.id " +
                "JOIN herramientas ON prestamos.herramienta_id = herramientas.id";

        try (Statement stmt = conexion.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            reporte.append("Reporte de Préstamos:\n");
            reporte.append(String.format("%-10s %-20s %-20s %-15s %-10s\n", "ID", "Usuario", "Herramienta", "Fecha", "Estado"));
            reporte.append("-------------------------------------------------------------------------------------\n");

            while (rs.next()) {
                reporte.append(String.format("%-10d %-20s %-20s %-15s %-10s\n",
                        rs.getInt("id_prestamo"),
                        rs.getString("usuario"),
                        rs.getString("herramienta"),
                        rs.getDate("fecha").toString(),
                        rs.getString("estado")));
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "Error al generar el reporte: " + e.getMessage();
        }

        return reporte.toString();
    }

    // Método para generar un reporte por estado
    public String generarReportePorEstado(String estadoFiltro) {
        StringBuilder reporte = new StringBuilder();
        String sql = "SELECT prestamos.id AS id_prestamo, usuarios.nombre AS usuario, herramientas.nombre AS herramienta, " +
                "prestamos.fecha_prestamo AS fecha, prestamos.estado AS estado " +
                "FROM prestamos " +
                "JOIN usuarios ON prestamos.usuario_id = usuarios.id " +
                "JOIN herramientas ON prestamos.herramienta_id = herramientas.id " +
                "WHERE prestamos.estado = ?";

        try (var stmt = conexion.prepareStatement(sql)) {
            stmt.setString(1, estadoFiltro);
            ResultSet rs = stmt.executeQuery();

            reporte.append("Reporte de Préstamos (Estado: ").append(estadoFiltro).append("):\n");
            reporte.append(String.format("%-10s %-20s %-20s %-15s %-10s\n", "ID", "Usuario", "Herramienta", "Fecha", "Estado"));
            reporte.append("-------------------------------------------------------------------------------------\n");

            while (rs.next()) {
                reporte.append(String.format("%-10d %-20s %-20s %-15s %-10s\n",
                        rs.getInt("id_prestamo"),
                        rs.getString("usuario"),
                        rs.getString("herramienta"),
                        rs.getDate("fecha").toString(),
                        rs.getString("estado")));
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "Error al generar el reporte: " + e.getMessage();
        }

        return reporte.toString();
    }

    // Método para generar un reporte resumido (conteo de préstamos por estado)
    public String generarReporteResumido() {
        StringBuilder reporte = new StringBuilder();
        String sql = "SELECT estado, COUNT(*) AS total FROM prestamos GROUP BY estado";

        try (Statement stmt = conexion.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            reporte.append("Resumen de Préstamos por Estado:\n");
            reporte.append(String.format("%-15s %-10s\n", "Estado", "Total"));
            reporte.append("----------------------------\n");

            while (rs.next()) {
                reporte.append(String.format("%-15s %-10d\n",
                        rs.getString("estado"),
                        rs.getInt("total")));
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "Error al generar el reporte resumido: " + e.getMessage();
        }

        return reporte.toString();
    }
}
