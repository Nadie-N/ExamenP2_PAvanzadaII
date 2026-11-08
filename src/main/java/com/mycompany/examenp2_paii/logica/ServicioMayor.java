package com.mycompany.examenp2_paii.logica;

public class ServicioMayor extends ServicioBase {
    public ServicioMayor(double costo) { super(costo); }
    
    @Override
    public double calcularCostoTotal() {
        return costoManoObra * 1.25; // + 25% de impuesto
    }
}