package com.mycompany.examenp2_paii.ui;

import com.mycompany.examenp2_paii.dao.PagoDAO;
import com.mycompany.examenp2_paii.model.Pago;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class FrmPago extends javax.swing.JFrame {

    private JTextField txtIdOrden, txtMonto, txtReferencia;
    private JComboBox<String> cmbMetodo;
    private JTable tblHistorial;
    private DefaultTableModel modeloTabla;

    public FrmPago() {
        setTitle("Módulo de Pagos e Historial - AutoTech");
        setSize(900, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        initComponents();
        cargarHistorial(); // Carga la tabla al abrir
    }

    private void initComponents() {
        // --- PANEL DE REGISTRO DE PAGOS (Arriba) ---
        JPanel panelPago = new JPanel(new GridLayout(2, 4, 10, 10));
        panelPago.setBorder(BorderFactory.createTitledBorder("Registrar Nuevo Abono"));

        panelPago.add(new JLabel("ID de la Orden:"));
        txtIdOrden = new JTextField();
        panelPago.add(txtIdOrden);

        panelPago.add(new JLabel("Monto a Pagar (L.):"));
        txtMonto = new JTextField();
        panelPago.add(txtMonto);

        panelPago.add(new JLabel("Método de Pago:"));
        cmbMetodo = new JComboBox<>(new String[]{"Efectivo", "Tarjeta", "Transferencia", "Cheque"});
        panelPago.add(cmbMetodo);

        panelPago.add(new JLabel("Referencia (Opcional):"));
        txtReferencia = new JTextField();
        panelPago.add(txtReferencia);

        JButton btnRegistrar = new JButton("Registrar Pago");
        btnRegistrar.setBackground(new Color(50, 150, 50));
        btnRegistrar.setForeground(Color.WHITE);
        btnRegistrar.addActionListener(e -> registrarPago());

        JPanel panelBoton = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panelBoton.add(btnRegistrar);

        // --- PANEL DE HISTORIAL (Abajo) ---
        JPanel panelHistorial = new JPanel(new BorderLayout());
        panelHistorial.setBorder(BorderFactory.createTitledBorder("Historial de Órdenes (Vista SQL)"));

        modeloTabla = new DefaultTableModel(new String[]{"ID Orden", "Cliente", "Placa", "Vehículo", "Mecánico", "Estado", "Síntomas"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };
        tblHistorial = new JTable(modeloTabla);
        JScrollPane scroll = new JScrollPane(tblHistorial);

        JButton btnActualizar = new JButton("Actualizar Historial");
        btnActualizar.addActionListener(e -> cargarHistorial());

        JPanel panelBotonTabla = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panelBotonTabla.add(btnActualizar);

        panelHistorial.add(scroll, BorderLayout.CENTER);
        panelHistorial.add(panelBotonTabla, BorderLayout.SOUTH);

        // --- ENSAMBLAR TODO ---
        JPanel panelSuperior = new JPanel(new BorderLayout());
        panelSuperior.add(panelPago, BorderLayout.CENTER);
        panelSuperior.add(panelBoton, BorderLayout.SOUTH);

        setLayout(new BorderLayout(10, 10));
        add(panelSuperior, BorderLayout.NORTH);
        add(panelHistorial, BorderLayout.CENTER);
    }

    private void registrarPago() {
        try {
            if (txtIdOrden.getText().trim().isEmpty() || txtMonto.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Debe ingresar el ID de la orden y el monto.");
                return;
            }

            // Armar el objeto Modelo
            Pago p = new Pago();
            p.setIdOrden(Integer.parseInt(txtIdOrden.getText().trim()));
            p.setMonto(Double.parseDouble(txtMonto.getText().trim()));
            p.setMetodoPago(cmbMetodo.getSelectedItem().toString());
            p.setReferencia(txtReferencia.getText().trim());

            // Llamar al DAO que usa el Procedimiento Almacenado
            PagoDAO dao = new PagoDAO();
            dao.registrarPago(p);

            JOptionPane.showMessageDialog(this, "¡Pago registrado exitosamente!");
            
            // Limpiar campos
            txtIdOrden.setText("");
            txtMonto.setText("");
            txtReferencia.setText("");
            
            // Refrescar la tabla
            cargarHistorial();

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "El ID y el monto deben ser números válidos.", "Error de formato", JOptionPane.WARNING_MESSAGE);
        } catch (Exception ex) {
            // Si el Procedimiento Almacenado detecta que hay sobrepago, lanza el RAISERROR y cae aquí
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Error de Base de Datos", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void cargarHistorial() {
        modeloTabla.setRowCount(0); // Limpiar tabla
        String sql = "SELECT * FROM vw_HistorialOrdenes";

        try (Connection conn = com.mycompany.examenp2_paii.DatabaseConnection.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                modeloTabla.addRow(new Object[]{
                    rs.getInt("IdOrden"),
                    rs.getString("Cliente"),
                    rs.getString("Placa"),
                    rs.getString("Vehiculo"),
                    rs.getString("MecanicoResponsable"),
                    rs.getString("Estado"),
                    rs.getString("Sintomas")
                });
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al cargar la vista del historial: " + e.getMessage());
        }
    }

    public static void main(String args[]) {
        // Look and feel Nimbus
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception ex) {
            java.util.logging.Logger.getLogger(FrmPago.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

        java.awt.EventQueue.invokeLater(() -> new FrmPago().setVisible(true));
    }
}