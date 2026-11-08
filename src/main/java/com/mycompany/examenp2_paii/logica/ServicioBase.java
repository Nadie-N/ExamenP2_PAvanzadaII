package com.mycompany.examenp2_paii.logica;

public abstract class ServicioBase implements IServicio {
    protected double costoManoObra;

    public ServicioBase(double costoManoObra) {
        this.costoManoObra = costoManoObra;
    }
}