package cr.ac.tec.ecohydro.modelo;

import cr.ac.tec.ecohydro.servicio.ServicioClima;
import cr.ac.tec.ecohydro.servicio.ServicioCorreo;

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

    private static int cuentaCodigos = 1;



    public UCCI(String sector, int nave, String estado, double potenciaAux, double limitePotencia, boolean compensacionClimatica, String ubicacion, double velocidad, double velocidadNominal, double anguloExtension, double horasUsado) {
    
    // le meti las 4 variables del brazo, pq en teoria este esta pegado por lo de composicion
    setSector(sector);
    setNave(nave);
    this.codigo = cuentaCodigos++; // para q inc para cada obj
    setEstado(estado);
    setPotenciaAux(potenciaAux);
    setLimitePotencia(limitePotencia);
    this.compensacionClimatica = compensacionClimatica;
    setUbicacion(ubicacion);
    this.brazo = new BrazoMecanico(velocidad, velocidadNominal, anguloExtension, horasUsado);
}

    public boolean calculoCapacidadMaxima(double potenciaAux, double limitePotencia) {
        // evaluar si "suma de la potencia requerida por el brazo y sus sistemas auxiliares excede la capacidad maxima de pontencia en vatios configurada por el controlador"
        return potenciaAux + brazo.calcularGastoEnergetico() <= limitePotencia;
    }

    public String consultarDisponibilidadBrazo(){
        // en realidad creo que este metodo deberia de ser tipo void, segun enunciado 
        // "Si el consumo excede el límite permitido, o si las horas de uso acumuladas del brazo superan las 1,000 horas de operación continua sin mantenimiento, la unidad central debe cambiar automáticamente su estado a modo de contingencia ("Bajo Consumo / Mantenimiento Requerido")"
        // osea q solo deberia de tener if ... setEstado("Bajo Consumo"), sin el return. Igual ahi lo dejo pq es String rn, pero para tomar en cuenta dsps.
        // ---- EN REALIDAD si es String pq devuelve un "diagnostico textual"
        if (brazo == null){
            return "Estado:" + estado+ ". No hay un brazo disponible para esta unidad, por lo que no se pueden iniciar muestreos";
        }

        if (!"Bajo Consumo / Mantenimiento Requerido".equals(estado) && !"Falla Critica".equals(estado) && (!calculoCapacidadMaxima(potenciaAux, limitePotencia) || brazo.requiereMantenimiento())){
            activarModoContingencia();
        }
        
        double dispo = limitePotencia - potenciaAux - brazo.calcularGastoEnergetico();
        if ("Falla Critica".equals(estado)){
            return "Estado:" +estado+ ". Potencia disponible: "+dispo+"W. Apta para iniciar muestreos: NO";
        }
        else{
            return "Estado:" +estado+ ". Potencia disponible: "+dispo+"W. Apta para iniciar muestreos: SI";
            }
        
        
    }

    public void activarModoContingencia(){
        // si bajo consumo (osea horas de brazo > 1k o calculoCapacidadMaxima True), reducir velo en 40%
        if (brazo == null ||  "Bajo Consumo / Mantenimiento Requerido".equals(estado)){ // si ya estaba en modo contingencia tonces no se le toca nada
            return;
        }
        else{
            estado = "Bajo Consumo / Mantenimiento Requerido";
            brazo.reducirVelocidad();
        }
       
        
    }

    public String procesarDatosClima(ServicioClima servclima){
        // lo de la api de la class ServicioClima
        // saca temperatura y humedad, recibe la ubicacion
        String salidilla = "";
        double[] clima = servclima.consultarClima(ubicacion);
        if (clima == null){
            return "No se pudo consultar el clima, verifique la ubicacion o el estado de OpenMeteo y Geocoding API";
        }
        double temperatura = clima[0];
        double humedad = clima[1];
        if (temperatura > 32){
            compensacionClimatica = true;
        }
        else{
            compensacionClimatica = false;
        }
        salidilla = "Clima en " + ubicacion +"= Temperatura: " +temperatura +"C, Humedad: "+humedad + "%, Fuentes: OpenMeteo y Geocoding API";
        if (compensacionClimatica){
            salidilla += " Compensacion climatica activada (+5% a conductividad)";
        }

        return salidilla;
    }

    public ReporteMuestreo ejecutarMuestreo(double ph, double conductividad, double duracionHoras){
        if ("Falla Critica".equals(estado) || brazo == null){
            //nose q hacer aca, en teoria deberia de mandar algun tipo de error o yoqsé
            // error de estado/brazo inexistente
            throw new IllegalStateException("No se puede ejecutar el muestreo ya que la unidad esta en Falla Critica o no tiene un brazo");
        }

        if (compensacionClimatica){
            conductividad = conductividad * 1.05;
        }

        return brazo.muestrear(ph, conductividad, duracionHoras);
        

        
    }

    public void registrarReporte(ReporteMuestreo reporte, ServicioCorreo correo){
        if (bitacora == null){
            throw new IllegalStateException("No hay una bitacora asociada a esta unidad");
        }
        else{
            bitacora.agregarReporte(reporte);
            if (reporte.anomaliaCritica()){
                correo.enviarAlertaCritica(operador, reporte, ubicacion); // estaba pensando en tambien incluir ubicacion para enviarAlertaCritica, idk
            }
        }
        
        
    }

    public void fallaCritica(){
        // lo de simulacion de falla critica del RF6. 
        // "Forzar la destrucción/desactivación de la unidad central para verificar por consola que el brazo robótico deja de existir/responder, mientras que la bitácora y los reportes previos se conservan intactos."
        brazo = null;
        estado = "Falla Critica";
    }





    public String getSector() {
        return sector;
    }

    public void setSector(String sector) {
        if (sector == null || sector.trim().isEmpty()) {
            throw new IllegalArgumentException("El sector no puede estar vacio.");
        }
        this.sector = sector.trim();
    }

    public int getNave() {
        return nave;
    }

    public void setNave(int nave) {
        if (nave < 1) {
            throw new IllegalArgumentException("El numero de nave debe ser mayor o igual a 1.");
        }
        this.nave = nave;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        if (codigo < 1) {
            throw new IllegalArgumentException("El codigo de la unidad debe ser mayor o igual a 1.");
        }
        this.codigo = codigo;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        if (!"Activo".equals(estado)
                && !"Bajo Consumo / Mantenimiento Requerido".equals(estado)
                && !"Falla Critica".equals(estado)) {
            throw new IllegalArgumentException("Estado invalido. Debe ser: Activo, Bajo Consumo / Mantenimiento Requerido o Falla Critica.");
        }
        this.estado = estado;
    }

    public double getPotenciaAux() {
        return potenciaAux;
    }

    public void setPotenciaAux(double potenciaAux) {
        if (!(potenciaAux >= 0)) {
            throw new IllegalArgumentException("La potencia de los sistemas auxiliares no puede ser negativa.");
        }
        this.potenciaAux = potenciaAux;
    }

    public double getLimitePotencia() {
        return limitePotencia;
    }

    public void setLimitePotencia(double limitePotencia) {
        if (!(limitePotencia > 0)) {
            throw new IllegalArgumentException("El limite de potencia debe ser mayor que 0.");
        }
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
        if (ubicacion == null || ubicacion.trim().isEmpty()) {
            throw new IllegalArgumentException("La ubicacion no puede estar vacia.");
        }
        this.ubicacion = ubicacion.trim();
    }

    public BrazoMecanico getBrazo() {
        return brazo;
    }

    public void setBrazo(BrazoMecanico brazo) {
        if (brazo == null) {
            throw new IllegalArgumentException("La unidad necesita un brazo robotico (composicion). Para destruirlo use fallaCritica().");
        }
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
        if (operador == null) {
            throw new IllegalArgumentException("La unidad debe tener un operador asignado.");
        }
        this.operador = operador;
    }




    @Override
    public String toString() {
        return "UCCI{" + "sector=" + sector + ", nave=" + nave + ", codigo=" + codigo + ", estado=" + estado + ", potenciaAux=" + potenciaAux + ", limitePotencia=" + limitePotencia + ", compensacionClimatica=" + compensacionClimatica + ", ubicacion=" + ubicacion + ", brazo=" + brazo + ", bitacora=" + bitacora + ", operador=" + operador + '}';
    }

}
