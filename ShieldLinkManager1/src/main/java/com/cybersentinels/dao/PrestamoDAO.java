package com.cybersentinels.dao;

import com.cybersentinels.modelo.Prestamo;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class PrestamoDAO {
    private final Connection conexion;

    public PrestamoDAO() {
        conexion = ConexionDB.conectar();
    }

    // Método para agregar un nuevo préstamo
    public boolean agregarPrestamo(Prestamo prestamo) {
        String obtenerMaxIdSQL = "SELECT MAX(id) AS max_id FROM prestamos";
        String insertarPrestamoSQL = "INSERT INTO prestamos (id, herramienta_id, usuario_id, fecha_prestamo, fecha_devolucion, estado, descripcion_uso, cantidad_solicitada) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement obtenerMaxIdStmt = conexion.prepareStatement(obtenerMaxIdSQL);
             PreparedStatement insertarStmt = conexion.prepareStatement(insertarPrestamoSQL)) {

            // Obtener el ID máximo
            ResultSet rs = obtenerMaxIdStmt.executeQuery();
            int nuevoId = 1; // Por defecto, empieza en 1
            if (rs.next()) {
                nuevoId = rs.getInt("max_id") + 1; // Incrementar el ID máximo
            }

            // Insertar el nuevo préstamo con el ID calculado
            insertarStmt.setInt(1, nuevoId);
            insertarStmt.setInt(2, prestamo.getHerramientaId());
            insertarStmt.setInt(3, prestamo.getUsuarioId());
            insertarStmt.setDate(4, Date.valueOf(prestamo.getFechaPrestamo()));
            insertarStmt.setDate(5, Date.valueOf(prestamo.getFechaDevolucion()));
            insertarStmt.setString(6, prestamo.getEstado());
            insertarStmt.setString(7, prestamo.getDescripcionUso());
            insertarStmt.setInt(8, prestamo.getCantidadSolicitada());

            return insertarStmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


    // Método para solicitar un préstamo con restricciones por rol
    public boolean solicitarPrestamoConRestriccion(int herramientaId, int usuarioId, String rolUsuario, String descripcionUso, LocalDate fechaInicio, LocalDate fechaDevolucion, int cantidadSolicitada) {
        // Determinar límite de herramientas basado en el rol
        int limitePrestamos = rolUsuario.equalsIgnoreCase("Estudiante") ? 3 : 30;

        // Validar cantidad actual de herramientas aprobadas
        int herramientasAprobadas = obtenerCantidadHerramientasAprobadas(usuarioId);

        if (herramientasAprobadas + cantidadSolicitada > limitePrestamos) {
            return false; // Excede el límite permitido
        }

        // SQL para realizar la solicitud
        String verificarCantidadSQL = "SELECT cantidad FROM herramientas WHERE id = ?";
        String actualizarCantidadSQL = "UPDATE herramientas SET cantidad = cantidad - ? WHERE id = ?";
        String insertarPrestamoSQL = "INSERT INTO prestamos (herramienta_id, usuario_id, fecha_prestamo, fecha_devolucion, estado, descripcion_uso, cantidad_solicitada) VALUES (?, ?, ?, ?, 'Pendiente', ?, ?)";

        try (PreparedStatement verificarStmt = conexion.prepareStatement(verificarCantidadSQL);
             PreparedStatement actualizarStmt = conexion.prepareStatement(actualizarCantidadSQL);
             PreparedStatement insertarStmt = conexion.prepareStatement(insertarPrestamoSQL)) {

            // Verificar disponibilidad de cantidad
            verificarStmt.setInt(1, herramientaId);
            ResultSet rs = verificarStmt.executeQuery();
            if (rs.next()) {
                int cantidadDisponible = rs.getInt("cantidad");
                if (cantidadSolicitada > cantidadDisponible) {
                    return false; // No hay suficientes herramientas disponibles
                }
            } else {
                return false; // Herramienta no encontrada
            }

            // Actualizar la cantidad en herramientas
            actualizarStmt.setInt(1, cantidadSolicitada);
            actualizarStmt.setInt(2, herramientaId);
            int filasActualizadas = actualizarStmt.executeUpdate();
            if (filasActualizadas == 0) {
                return false; // Fallo al actualizar la cantidad
            }

            // Registrar el préstamo
            insertarStmt.setInt(1, herramientaId);
            insertarStmt.setInt(2, usuarioId);
            insertarStmt.setDate(3, Date.valueOf(fechaInicio));
            insertarStmt.setDate(4, Date.valueOf(fechaDevolucion));
            insertarStmt.setString(5, descripcionUso);
            insertarStmt.setInt(6, cantidadSolicitada);
            insertarStmt.executeUpdate();

            return true; // Préstamo realizado con éxito
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Método para obtener la cantidad de herramientas aprobadas de un usuario
    public int obtenerCantidadHerramientasAprobadas(int usuarioId) {
        String sql = "SELECT SUM(cantidad_solicitada) AS total FROM prestamos WHERE usuario_id = ? AND (estado = 'Aprobado' OR estado = 'Pendiente')";
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setInt(1, usuarioId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("total");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0; // Si no hay registros, retorna 0.
    }
    public int obtenerCantidadHerramientasActivas(int usuarioId) {
        String sql = "SELECT SUM(cantidad_solicitada) AS total " +
                "FROM prestamos " +
                "WHERE usuario_id = ? AND estado = 'Activo'";
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setInt(1, usuarioId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("total");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0; // Si no hay registros, retorna 0.
    }
    public int obtenerCantidadPrestamosActivosYAprobados(int usuarioId) {
        String sql = "SELECT SUM(cantidad_solicitada) AS total " +
                "FROM prestamos " +
                "WHERE usuario_id = ? AND (estado = 'Activo' OR estado = 'Pendiente')";
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setInt(1, usuarioId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("total");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0; // Si no hay registros, retorna 0.
    }
    public boolean solicitarPrestamo(int herramientaId, int usuarioId, String descripcionUso, LocalDate fechaInicio, LocalDate fechaDevolucion, int cantidadSolicitada) {
        String verificarCantidadSQL = "SELECT cantidad FROM herramientas WHERE id = ?";
        String actualizarCantidadSQL = "UPDATE herramientas SET cantidad = cantidad - ? WHERE id = ?";
        String insertarPrestamoSQL = "INSERT INTO prestamos (herramienta_id, usuario_id, fecha_prestamo, fecha_devolucion, estado, descripcion_uso, cantidad_solicitada) VALUES (?, ?, ?, ?, 'Pendiente', ?, ?)";

        try (PreparedStatement verificarStmt = conexion.prepareStatement(verificarCantidadSQL);
             PreparedStatement actualizarStmt = conexion.prepareStatement(actualizarCantidadSQL);
             PreparedStatement insertarStmt = conexion.prepareStatement(insertarPrestamoSQL)) {

            // Verificar disponibilidad de cantidad
            verificarStmt.setInt(1, herramientaId);
            ResultSet rs = verificarStmt.executeQuery();
            if (rs.next()) {
                int cantidadDisponible = rs.getInt("cantidad");
                if (cantidadSolicitada > cantidadDisponible) {
                    return false; // No hay suficientes herramientas disponibles
                }
            } else {
                return false; // Herramienta no encontrada
            }

            // Actualizar la cantidad en herramientas
            actualizarStmt.setInt(1, cantidadSolicitada);
            actualizarStmt.setInt(2, herramientaId);
            int filasActualizadas = actualizarStmt.executeUpdate();
            if (filasActualizadas == 0) {
                return false; // Fallo al actualizar la cantidad
            }

            // Registrar el préstamo
            insertarStmt.setInt(1, herramientaId);
            insertarStmt.setInt(2, usuarioId);
            insertarStmt.setDate(3, Date.valueOf(fechaInicio));
            insertarStmt.setDate(4, Date.valueOf(fechaDevolucion));
            insertarStmt.setString(5, descripcionUso);
            insertarStmt.setInt(6, cantidadSolicitada);
            insertarStmt.executeUpdate();

            return true; // Préstamo realizado con éxito
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


    // Método para obtener préstamos con detalles adicionales
    public List<Prestamo> obtenerPrestamosConDetalle() {
        List<Prestamo> prestamos = new ArrayList<>();
        String sql = "SELECT p.id, p.usuario_id, p.herramienta_id, p.fecha_prestamo, p.fecha_devolucion, p.estado, p.descripcion_uso, p.cantidad_solicitada, h.nombre AS herramienta_nombre, u.rol AS usuario_rol " +
                "FROM prestamos p " +
                "JOIN herramientas h ON p.herramienta_id = h.id " +
                "JOIN usuarios u ON p.usuario_id = u.id";

        try (Statement stmt = conexion.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Prestamo prestamo = new Prestamo(
                        rs.getInt("id"),
                        rs.getInt("usuario_id"),
                        rs.getInt("herramienta_id"),
                        rs.getDate("fecha_prestamo").toLocalDate(),
                        rs.getDate("fecha_devolucion").toLocalDate(),
                        rs.getString("estado"),
                        rs.getString("descripcion_uso"),
                        rs.getInt("cantidad_solicitada"),
                        rs.getString("herramienta_nombre"),
                        rs.getString("usuario_rol")
                );
                prestamos.add(prestamo);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return prestamos;
    }

    // Método para actualizar el estado de un préstamo
    public boolean actualizarEstadoPrestamo(int prestamoId, String nuevoEstado) {
        String sql = "UPDATE prestamos SET estado = ? WHERE id = ?";
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setString(1, nuevoEstado);
            stmt.setInt(2, prestamoId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Método para completar un préstamo
    public boolean completarPrestamo(int prestamoId) {
        String sql = "UPDATE prestamos SET estado = 'Completado' WHERE id = ?";
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setInt(1, prestamoId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    public List<Prestamo> obtenerPrestamosPorUsuario(int usuarioId) {
        List<Prestamo> prestamos = new ArrayList<>();
        String sql = "SELECT * FROM prestamos WHERE usuario_id = ?";
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setInt(1, usuarioId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Prestamo prestamo = new Prestamo(
                        rs.getInt("id"),
                        rs.getInt("usuario_id"),
                        rs.getInt("herramienta_id"),
                        rs.getDate("fecha_prestamo").toLocalDate(),
                        rs.getDate("fecha_devolucion") != null ? rs.getDate("fecha_devolucion").toLocalDate() : null,
                        rs.getString("estado"),
                        rs.getString("descripcion_uso"),
                        rs.getInt("cantidad_solicitada")
                );
                prestamos.add(prestamo);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return prestamos;
    }
    public List<Prestamo> obtenerPrestamosPorEstado(String estado) {
        List<Prestamo> prestamos = new ArrayList<>();
        String sql = "SELECT * FROM prestamos WHERE estado = ?";

        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setString(1, estado);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Prestamo prestamo = new Prestamo(
                        rs.getInt("id"),
                        rs.getInt("usuario_id"),
                        rs.getInt("herramienta_id"),
                        rs.getDate("fecha_prestamo").toLocalDate(),
                        rs.getDate("fecha_devolucion") != null ? rs.getDate("fecha_devolucion").toLocalDate() : null,
                        rs.getString("estado"),
                        rs.getString("descripcion_uso"),
                        rs.getInt("cantidad_solicitada")
                );
                prestamos.add(prestamo);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return prestamos;
    }
    public List<Prestamo> obtenerPrestamos() {
        List<Prestamo> prestamos = new ArrayList<>();
        String sql = "SELECT * FROM prestamos";

        try (Statement stmt = conexion.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Prestamo prestamo = new Prestamo(
                        rs.getInt("id"),
                        rs.getInt("usuario_id"),
                        rs.getInt("herramienta_id"),
                        rs.getDate("fecha_prestamo").toLocalDate(),
                        rs.getDate("fecha_devolucion") != null ? rs.getDate("fecha_devolucion").toLocalDate() : null,
                        rs.getString("estado"),
                        rs.getString("descripcion_uso"),
                        rs.getInt("cantidad_solicitada")
                );
                prestamos.add(prestamo);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return prestamos;
    }
    public boolean actualizarPrestamo(Prestamo prestamo) {
        String sql = "UPDATE prestamos SET herramienta_id = ?, usuario_id = ?, fecha_prestamo = ?, fecha_devolucion = ?, estado = ?, descripcion_uso = ?, cantidad_solicitada = ? WHERE id = ?";
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setInt(1, prestamo.getHerramientaId());
            stmt.setInt(2, prestamo.getUsuarioId());
            stmt.setDate(3, Date.valueOf(prestamo.getFechaPrestamo()));
            stmt.setDate(4, Date.valueOf(prestamo.getFechaDevolucion()));
            stmt.setString(5, prestamo.getEstado());
            stmt.setString(6, prestamo.getDescripcionUso());
            stmt.setInt(7, prestamo.getCantidadSolicitada());
            stmt.setInt(8, prestamo.getId());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    public boolean eliminarPrestamo(int id) {
        String sql = "DELETE FROM prestamos WHERE id = ?";
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    public int obtenerCantidadPrestada(int prestamoId) {
        String sql = "SELECT cantidad_solicitada FROM prestamos WHERE id = ?";
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setInt(1, prestamoId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("cantidad_solicitada");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0; // Retorna 0 si no se encuentra el préstamo
    }
    public boolean incrementarCantidadHerramienta(int herramientaId, int cantidad) {
        String sql = "UPDATE herramientas SET cantidad = cantidad + ? WHERE id = ?";
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setInt(1, cantidad);
            stmt.setInt(2, herramientaId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

}
