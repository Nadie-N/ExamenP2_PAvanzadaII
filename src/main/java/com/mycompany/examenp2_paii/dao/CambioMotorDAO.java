package com.mycompany.examenp2_paii.dao;

import com.mycompany.examenp2_paii.DatabaseConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class CambioMotorDAO {

    /**
     * Registra el cambio de motor de forma TRANSACCIONAL.
     * Si algo falla, se revierte todo (Rollback).
     */
    public boolean registrarCambioMotor(int idOrden, int idMotorNuevo, String serieAnterior, 
                                        String serieNueva, double manoObra, boolean autorizado, 
                                        String destinoRetirado, int garantiaMeses) throws SQLException {
        Connection conn = null;
        try {
            conn = DatabaseConnection.getConnection();
            // 1. INICIAMOS LA TRANSACCIÓN
            conn.setAutoCommit(false); 

            // PASO A: Insertar en la tabla CambiosMotor
            String sql1 = "INSERT INTO CambiosMotor (IdOrden, IdMotor, SerieMotorAnterior, DestinoMotorRetirado, " +
                          "SerieMotorNuevo, ManoObra, AutorizacionCliente, FechaCambio, GarantiaMeses) " +
                          "VALUES (?, ?, ?, ?, ?, ?, ?, GETDATE(), ?)";
            try (PreparedStatement ps = conn.prepareStatement(sql1)) {
                ps.setInt(1, idOrden);
                ps.setInt(2, idMotorNuevo);
                ps.setString(3, serieAnterior);
                ps.setString(4, destinoRetirado);
                ps.setString(5, serieNueva);
                ps.setDouble(6, manoObra);
                ps.setBoolean(7, autorizado);
                ps.setInt(8, garantiaMeses);
                ps.executeUpdate();
            }

            // PASO B: Actualizar el estado del motor nuevo a 'Instalado' en inventario
            String sql2 = "UPDATE Motores SET Estado = 'Instalado' WHERE IdMotor = ?";
            try (PreparedStatement ps = conn.prepareStatement(sql2)) {
                ps.setInt(1, idMotorNuevo);
                ps.executeUpdate();
            }

            // PASO C: Registrar Control de Calidad Aprobado (para que la orden se pueda finalizar)
            String sql3 = "INSERT INTO ControlesCalidad (IdOrden, Verificacion, Aprobado, Observaciones) " +
                          "VALUES (?, 'Pruebas post-instalación de motor', 1, 'Motor instalado y probado correctamente')";
            try (PreparedStatement ps = conn.prepareStatement(sql3)) {
                ps.setInt(1, idOrden);
                ps.executeUpdate();
            }

            // SI TODO SALIÓ BIEN, GUARDAMOS LOS CAMBIOS
            conn.commit(); 
            return true;

        } catch (SQLException e) {
            // SI HAY UN ERROR, REVERTIMOS TODO
            if (conn != null) {
                conn.rollback(); 
            }
            throw e; // Lanzamos el error para que la interfaz muestre el mensaje
        } finally {
            // Cerramos la conexión
            if (conn != null) {
                conn.setAutoCommit(true);
                conn.close();
            }
        }
    }
}