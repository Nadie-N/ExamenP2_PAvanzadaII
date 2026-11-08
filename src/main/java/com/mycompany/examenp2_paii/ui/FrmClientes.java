package com.mycompany.examenp2_paii.ui;

import com.mycompany.examenp2_paii.dao.ClienteDAO;
import com.mycompany.examenp2_paii.model.Cliente;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class FrmClientes extends JFrame {
    private JTextField txtNombre, txtDni, txtTelefono, txtCorreo;
    private JTable tabla;
    private DefaultTableModel modeloTabla;
    private ClienteDAO dao;
    private int idSeleccionado = -1;

    public FrmClientes() {
        dao = new ClienteDAO();
        setTitle("Gestión de Clientes - AutoTech");
        setSize(800, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        initComponents();
        cargarTabla();
    }

    private void initComponents() {
        JPanel panelForm = new JPanel(new GridLayout(4, 2, 5, 5));
        panelForm.setBorder(BorderFactory.createTitledBorder("Datos del Cliente"));

        txtNombre = new JTextField();
        txtDni = new JTextField();
        txtTelefono = new JTextField();
        txtCorreo = new JTextField();

        panelForm.add(new JLabel("Nombre:")); panelForm.add(txtNombre);
        panelForm.add(new JLabel("DNI:")); panelForm.add(txtDni);
        panelForm.add(new JLabel("Teléfono:")); panelForm.add(txtTelefono);
        panelForm.add(new JLabel("Correo:")); panelForm.add(txtCorreo);

        JPanel panelBotones = new JPanel(new FlowLayout());
        JButton btnGuardar = new JButton("Guardar");
        JButton btnActualizar = new JButton("Actualizar");
        JButton btnDesactivar = new JButton("Desactivar");
        JButton btnLimpiar = new JButton("Limpiar");

        panelBotones.add(btnGuardar);
        panelBotones.add(btnActualizar);
        panelBotones.add(btnDesactivar);
        panelBotones.add(btnLimpiar);

        modeloTabla = new DefaultTableModel(new String[]{"ID", "Nombre", "DNI", "Teléfono", "Correo"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };
        tabla = new JTable(modeloTabla);
        
        // Evento seleccionar tabla
        tabla.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && tabla.getSelectedRow() != -1) {
                int fila = tabla.getSelectedRow();
                idSeleccionado = (int) modeloTabla.getValueAt(fila, 0);
                txtNombre.setText(modeloTabla.getValueAt(fila, 1).toString());
                txtDni.setText(modeloTabla.getValueAt(fila, 2).toString());
                txtTelefono.setText(modeloTabla.getValueAt(fila, 3).toString());
                txtCorreo.setText(modeloTabla.getValueAt(fila, 4).toString());
            }
        });

        // Acciones de botones
        btnGuardar.addActionListener(e -> guardar());
        btnActualizar.addActionListener(e -> actualizar());
        btnDesactivar.addActionListener(e -> desactivar());
        btnLimpiar.addActionListener(e -> limpiar());

        setLayout(new BorderLayout());
        add(panelForm, BorderLayout.NORTH);
        add(new JScrollPane(tabla), BorderLayout.CENTER);
        add(panelBotones, BorderLayout.SOUTH);
    }

    private void cargarTabla() {
        modeloTabla.setRowCount(0);
        List<Cliente> lista = dao.listarActivos();
        for (Cliente c : lista) {
            modeloTabla.addRow(new Object[]{c.getIdCliente(), c.getNombre(), c.getDni(), c.getTelefono(), c.getCorreo()});
        }
    }

    private void guardar() {
        if (txtNombre.getText().trim().isEmpty() || txtDni.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Nombre y DNI son obligatorios");
            return;
        }
        Cliente c = new Cliente();
        c.setNombre(txtNombre.getText());
        c.setDni(txtDni.getText());
        c.setTelefono(txtTelefono.getText());
        c.setCorreo(txtCorreo.getText());

        if (dao.insertar(c)) {
            JOptionPane.showMessageDialog(this, "Cliente guardado");
            limpiar();
            cargarTabla();
        } else {
            JOptionPane.showMessageDialog(this, "Error al guardar (Quizás el DNI ya existe)");
        }
    }

    private void actualizar() {
        if (idSeleccionado == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione un cliente de la tabla");
            return;
        }
        Cliente c = new Cliente();
        c.setIdCliente(idSeleccionado);
        c.setNombre(txtNombre.getText());
        c.setDni(txtDni.getText());
        c.setTelefono(txtTelefono.getText());
        c.setCorreo(txtCorreo.getText());

        if (dao.actualizar(c)) {
            JOptionPane.showMessageDialog(this, "Cliente actualizado");
            limpiar();
            cargarTabla();
        } else {
            JOptionPane.showMessageDialog(this, "Error al actualizar");
        }
    }

    private void desactivar() {
        if (idSeleccionado == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione un cliente de la tabla");
            return;
        }
        int confirm = JOptionPane.showConfirmDialog(this, "¿Desactivar cliente?", "Confirmar", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            if (dao.desactivar(idSeleccionado)) {
                JOptionPane.showMessageDialog(this, "Cliente desactivado");
                limpiar();
                cargarTabla();
            }
        }
    }

    private void limpiar() {
        idSeleccionado = -1;
        txtNombre.setText("");
        txtDni.setText("");
        txtTelefono.setText("");
        txtCorreo.setText("");
        tabla.clearSelection();
    }
}