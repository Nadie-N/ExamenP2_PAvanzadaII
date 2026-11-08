package com.mycompany.examenp2_paii.model;

public class Cliente {
    private int idCliente;
    private String nombre;
    private String dni;
    private String telefono;
    private String correo;
    private boolean estado;

    public Cliente() {}

    public Cliente(int idCliente, String nombre, String dni, String telefono, String correo, boolean estado) {
        this.idCliente = idCliente;
        this.nombre = nombre;
        this.dni = dni;
        this.telefono = telefono;
        this.correo = correo;
        this.estado = estado;
    }

    // Getters y Setters
    public int getIdCliente() { return idCliente; }
    public void setIdCliente(int idCliente) { this.idCliente = idCliente; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getDni() { return dni; }
    public void setDni(String dni) { this.dni = dni; }

    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }

    public String getCorreo() { return correo; }
    public void setCorreo(String correo) { this.correo = correo; }

    public boolean isEstado() { return estado; }
    public void setEstado(boolean estado) { this.estado = estado; }

    @Override
    public String toString() {
        return this.nombre; // Para que en el combo solo se vea el nombre del cliente
    }
}