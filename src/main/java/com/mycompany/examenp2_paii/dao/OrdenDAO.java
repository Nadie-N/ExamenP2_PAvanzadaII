package com.mycompany.examenp2_paii.dao;

import com.mycompany.examenp2_paii.DatabaseConnection;
import com.mycompany.examenp2_paii.model.OrdenTrabajo;
import java.sql.*;

public class OrdenDAO {

    /**
     * Inserta una orden y retorna el ID generado.
     * Si hay error, lanza excepción para que la UI muestre el mensaje.
     */
    public int insertarOrden(OrdenTrabajo o) throws SQLException {
        String sql = "INSERT INTO OrdenesTrabajo (IdCliente, IdVehiculo, IdMecanicoResponsable, Kilometraje, Sintomas, Estado) " +
                     "VALUES (?, ?, ?, ?, ?, 'Recibida')";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            ps.setInt(1, o.getIdCliente());
            ps.setInt(2, o.getIdVehiculo());
            
            if (o.getIdMecanicoResponsable() != null) {
                ps.setInt(3, o.getIdMecanicoResponsable());
            } else {
                ps.setNull(3, Types.INTEGER);
            }
            
            ps.setInt(4, o.getKilometraje());
            ps.setString(5, o.getSintomas());
            
            ps.executeUpdate();
            
            // Obtener el ID autogenerado (Identity)
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        }
        throw new SQLException("No se pudo obtener el ID de la orden.");
    }
}