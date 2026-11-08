package com.mycompany.examenp2_paii.dao;

import com.mycompany.examenp2_paii.DatabaseConnection;
import com.mycompany.examenp2_paii.model.Cliente;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClienteDAO {

    // CREATE
    public boolean insertar(Cliente c) {
        String sql = "INSERT INTO Clientes (Nombre, DNI, Telefono, Correo, Estado) VALUES (?, ?, ?, ?, 1)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, c.getNombre());
            ps.setString(2, c.getDni());
            ps.setString(3, c.getTelefono());
            ps.setString(4, c.getCorreo());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al insertar cliente: " + e.getMessage());
            return false;
        }
    }

    // READ (Listar todos los activos)
    public List<Cliente> listarActivos() {
        List<Cliente> lista = new ArrayList<>();
        String sql = "SELECT IdCliente, Nombre, DNI, Telefono, Correo, Estado FROM Clientes WHERE Estado = 1 ORDER BY Nombre";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                lista.add(new Cliente(
                    rs.getInt("IdCliente"),
                    rs.getString("Nombre"),
                    rs.getString("DNI"),
                    rs.getString("Telefono"),
                    rs.getString("Correo"),
                    rs.getBoolean("Estado")
                ));
            }
        } catch (SQLException e) {
            System.err.println("Error al listar clientes: " + e.getMessage());
        }
        return lista;
    }

    // UPDATE
    public boolean actualizar(Cliente c) {
        String sql = "UPDATE Clientes SET Nombre=?, DNI=?, Telefono=?, Correo=? WHERE IdCliente=?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, c.getNombre());
            ps.setString(2, c.getDni());
            ps.setString(3, c.getTelefono());
            ps.setString(4, c.getCorreo());
            ps.setInt(5, c.getIdCliente());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al actualizar cliente: " + e.getMessage());
            return false;
        }
    }

    // DELETE LÓGICO (Desactivar)
    public boolean desactivar(int idCliente) {
        String sql = "UPDATE Clientes SET Estado = 0 WHERE IdCliente = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idCliente);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al desactivar cliente: " + e.getMessage());
            return false;
        }
    }
}