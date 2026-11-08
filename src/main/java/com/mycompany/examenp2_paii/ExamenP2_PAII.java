package com.mycompany.examenp2_paii;

import com.mycompany.examenp2_paii.ui.FrmClientes;
import java.sql.Connection;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

public class ExamenP2_PAII {
    public static void main(String[] args) {
        try (Connection c = DatabaseConnection.getConnection()) {
            System.out.println("✅ Conectado a: " + c.getCatalog());
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error DB: " + e.getMessage());
            return;
        }

        SwingUtilities.invokeLater(() -> {
            new FrmClientes().setVisible(true);
        });
    }
}