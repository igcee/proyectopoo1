package cr.ac.tec.ecohydro.ui;

import java.util.ArrayList;
import java.util.Scanner;

import cr.ac.tec.ecohydro.modelo.BitacoraHistorica;
import cr.ac.tec.ecohydro.modelo.OperadorHumano;
import cr.ac.tec.ecohydro.modelo.ReporteMuestreo;
import cr.ac.tec.ecohydro.modelo.UCCI;
import cr.ac.tec.ecohydro.servicio.ServicioClima;
import cr.ac.tec.ecohydro.servicio.ServicioCorreo;

public class Menu{


    static Scanner entrada = new Scanner (System.in);
    private final BitacoraHistorica bitacora;
    private final ServicioClima clima;
    private final ServicioCorreo correo;

    // estos 2 no son final pq se modifican
    private UCCI unidad;
    private ReporteMuestreo reporte;

    public Menu(ServicioClima clima, ServicioCorreo correo){
        this.bitacora = new BitacoraHistorica();
        this.clima = clima;
        this.correo = correo;
    }

    //lee un entero dentro de un rango
    private static int leerEntero(String mensaje, int min, int max) {
        while (true) {
            System.out.print(mensaje);
            String texto = entrada.nextLine().trim();
            try {
                int valor = Integer.parseInt(texto);
                if (valor < min || valor > max) {
                    System.out.println("ERROR: el valor debe estar entre " + min + " y " + max + ".");
                } else {
                    return valor;
                }
            } catch (NumberFormatException e) {
                System.out.println("ERROR: debe ingresar un numero entero.");
            }
        }
    }

    //lee un decimal dentro de un rango
    private static double leerDecimal(String mensaje, double min, double max) {
        while (true) {
            System.out.print(mensaje);
            String texto = entrada.nextLine().trim();
            try {
                double valor = Double.parseDouble(texto);
                if (!(valor >= min && valor <= max)) {
                    System.out.println("ERROR: el valor debe estar entre " + min + " y " + max + ".");
                } else {
                    return valor;
                }
            } catch (NumberFormatException e) {
                System.out.println("ERROR: debe ingresar un numero (use punto decimal, ejemplo 6.5).");
            }
        }
    }

    //lee un texto completo 
    private static String leerTexto(String mensaje) {
        while (true) {
            System.out.print(mensaje);
            String texto = entrada.nextLine().trim();
            if (!texto.isEmpty()) {
                return texto;
            }
            System.out.println("ERROR: este dato no puede quedar vacio.");
        }
    }

    private static String leerCorreo(String mensaje) {
        while (true) {
            String texto = leerTexto(mensaje);
            if (texto.matches("^[\\w.+-]+@[\\w-]+(\\.[\\w-]+)+$")) {
                return texto;
            }
            System.out.println("ERROR: el correo no tiene un formato valido.");
        }
    }

    public void mostrarMenu(){
        System.out.println(" ==== Test menu bien bonito :) ==== ");
        if (unidad == null){
            System.out.println("No existe unidad aun, obligatorio crear una con la opcion 1 antes de seguir");
            // estaria bien poner un catch en el chunche de registro para el caso de darle a otra opcion cuando aun no existe una unidad
            // si no me equivoco eso se hace con un catch(IllegalStateException e), pero no estoy seguro de como armarlo
        }
        else{
            System.out.println("Unidad numero" + unidad.getCodigo()+ " ||| Estado: " + unidad.getEstado());
        }
        System.out.println(); // no me acuerdo de como hacer salto de linea, creo q asi sirve igual
        System.out.println(" ==== OPCIONES DEL MENU ====");
        System.out.println("1. Configuracion inicial"); // pide datos de UCCI, brazo y asign de operador
        System.out.println("2. Ejecutar ciclo de muestreo"); // RF1 y RF2, iniciar inspeccion fisica (?), calcular consumo energetico y mostrar reporte
        System.out.println("3. Sincronizar clima externo"); // RF4, obtener datos meteo y ajustar param de sistema (osea los chunches de api)
        System.out.println("4. Registrar y analizar en bitacora"); // RF3 y RF5, guarda reporte en bitacora, verifica anomalias y envia correo a operador si anomala
        System.out.println("5. Ver estado de bitacora e historial"); // mostrar "estadisticas acomuladas" (???) y % de anomalias
        System.out.println("6. Simulacion de falla critica"); // destruccion de UCCI para verificar q brazo deja de existir, mientras q bitacora y reportes quedan intactos
        System.out.println("7. Salir");
        //System.out.println("Seleccione una opcion: ");


    }
    //error si no existe unidad
    private void exigirUnidad() {
        if (unidad == null) {
            throw new IllegalStateException("No existe una unidad configurada. Por favor, configure una unidad antes de continuar.");
        }
    }


    public void inicio(){
        boolean salir = false;
        while (!salir){
            mostrarMenu();
            int eleccion =leerEntero("Seleccione una opcion: ",1,7);
            //para que valida solo numeros del 1 al 7 y yap
            try{
                switch(eleccion){
                    case 1 -> configInicial();
                    case 2 -> ejecutarMuestreo();
                    case 3 -> sincronizarClima();
                    case 4 -> registrarYAnalizar();
                    case 5 -> verBitacora();
                    case 6 -> simularFallaCritica();
                    case 7 -> {
                        salir = true;
                        System.out.println("Saliendo del programa, chao!");
                    }
                }
            }
            catch (IllegalStateException e){
                System.out.println("ERROR: " + e.getMessage());
            } 
    }
}


public void configInicial(){
    if (unidad != null){
        System.out.println("Ya existe una unidad configurada");
        return;
        // estaria bien ponerle una opcion de "reemplazar por una nueva" o algo por el estilo
    }

    System.out.println(" === Datos para una nueva Unidad Central de Control Industrial ===");
    String sector = leerTexto("Ingrese el sector (ejemplo: Sector A): ");
    int nave = leerEntero("Ingrese el numero de nave: ", 1, 999);
    double potenciaAux = leerDecimal("Ingrese la potencia de los sistemas auxiliares (W): ", 0, Double.MAX_VALUE);
    double limitePotencia = leerDecimal("Ingrese el limite de potencia electrica soportada (W): ", 1, Double.MAX_VALUE);
    String ubicacion = leerTexto("Ingrese la ubicacion geografica de la unidad: ");

    System.out.println();
    System.out.println(" === Datos para el brazo robotico ===");
    double velocidadNominal = leerDecimal("Ingrese la velocidad nominal: ", 0.1, Double.MAX_VALUE);
    double angulo = leerDecimal("Ingrese el angulo de extension (0 a 180): ", 0, 180);
    double horas = leerDecimal("Ingrese la cantidad de horas acumuladas: ", 0, Double.MAX_VALUE);

    System.out.println();
    System.out.println(" === Datos para el operador humano responsable de esta unidad ===");
    String ficha = leerTexto("Ingrese la ficha de empleado: ");
    String nombre = leerTexto("Ingrese el nombre completo de la persona: ");
    String correo = leerCorreo("Ingrese el correo del operador: ");

    unidad = new UCCI(sector, nave, "Activo", potenciaAux, limitePotencia, false, ubicacion, velocidadNominal, velocidadNominal, angulo, horas); // el false es lo de compensacion climatica
    unidad.setBitacora(bitacora); //agregacion
    unidad.setOperador(new OperadorHumano(ficha,nombre, correo));

    System.out.println("Unidad" + unidad +"configurada correctamente:");



}

public void ejecutarMuestreo(){
    exigirUnidad();
    if (unidad.getBrazo() == null) {
        throw new IllegalStateException("No hay un brazo mecanico disponible para esta unidad. La unidad esta en falla critica.");
    }

    System.out.println("Estado actual del brazo: "+ unidad.consultarDisponibilidadBrazo());
    double ph = leerDecimal("Ingrese el nivel de pH medido (0 a 14): ", 0, 14);
    double conductividad = leerDecimal("Ingrese la conductividad medida en dS/m (0 a 20): ", 0, 20);
    double duracion = leerDecimal("Ingrese la duracion de la maniobra en horas: ", 0.01, Double.MAX_VALUE);

    reporte = unidad.ejecutarMuestreo(ph, conductividad, duracion);
    System.out.println(reporte); 
    System.out.println("Costo en W de la maniobra: " + reporte.getGastoEnergetico() + " W");
    System.out.println("Estado actual de la unidad:" + unidad.getEstado());

}
 
public void sincronizarClima(){
    exigirUnidad();
    System.out.println(unidad.procesarDatosClima(clima));
}


public void registrarYAnalizar(){
    exigirUnidad();
    if (reporte == null){
        throw new IllegalStateException("No hay un reporte de muestreo disponible. Por favor, ejecute un ciclo de muestreo antes de registrar y analizar.");
    }

    System.out.println("Analisis del reporte" + reporte.resumenAnomalia());
    if (reporte.anomaliaCritica()){
        System.out.println("Se ha detectado una anomalia, se envia alerta al operador " + unidad.getOperador().getNombreCompleto() + " a la direccion de correo "+ unidad.getOperador().getCorreo() );   
    }
    unidad.registrarReporte(reporte, correo);
}

public void verBitacora(){
    System.out.println(" === Estado de la bitacora ===");
    ArrayList<ReporteMuestreo> reportes = bitacora.getReportes();
    if (reportes.isEmpty()){
        System.out.println("No hay reportes en la bitacora");
        return;
    }
    System.out.println(bitacora.generarResumen());
    System.out.println("Porcentaje de reportes con anomalias: " + bitacora.porcentajeLecturasAnomalas() + "%");
    for (int i = 0; i < reportes.size(); i++){
        System.out.println(i + ". " + reportes.get(i));
    }
}


   public void simularFallaCritica(){
       exigirUnidad();
       if (unidad.getBrazo() == null) {
           throw new IllegalStateException("La unidad ya se encuentra en falla critica.");
       }

       System.out.println(" === Simulacion de falla critica ===");
       System.out.println("ANTES de la falla:");
       System.out.println("  Brazo existe: " + (unidad.getBrazo() != null));
       System.out.println("  Reportes en la bitacora: " + bitacora.getReportes().size());

       unidad.fallaCritica();

       System.out.println("DESPUES de la falla:");
       System.out.println("  Estado de la unidad: " + unidad.getEstado());
       System.out.println("  Brazo existe: " + (unidad.getBrazo() != null));
       System.out.println("  Reportes en la bitacora: " + bitacora.getReportes().size() + " (siguen intactos)");
   }


}