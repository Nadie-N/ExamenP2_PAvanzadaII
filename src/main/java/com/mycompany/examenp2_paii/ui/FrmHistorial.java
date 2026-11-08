package com.mycompany.examenp2_paii.ui;

import com.mycompany.examenp2_paii.DatabaseConnection;
import com.mycompany.examenp2_paii.model.Vehiculo;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class FrmHistorial extends javax.swing.JFrame {

    private JComboBox<Vehiculo> cmbVehiculos;
    private JTable tblHistorial;
    private DefaultTableModel modeloTabla;

    public FrmHistorial() {
        setTitle("Historial por Vehículo - AutoTech");
        setSize(800, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        initComponents();
        cargarComboVehiculos();
    }

    private void initComponents() {
        // --- PANEL SUPERIOR (Búsqueda) ---
        JPanel panelBusqueda = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        panelBusqueda.setBorder(BorderFactory.createTitledBorder("Seleccione un Vehículo"));

        panelBusqueda.add(new JLabel("Vehículo (Placa - Marca):"));
        
        cmbVehiculos = new JComboBox<>();
        cmbVehiculos.setPreferredSize(new Dimension(250, 25));
        panelBusqueda.add(cmbVehiculos);

        JButton btnBuscar = new JButton("Buscar Historial");
        btnBuscar.setBackground(new Color(50, 150, 50));
        btnBuscar.setForeground(Color.WHITE);
        btnBuscar.addActionListener(e -> buscarHistorial());
        panelBusqueda.add(btnBuscar);

        // --- PANEL CENTRAL (Tabla) ---
        modeloTabla = new DefaultTableModel(
            new String[]{"ID Orden", "Fecha Ingreso", "Mecánico Responsable", "Estado", "Síntomas"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };
        tblHistorial = new JTable(modeloTabla);
        JScrollPane scroll = new JScrollPane(tblHistorial);
        scroll.setBorder(BorderFactory.createTitledBorder("Historial de Órdenes del Vehículo"));

        // --- ENSAMBLAR ---
        setLayout(new BorderLayout(10, 10));
        add(panelBusqueda, BorderLayout.NORTH);
        add(scroll, BorderLayout.CENTER);
    }

    private void cargarComboVehiculos() {
        try (Connection conn = DatabaseConnection.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery("SELECT IdVehiculo, Placa, Marca, Modelo FROM Vehiculos WHERE Estado = 1")) {
            
            cmbVehiculos.removeAllItems();
            while (rs.next()) {
                Vehiculo v = new Vehiculo();
                v.setIdVehiculo(rs.getInt("IdVehiculo"));
                v.setPlaca(rs.getString("Placa"));
                v.setMarca(rs.getString("Marca"));
                v.setModelo(rs.getString("Modelo"));
                cmbVehiculos.addItem(v);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al cargar vehículos: " + e.getMessage());
        }
    }

    private void buscarHistorial() {
        Vehiculo v = (Vehiculo) cmbVehiculos.getSelectedItem();
        if (v == null) {
            JOptionPane.showMessageDialog(this, "No hay vehículos registrados.");
            return;
        }

        modeloTabla.setRowCount(0); // Limpiar tabla

        String sql = "SELECT O.IdOrden, O.FechaIngreso, M.Nombre + ' ' + M.Apellido AS Mecanico, " +
                     "O.Estado, O.Sintomas FROM OrdenesTrabajo O " +
                     "LEFT JOIN Mecanicos M ON O.IdMecanicoResponsable = M.IdMecanico " +
                     "WHERE O.IdVehiculo = ? ORDER BY O.FechaIngreso DESC";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setInt(1, v.getIdVehiculo());
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    modeloTabla.addRow(new Object[]{
                        rs.getInt("IdOrden"),
                        rs.getTimestamp("FechaIngreso"),
                        rs.getString("Mecanico") != null ? rs.getString("Mecanico") : "Sin asignar",
                        rs.getString("Estado"),
                        rs.getString("Sintomas")
                    });
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al buscar historial: " + e.getMessage());
        }
    }

    public static void main(String args[]) {
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception ex) {
            java.util.logging.Logger.getLogger(FrmHistorial.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

        java.awt.EventQueue.invokeLater(() -> new FrmHistorial().setVisible(true));
    }
}