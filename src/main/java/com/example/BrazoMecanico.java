package com.example;

public class BrazoMecanico {
    private double velocidad;
    private double velocidadNominal; // este para q era? XD
    private double anguloExtension;
    private double horasUsado;


    public BrazoMecanico(double velocidad, double velocidadNominal, double anguloExtension, double horasUsado){

    }

    public void reducirVelocidad(double porcentaje){
        // lo de reducir en modo de contingencia, aunque no creo que sea necesario, ya que se podria usar setVelocidad desde UCCI al usar el metodo de activarModoContingencia()


    }

    public void restaurarVelocidad(){
        // si no me equivoco, esto era para lo de recuperar a velocidad original despues de la reduccion de 40% de lo de modo de contingencia

    }

    public double calcularGastoEnergetico(){
        // Gasto(W) = (anguloExtension x 1.5) + (velocidad x 12.8)
        // enunciado dice "Velocidad de Desplazamiento", ese era velocidad o velocidadNominal?

    }

    public ReporteMuestreo muestrear(double ph, double conductividad, double duracionHoras){
        // el reporte q sale al finalizar la maniobra del brazo, tiene ph, conductividad, Gasto; y registra tmb fecha y hora de generacion

    }

    public boolean requiereMantenimiento(){
        // la vrdd no estoy muy seguro sobre esto? Ya ni me acuerdo para q la ocupabamos
        // tal vez quitar + tarde?

        
    }


    public double getVelocidad() {
        return velocidad;
    }


    public void setVelocidad(double velocidad) {
        this.velocidad = velocidad;
    }


    public double getVelocidadNominal() {
        return velocidadNominal;
    }


    public void setVelocidadNominal(double velocidadNominal) {
        this.velocidadNominal = velocidadNominal;
    }


    public double getAnguloExtension() {
        return anguloExtension;
    }


    public void setAnguloExtension(double anguloExtension) {
        this.anguloExtension = anguloExtension;
    }


    public double getHorasUsado() {
        return horasUsado;
    }


    public void setHorasUsado(double horasUsado) {
        this.horasUsado = horasUsado;
    }


    



    




}
