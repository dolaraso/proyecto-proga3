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
    // Método para agregar una nueva herramienta con ID personalizado
    public boolean agregarHerramienta(Herramienta herramienta) {
        String sql = "INSERT INTO herramientas (id, nombre, descripcion, estado, tipo) VALUES (?, ?, ?, ?, ?)";
        int siguienteId = obtenerSiguienteId(); // Calcula el siguiente ID disponible
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setInt(1, siguienteId); // Asigna manualmente el ID
            stmt.setString(2, herramienta.getNombre());
            stmt.setString(3, herramienta.getDescripcion());
            stmt.setString(4, herramienta.getEstado());
            stmt.setString(5, herramienta.getTipo());
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
                        rs.getString("tipo")
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
                        rs.getString("tipo")
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
                        rs.getString("tipo")
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
                        rs.getString("tipo")
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

    // Método para eliminar una herramienta por su nombre
    public boolean eliminarHerramientaPorNombre(String nombre) {
        String sql = "DELETE FROM herramientas WHERE nombre = ?";
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setString(1, nombre);
            int filasAfectadas = stmt.executeUpdate();
            return filasAfectadas > 0;
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
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Método para verificar si una herramienta está disponible por su ID
    public boolean estaDisponible(int id) {
        String sql = "SELECT estado FROM herramientas WHERE id = ?";
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return "Disponible".equalsIgnoreCase(rs.getString("estado"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    // Método para obtener el siguiente ID disponible
    private int obtenerSiguienteId() {
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


}
