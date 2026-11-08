package com.mycompany.examenp2_paii.dao;

import com.mycompany.examenp2_paii.DatabaseConnection;
import com.mycompany.examenp2_paii.model.Pago;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;

public class PagoDAO {

    /**
     * Llama al Procedimiento Almacenado sp_RegistrarPago para registrar el abono.
     * El SP se encarga de validar que no haya sobrepago.
     */
    public boolean registrarPago(Pago p) throws SQLException {
        String sql = "{call sp_RegistrarPago(?, ?, ?)}";
        
        try (Connection conn = DatabaseConnection.getConnection();
             CallableStatement cs = conn.prepareCall(sql)) {
            
            cs.setInt(1, p.getIdOrden());
            cs.setDouble(2, p.getMonto());
            cs.setString(3, p.getMetodoPago());
            
            cs.execute();
            return true;
        } catch (SQLException e) {
            // Si el SP lanza el RAISERROR (sobrepago), se captura aquí
            throw new SQLException("Error al registrar pago: " + e.getMessage());
        }
    }
}