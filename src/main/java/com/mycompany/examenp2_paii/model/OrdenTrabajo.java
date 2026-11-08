package com.mycompany.examenp2_paii.model;

import java.util.Date;

public class OrdenTrabajo {
    private int idOrden;
    private int idCliente;
    private int idVehiculo;
    private Integer idMecanicoResponsable; // Usamos Integer en vez de int para permitir valores NULL
    private Date fechaIngreso;
    private int kilometraje;
    private String sintomas;
    private String diagnosticoPreliminar;
    private String estado;

    // Constructor vacío
    public OrdenTrabajo() {
    }

    // Constructor completo
    public OrdenTrabajo(int idOrden, int idCliente, int idVehiculo, Integer idMecanicoResponsable, Date fechaIngreso, int kilometraje, String sintomas, String diagnosticoPreliminar, String estado) {
        this.idOrden = idOrden;
        this.idCliente = idCliente;
        this.idVehiculo = idVehiculo;
        this.idMecanicoResponsable = idMecanicoResponsable;
        this.fechaIngreso = fechaIngreso;
        this.kilometraje = kilometraje;
        this.sintomas = sintomas;
        this.diagnosticoPreliminar = diagnosticoPreliminar;
        this.estado = estado;
    }

    // --- GETTERS Y SETTERS ---
    public int getIdOrden() { return idOrden; }
    public void setIdOrden(int idOrden) { this.idOrden = idOrden; }

    public int getIdCliente() { return idCliente; }
    public void setIdCliente(int idCliente) { this.idCliente = idCliente; }

    public int getIdVehiculo() { return idVehiculo; }
    public void setIdVehiculo(int idVehiculo) { this.idVehiculo = idVehiculo; }

    public Integer getIdMecanicoResponsable() { return idMecanicoResponsable; }
    public void setIdMecanicoResponsable(Integer idMecanicoResponsable) { this.idMecanicoResponsable = idMecanicoResponsable; }

    public Date getFechaIngreso() { return fechaIngreso; }
    public void setFechaIngreso(Date fechaIngreso) { this.fechaIngreso = fechaIngreso; }

    public int getKilometraje() { return kilometraje; }
    public void setKilometraje(int kilometraje) { this.kilometraje = kilometraje; }

    public String getSintomas() { return sintomas; }
    public void setSintomas(String sintomas) { this.sintomas = sintomas; }

    public String getDiagnosticoPreliminar() { return diagnosticoPreliminar; }
    public void setDiagnosticoPreliminar(String diagnosticoPreliminar) { this.diagnosticoPreliminar = diagnosticoPreliminar; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
}