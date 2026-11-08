package com.mycompany.examenp2_paii.dao;

import com.mycompany.examenp2_paii.DatabaseConnection;
import com.mycompany.examenp2_paii.model.Vehiculo;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class VehiculoDAO {

    public boolean insertar(Vehiculo v) {
        String sql = "INSERT INTO Vehiculos (IdCliente, Placa, VIN, Marca, Modelo, Anio, Color, Kilometraje, Combustible) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, v.getIdCliente());
            ps.setString(2, v.getPlaca());
            ps.setString(3, v.getVin());
            ps.setString(4, v.getMarca());
            ps.setString(5, v.getModelo());
            ps.setInt(6, v.getAnio());
            ps.setString(7, v.getColor());
            ps.setInt(8, v.getKilometraje());
            ps.setString(9, v.getCombustible());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error insertar vehiculo: " + e.getMessage());
            return false;
        }
    }

    public List<Vehiculo> listarPorCliente(int idCliente) {
        List<Vehiculo> lista = new ArrayList<>();
        String sql = "SELECT * FROM Vehiculos WHERE IdCliente = ? AND Estado = 1";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idCliente);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Vehiculo v = new Vehiculo();
                    v.setIdVehiculo(rs.getInt("IdVehiculo"));
                    v.setIdCliente(rs.getInt("IdCliente"));
                    v.setPlaca(rs.getString("Placa"));
                    v.setVin(rs.getString("VIN"));
                    v.setMarca(rs.getString("Marca"));
                    v.setModelo(rs.getString("Modelo"));
                    v.setAnio(rs.getInt("Anio"));
                    v.setColor(rs.getString("Color"));
                    v.setKilometraje(rs.getInt("Kilometraje"));
                    v.setCombustible(rs.getString("Combustible"));
                    lista.add(v);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error listar vehiculos: " + e.getMessage());
        }
        return lista;
    }
}