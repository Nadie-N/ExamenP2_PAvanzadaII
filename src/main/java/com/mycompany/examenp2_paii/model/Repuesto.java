package com.mycompany.examenp2_paii.model;

public class Repuesto {
    private int idRepuesto;
    private String codigo;
    private String nombre;
    private String descripcion;
    private String marca;
    private double costo;
    private double precioVenta;
    private int stock;
    private int stockMinimo;
    private boolean estado;

    public Repuesto() {}

    // Getters y Setters
    public int getIdRepuesto() { return idRepuesto; }
    public void setIdRepuesto(int idRepuesto) { this.idRepuesto = idRepuesto; }

    public String getCodigo() { return codigo; }
    public void setCodigo(String codigo) { this.codigo = codigo; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public String getMarca() { return marca; }
    public void setMarca(String marca) { this.marca = marca; }

    public double getCosto() { return costo; }
    public void setCosto(double costo) { this.costo = costo; }

    public double getPrecioVenta() { return precioVenta; }
    public void setPrecioVenta(double precioVenta) { this.precioVenta = precioVenta; }

    public int getStock() { return stock; }
    public void setStock(int stock) { this.stock = stock; }

    public int getStockMinimo() { return stockMinimo; }
    public void setStockMinimo(int stockMinimo) { this.stockMinimo = stockMinimo; }

    public boolean isEstado() { return estado; }
    public void setEstado(boolean estado) { this.estado = estado; }
    
    @Override
    public String toString() {
        return this.codigo + " - " + this.nombre; // Útil para mostrarlo en combos
    }
}