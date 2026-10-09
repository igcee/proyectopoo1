package com.example;
import java.time.LocalDate;

public class ReporteMuestreo {
    private LocalDate fechaMedicion;
    private double gastoEnergetico;
    // estos 2 de aca abajo hay q ver si cambiar o no
    private double ph; 
    private double conductividad;

    public ReporteMuestreo(double ph, double conductividad, double gastoEnergetico){

    }

    public boolean anomaliaCritica(){
        // condiciones de anomalia = ph < 5.5 o > 6.5, o conductividad < 1.2 dS/M o > 2.4 dS/M
        // cuando reporte tiene anomalia, tiene q sacar automaticamente un msj de emergencia (osea el chunche de correo)
    }


    public String resumenAnomalia(){
        // no me acuerdo q tenia q sacar este chunche
    }

    public LocalDate getFechaMedicion() {
        return fechaMedicion;
    }

    public void setFechaMedicion(LocalDate fechaMedicion) {
        this.fechaMedicion = fechaMedicion;
    }

    public double getGastoEnergetico(){
        return gastoEnergetico;
    }

    public void setGastoEnergetico(double gastoEnergetico){
        this.gastoEnergetico = gastoEnergetico;
    }

    public double getPh() {
        return ph;
    }

    public void setPh(double ph) {
        this.ph = ph;
    }

    public double getConductividad() {
        return conductividad;
    }

    public void setConductividad(double conductividad) {
        this.conductividad = conductividad;
    }

}
