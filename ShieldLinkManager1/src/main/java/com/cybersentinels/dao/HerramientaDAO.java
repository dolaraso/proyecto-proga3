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

    // Método para agregar una nueva herramienta con cantidad
    public boolean agregarHerramienta(Herramienta herramienta) {
        String sql = "INSERT INTO herramientas (id, nombre, descripcion, estado, tipo, cantidad) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setInt(1, herramienta.getId());
            stmt.setString(2, herramienta.getNombre());
            stmt.setString(3, herramienta.getDescripcion());
            stmt.setString(4, herramienta.getEstado());
            stmt.setString(5, herramienta.getTipo());
            stmt.setInt(6, herramienta.getCantidad()); // Manejo de cantidad
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
                        rs.getString("tipo"),
                        rs.getInt("cantidad") // Incluye cantidad
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return herramientas;
    }

    // Método para buscar herramientas por nombre
    public List<Herramienta> buscarHerramientasPorNombre(String nombre) {
        List<Herramienta> herramientas = new ArrayList<>();
        String sql = "SELECT * FROM herramientas WHERE nombre LIKE ?";
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setString(1, "%" + nombre + "%");
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                herramientas.add(new Herramienta(
                        rs.getInt("id"),
                        rs.getString("nombre"),
                        rs.getString("descripcion"),
                        rs.getString("estado"),
                        rs.getString("tipo"),
                        rs.getInt("cantidad") // Incluye cantidad
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return herramientas;
    }

    // Método para buscar una herramienta por nombre exacto
    public Herramienta obtenerHerramientaPorNombre(String nombre) {
        String sql = "SELECT * FROM herramientas WHERE nombre = ?";
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setString(1, nombre);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Herramienta(
                        rs.getInt("id"),
                        rs.getString("nombre"),
                        rs.getString("descripcion"),
                        rs.getString("estado"),
                        rs.getString("tipo"),
                        rs.getInt("cantidad") // Incluye cantidad
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null; // Retorna null si no se encuentra la herramienta
    }

    // Método para buscar herramientas por estado
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
                        rs.getString("tipo"),
                        rs.getInt("cantidad") // Incluye cantidad
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return herramientas;
    }

    // Método para obtener los nombres de herramientas disponibles
    public List<String> obtenerNombresHerramientasDisponibles() {
        List<String> nombres = new ArrayList<>();
        String sql = "SELECT nombre FROM herramientas WHERE estado = 'Disponible'";
        try (Statement stmt = conexion.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                nombres.add(rs.getString("nombre"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return nombres;
    }

    // Método para obtener el ID de una herramienta por su nombre
    public int obtenerIdHerramientaPorNombre(String nombre) {
        String sql = "SELECT id FROM herramientas WHERE nombre = ?";
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setString(1, nombre);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1; // Retorna -1 si no se encuentra la herramienta
    }

    // Método para buscar una herramienta por su ID
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
                        rs.getString("tipo"),
                        rs.getInt("cantidad") // Incluye cantidad
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null; // Retorna null si no encuentra la herramienta
    }

    // Método para eliminar una herramienta por ID
    public boolean eliminarHerramienta(int id) {
        String sql = "DELETE FROM herramientas WHERE id = ?";
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Método para actualizar una herramienta existente
    public boolean actualizarHerramienta(Herramienta herramienta) {
        String sql = "UPDATE herramientas SET nombre = ?, descripcion = ?, estado = ?, tipo = ?, cantidad = ? WHERE id = ?";
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setString(1, herramienta.getNombre());
            stmt.setString(2, herramienta.getDescripcion());
            stmt.setString(3, herramienta.getEstado());
            stmt.setString(4, herramienta.getTipo());
            stmt.setInt(5, herramienta.getCantidad()); // Actualiza cantidad
            stmt.setInt(6, herramienta.getId());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Método para verificar si una herramienta está disponible por cantidad
    public boolean estaDisponible(int id) {
        String sql = "SELECT cantidad FROM herramientas WHERE id = ?";
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("cantidad") > 0; // Disponible si la cantidad es mayor a 0
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Método para actualizar el estado de una herramienta
    public boolean actualizarEstadoHerramienta(int id, String nuevoEstado) {
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

    // Método para eliminar herramienta por nombre
    public boolean eliminarHerramientaPorNombre(String nombre) {
        String sql = "DELETE FROM herramientas WHERE nombre = ?";
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setString(1, nombre);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Método para obtener el siguiente ID disponible
    protected int obtenerSiguienteId() {
        String sql = "SELECT id FROM herramientas ORDER BY id";
        try (Statement stmt = conexion.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            int expectedId = 1;
            while (rs.next()) {
                int currentId = rs.getInt("id");
                if (currentId != expectedId) {
                    return expectedId; // Retorna el primer ID faltante
                }
                expectedId++;
            }
            return expectedId; // Si no hay saltos, retorna el siguiente ID después del mayor
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 1; // Si algo falla, empieza desde 1
    }
    public int getProximoId() {
        return obtenerSiguienteId();
    }
}
