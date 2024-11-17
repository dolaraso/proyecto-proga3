package com.cybersentinels.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CategoriaHerramientaDAO {
    private Connection conexion;

    public CategoriaHerramientaDAO() {
        conexion = ConexionDB.conectar();
    }

    public boolean agregarCategoria(String tipo, String descripcion) {
        String sql = "INSERT INTO categorias (tipo, descripcion) VALUES (?, ?)";
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setString(1, tipo);
            stmt.setString(2, descripcion);
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<String> obtenerCategorias() {
        List<String> categorias = new ArrayList<>();
        String sql = "SELECT tipo FROM categorias";
        try (Statement stmt = conexion.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                categorias.add(rs.getString("tipo"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return categorias;
    }

    public String obtenerDescripcionCategoria(String tipo) {
        String sql = "SELECT descripcion FROM categorias WHERE tipo = ?";
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setString(1, tipo);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getString("descripcion");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean eliminarCategoria(String tipo) {
        String sql = "DELETE FROM categorias WHERE tipo = ?";
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setString(1, tipo);
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
