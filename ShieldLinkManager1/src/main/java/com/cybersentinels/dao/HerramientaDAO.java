package com.cybersentinels.dao;

import com.cybersentinels.modelo.Herramienta;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class HerramientaDAO {
    private final Connection conexion;

    public HerramientaDAO() {
        conexion = ConexionDB.conectar();
    }

    // Método para agregar una nueva herramienta
    public boolean agregarHerramienta(Herramienta herramienta) {
        String sql = "INSERT INTO herramientas (nombre, descripcion, estado, tipo) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setString(1, herramienta.getNombre());
            stmt.setString(2, herramienta.getDescripcion());
            stmt.setString(3, herramienta.getEstado());
            stmt.setString(4, herramienta.getTipo());
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Método para obtener todas las herramientas
    public List<Herramienta> obtenerHerramientas() {
        List<Herramienta> herramientas = new ArrayList<>();
        String sql = "SELECT * FROM herramientas";
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

    // Método para eliminar una herramienta por ID
    public boolean eliminarHerramienta(int id) {
        String sql = "DELETE FROM herramientas WHERE id = ?";
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Método para actualizar una herramienta existente
    public boolean actualizarHerramienta(Herramienta herramienta) {
        String sql = "UPDATE herramientas SET nombre = ?, descripcion = ?, estado = ?, tipo = ? WHERE id = ?";
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setString(1, herramienta.getNombre());
            stmt.setString(2, herramienta.getDescripcion());
            stmt.setString(3, herramienta.getEstado());
            stmt.setString(4, herramienta.getTipo());
            stmt.setInt(5, herramienta.getId());
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Método para obtener una herramienta específica por ID
    public Herramienta obtenerHerramientaPorId(int id) {
        String sql = "SELECT * FROM herramientas WHERE id = ?";
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Herramienta(
                        rs.getInt("id"),
                        rs.getString("nombre"),
                        rs.getString("descripcion"),
                        rs.getString("estado"),
                        rs.getString("tipo")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Método para obtener herramientas filtradas por estado
    public List<Herramienta> obtenerHerramientasPorEstado(String estado) {
        List<Herramienta> herramientas = new ArrayList<>();
        String sql = "SELECT * FROM herramientas WHERE estado = ?";
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setString(1, estado);
            ResultSet rs = stmt.executeQuery();
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

    // Método para obtener herramientas disponibles en formato de lista de nombres
    public List<String> obtenerNombresHerramientasDisponibles() {
        List<String> nombresHerramientas = new ArrayList<>();
        String sql = "SELECT nombre FROM herramientas WHERE estado = 'disponible'";
        try (Statement stmt = conexion.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                nombresHerramientas.add(rs.getString("nombre"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return nombresHerramientas;
    }

    // Método para obtener el ID de una herramienta por su nombre
    public int obtenerIdHerramientaPorNombre(String nombreHerramienta) {
        String sql = "SELECT id FROM herramientas WHERE nombre = ?";
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setString(1, nombreHerramienta);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    // Nuevo: Método para verificar si una herramienta está disponible por ID
    public boolean estaDisponible(int id) {
        String sql = "SELECT estado FROM herramientas WHERE id = ?";
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return "disponible".equalsIgnoreCase(rs.getString("estado"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
