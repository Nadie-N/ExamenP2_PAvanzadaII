package com.mycompany.examenp2_paii.model;

public class Mecanico {
    private int idMecanico;
    private String nombre;
    private String apellido;
    private String dni;
    private String telefono;
    private boolean estado;

    public Mecanico() {}

    public int getIdMecanico() { return idMecanico; }
    public void setIdMecanico(int idMecanico) { this.idMecanico = idMecanico; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getApellido() { return apellido; }
    public void setApellido(String apellido) { this.apellido = apellido; }

    public String getDni() { return dni; }
    public void setDni(String dni) { this.dni = dni; }

    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }

    public boolean isEstado() { return estado; }
    public void setEstado(boolean estado) { this.estado = estado; }
    
   
    @Override
    public String toString() {
        return this.nombre + " " + this.apellido; // Para que se vea "Nombre Apellido"
    }
}