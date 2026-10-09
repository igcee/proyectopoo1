package com.example;

public class UCCI {
    private String sector;
    private int nave;
    private int codigo;
    private String estado;
    private double potenciaAux;
    private double limitePotencia;
    private boolean compensacionClimatica;
    private String ubicacion;

    private BrazoMecanico brazo;
    private BitacoraHistorica bitacora;
    private OperadorHumano operador;



    public UCCI(String sector, int nave, String estado, double potenciaAux, double limitePotencia, boolean compensacionClimatica, String ubicacion) {
    }

    public boolean calculoCapacidadMaxima(double potenciaAux, double limitePotencia) {
        // evaluar si "suma de la potencia requerida por el brazo y sus sistemas auxiliares excede la capacidad maxima de pontencia en vatios configurada por el controlador"
        return potenciaAux + brazo.calcularGastoEnergetico() <= limitePotencia;
    }

    public String consultarDisponibilidadBrazo(){
        // en realidad creo que este metodo deberia de ser tipo void, segun enunciado 
        // "Si el consumo excede el límite permitido, o si las horas de uso acumuladas del brazo superan las 1,000 horas de operación continua sin mantenimiento, la unidad central debe cambiar automáticamente su estado a modo de contingencia ("Bajo Consumo / Mantenimiento Requerido")"
        // osea q solo deberia de tener if ... setEstado("Bajo Consumo"), sin el return. Igual ahi lo dejo pq es String rn, pero para tomar en cuenta dsps.
        if (calculoCapacidadMaxima(potenciaAux, limitePotencia) or brazo.getHorasUsado() > 1000) {
            setEstado("Bajo Consumo");
        }
        return getEstado();
    }

    public void activarModoContingencia(){
        // si bajo consumo (osea horas de brazo > 1k o calculoCapacidadMaxima True), reducir velo en 40%
        if (getEstado() == "Bajo Consumo"){
            brazo.setVelocidad(brazo.getVelocidad() * 0.6);
        } 
    }

    public void procesarDatosClima(){
        // lo de la api de la class ServicioClima
        // saca temperatura y humedad, recibe la ubicacion
    }

    public ReporteMuestreo ejecutarMuestreo(double ph, double conductividad){
        
    }

    public void registrarReporte(ReporteMuestreo reporte){
        
    }

    public void fallaCritica(){
        // lo de simulacion de falla critica del RF6. 
        // "Forzar la destrucción/desactivación de la unidad central para verificar por consola que el brazo robótico deja de existir/responder, mientras que la bitácora y los reportes previos se conservan intactos."
    }





    public String getSector() {
        return sector;
    }

    public void setSector(String sector) {
        this.sector = sector;
    }

    public int getNave() {
        return nave;
    }

    public void setNave(int nave) {
        this.nave = nave;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public double getPotenciaAux() {
        return potenciaAux;
    }

    public void setPotenciaAux(double potenciaAux) {
        this.potenciaAux = potenciaAux;
    }

    public double getLimitePotencia() {
        return limitePotencia;
    }

    public void setLimitePotencia(double limitePotencia) {
        this.limitePotencia = limitePotencia;
    }

    public boolean isCompensacionClimatica() {
        return compensacionClimatica;
    }

    public void setCompensacionClimatica(boolean compensacionClimatica) {
        this.compensacionClimatica = compensacionClimatica;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    public BrazoMecanico getBrazo() {
        return brazo;
    }

    public void setBrazo(BrazoMecanico brazo) {
        this.brazo = brazo;
    }

    public BitacoraHistorica getBitacora() {
        return bitacora;
    }

    public void setBitacora(BitacoraHistorica bitacora) {
        this.bitacora = bitacora;
    }

    public OperadorHumano getOperador() {
        return operador;
    }

    public void setOperador(OperadorHumano operador) {
        this.operador = operador;
    }




    @Override
    public String toString() {
        return "UCCI{" + "sector=" + sector + ", nave=" + nave + ", codigo=" + codigo + ", estado=" + estado + ", potenciaAux=" + potenciaAux + ", limitePotencia=" + limitePotencia + ", compensacionClimatica=" + compensacionClimatica + ", ubicacion=" + ubicacion + ", brazo=" + brazo + ", bitacora=" + bitacora + ", operador=" + operador + '}';
    }

}
