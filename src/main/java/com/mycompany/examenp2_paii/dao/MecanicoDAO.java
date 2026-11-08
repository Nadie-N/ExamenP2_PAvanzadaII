package com.mycompany.examenp2_paii.dao;

import com.mycompany.examenp2_paii.DatabaseConnection;
import com.mycompany.examenp2_paii.model.Mecanico;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MecanicoDAO {

    public boolean insertar(Mecanico m) {
        String sql = "INSERT INTO Mecanicos (Nombre, Apellido, DNI, Telefono, Estado) VALUES (?, ?, ?, ?, 1)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, m.getNombre());
            ps.setString(2, m.getApellido());
            ps.setString(3, m.getDni());
            ps.setString(4, m.getTelefono());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error insertar mecanico: " + e.getMessage());
            return false;
        }
    }

    public List<Mecanico> listarActivos() {
        List<Mecanico> lista = new ArrayList<>();
        String sql = "SELECT * FROM Mecanicos WHERE Estado = 1 ORDER BY Nombre";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                Mecanico m = new Mecanico();
                m.setIdMecanico(rs.getInt("IdMecanico"));
                m.setNombre(rs.getString("Nombre"));
                m.setApellido(rs.getString("Apellido"));
                m.setDni(rs.getString("DNI"));
                m.setTelefono(rs.getString("Telefono"));
                m.setEstado(rs.getBoolean("Estado"));
                lista.add(m);
            }
        } catch (SQLException e) {
            System.err.println("Error listar mecanicos: " + e.getMessage());
        }
        return lista;
    }
}