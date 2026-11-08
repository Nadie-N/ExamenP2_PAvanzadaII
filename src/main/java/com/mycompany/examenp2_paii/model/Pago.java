package com.mycompany.examenp2_paii.model;

import java.util.Date;

public class Pago {
    private int idPago;
    private int idOrden;
    private Date fechaPago;
    private double monto;
    private String metodoPago;
    private String referencia;

    public Pago() {}

    // Getters y Setters
    public int getIdPago() { return idPago; }
    public void setIdPago(int idPago) { this.idPago = idPago; }

    public int getIdOrden() { return idOrden; }
    public void setIdOrden(int idOrden) { this.idOrden = idOrden; }

    public Date getFechaPago() { return fechaPago; }
    public void setFechaPago(Date fechaPago) { this.fechaPago = fechaPago; }

    public double getMonto() { return monto; }
    public void setMonto(double monto) { this.monto = monto; }

    public String getMetodoPago() { return metodoPago; }
    public void setMetodoPago(String metodoPago) { this.metodoPago = metodoPago; }

    public String getReferencia() { return referencia; }
    public void setReferencia(String referencia) { this.referencia = referencia; }
}