package com.mycompany.examenp2_paii.logica;

public class ServicioBasico extends ServicioBase {
    public ServicioBasico(double costo) { super(costo); }
    
    @Override
    public double calcularCostoTotal() {
        return costoManoObra * 1.15; // + 15% de impuesto
    }
}