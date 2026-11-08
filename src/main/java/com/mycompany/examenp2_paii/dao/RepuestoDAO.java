package com.mycompany.examenp2_paii.dao;

import com.mycompany.examenp2_paii.DatabaseConnection;
import com.mycompany.examenp2_paii.model.Repuesto;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RepuestoDAO {

    // 1. Método para registrar un repuesto en el inventario
    public boolean insertarRepuesto(Repuesto r) throws SQLException {
        String sql = "INSERT INTO Repuestos (Codigo, Nombre, Costo, PrecioVenta, Stock, StockMinimo) VALUES (?, ?, ?, ?, ?, ?)";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, r.getCodigo());
            ps.setString(2, r.getNombre());
            ps.setDouble(3, r.getCosto());
            ps.setDouble(4, r.getPrecioVenta());
            ps.setInt(5, r.getStock());
            ps.setInt(6, r.getStockMinimo());
            
            return ps.executeUpdate() > 0;
        }
    }

    // 2. Método TRANSACCIONAL: Registrar consumo y descontar stock
    public boolean consumirRepuesto(int idOrden, int idRepuesto, int cantidad) throws SQLException {
        Connection conn = null;
        try {
            conn = DatabaseConnection.getConnection();
            conn.setAutoCommit(false); // Iniciar transacción

            // Paso A: Verificar si hay stock suficiente
            String sqlCheck = "SELECT Stock, PrecioVenta FROM Repuestos WHERE IdRepuesto = ?";
            double precioUnitario = 0;
            int stockActual = 0;
            
            try (PreparedStatement psCheck = conn.prepareStatement(sqlCheck)) {
                psCheck.setInt(1, idRepuesto);
                try (ResultSet rs = psCheck.executeQuery()) {
                    if (rs.next()) {
                        stockActual = rs.getInt("Stock");
                        precioUnitario = rs.getDouble("PrecioVenta");
                    } else {
                        throw new SQLException("El repuesto con ese ID no existe.");
                    }
                }
            }

            if (stockActual < cantidad) {
                throw new SQLException("Stock insuficiente. Solo hay " + stockActual + " unidades disponibles.");
            }

            // Paso B: Insertar el consumo en la tabla ConsumosRepuestos
            String sqlConsumo = "INSERT INTO ConsumosRepuestos (IdOrden, IdRepuesto, Cantidad, PrecioUnitario) VALUES (?, ?, ?, ?)";
            try (PreparedStatement ps = conn.prepareStatement(sqlConsumo)) {
                ps.setInt(1, idOrden);
                ps.setInt(2, idRepuesto);
                ps.setInt(3, cantidad);
                ps.setDouble(4, precioUnitario);
                ps.executeUpdate();
            }

            // Paso C: Descontar el stock en la tabla Repuestos
            String sqlStock = "UPDATE Repuestos SET Stock = Stock - ? WHERE IdRepuesto = ?";
            try (PreparedStatement ps = conn.prepareStatement(sqlStock)) {
                ps.setInt(1, cantidad);
                ps.setInt(2, idRepuesto);
                ps.executeUpdate();
            }

            conn.commit(); // Confirmar transacción (todo salió bien)
            return true;

        } catch (SQLException e) {
            if (conn != null) conn.rollback(); // Revertir si algo falla
            throw e;
        } finally {
            if (conn != null) {
                conn.setAutoCommit(true);
                conn.close();
            }
        }
    }
}