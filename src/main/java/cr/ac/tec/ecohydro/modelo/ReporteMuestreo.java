package cr.ac.tec.ecohydro.modelo;
import java.time.LocalDateTime; // era LocalDateTime, no LocalDate, sino no agarra hora

public class ReporteMuestreo {
    private LocalDateTime fechaMedicion;
    private double gastoEnergetico;
    // estos 2 de aca abajo hay q ver si cambiar o no
    private double ph; 
    private double conductividad;

    public ReporteMuestreo(double ph, double conductividad, double gastoEnergetico){
        setPh(ph);
        setConductividad(conductividad);
        setGastoEnergetico(gastoEnergetico);
        this.fechaMedicion = LocalDateTime.now(); // saca la fecha del momento q se usa
    }

    public boolean anomaliaCritica(){
        // condiciones de anomalia = ph < 5.5 o > 6.5, o conductividad < 1.2 dS/M o > 2.4 dS/M
        // cuando reporte tiene anomalia, tiene q sacar automaticamente un msj de emergencia (osea el chunche de correo)
        // EN REALIDAD creo que UCCI deberia de enviar el correo, no ReporteMuestreo. Por cualquier cosa, si pusieramos lo de correo en ReporteMuestreo, iria aca
        return ph > 6.5 || ph < 5.5 || conductividad < 1.2 || conductividad > 2.4;
    }


    public String resumenAnomalia(){
        String salidaResumen = "";
        
        if (ph > 6.5 || ph < 5.5){
            salidaResumen += "Anomalia: ph fuera de rango = " + ph + " (rango optimo: 5.5 - 6.5) ";
        }
        else if(conductividad < 1.2 || conductividad > 2.4){
            salidaResumen += "Anomalia: conductividad fuera de rango = " + conductividad + "(rango optimo: 1.2 - 2.4 ds/M)";
        }
        else{
            salidaResumen += "Sin anomalias, ph se encuentra en el rango optimo (" + ph + ") y la conductividad tambien (" + conductividad + ").";

        }

        return salidaResumen;
    }
    
    

    public LocalDateTime getFechaMedicion() {
        return fechaMedicion;
    }

    public void setFechaMedicion(LocalDateTime fechaMedicion) {
        if (fechaMedicion == null) {
            throw new IllegalArgumentException("La fecha de medicion no puede ser nula.");
        }
        this.fechaMedicion = fechaMedicion;
    }

    public double getGastoEnergetico(){
        return gastoEnergetico;
    }

    public void setGastoEnergetico(double gastoEnergetico){
        if (!(gastoEnergetico >= 0)) {
            throw new IllegalArgumentException("El gasto energetico no puede ser negativo.");
        }
        this.gastoEnergetico = gastoEnergetico;
    }

    public double getPh() {
        return ph;
    }

    public void setPh(double ph) {
        if (!(ph >= 0 && ph <= 14)) {
            throw new IllegalArgumentException("El pH debe estar entre 0 y 14.");
        }
        this.ph = ph;
    }

    public double getConductividad() {
        return conductividad;
    }

    public void setConductividad(double conductividad) {
        if (!(conductividad >= 0)) {
            throw new IllegalArgumentException("La conductividad no puede ser negativa.");
        }
        this.conductividad = conductividad;
    }

    @Override
    public String toString(){
        return "ReporteMuestreo{ph=" + ph + ", conductividad="+ conductividad + ", gastoEnergetico=" + gastoEnergetico +", fechaMedicion=" + fechaMedicion + "}";

    }

}
