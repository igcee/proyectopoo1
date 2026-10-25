package cr.ac.tec.ecohydro.ui;

import java.util.ArrayList;
import java.util.InputMismatchException; // hay q hacerle import a este chunche para el catch del menu
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
                    //case 3 -> sincronizarClima();
                    case 4 -> registrarYAnalizar();
                    case 5 -> verBitacora();
                    //case 6 -> simularFallaCritica(); // nombres temporales, pq habia q poner algo
                    case 7 -> {
                        salir = true;
                        System.out.println("Saliendo del programa, chao!");
                    }
                }
            }
            catch (InputMismatchException e){ // InputMismatchException es para cuando se espera int pero se introduce txt
                System.out.println("ERROR: el programa no admite esa entrada, solo se admiten numeros entre 1 y 7");
            } 
            // aca iria un catch(IllegalStateException e), para cuando se intenta cualquier opcion que no sea la 1 cuando no existe una unidad
    }
}


public void configInicial(){
    if (unidad != null){
        System.out.println("Ya existe una unidad configurada");
        return;
        // estaria bien ponerle una opcion de "reemplazar por una nueva" o algo por el estilo
    }

    System.out.println(" === Datos para una nueva Unidad Central de Control Industrial ===");
    System.out.println("Ingrese el sector (String): ");
    String sector = entrada.next();
    System.out.println("Ingrese el numero de nave (Int): ");
    int nave = entrada.nextInt();
    System.out.println("Ingrese la potencia de los sistemas auxiliares (Double): ");
    double potenciaAux = entrada.nextDouble(); // nextDouble es lo mismo de nextInt pero para los doubles
    System.out.println("Ingrese el limite de potencia electrica soportada (Double): ");
    double limitePotencia = entrada.nextDouble();
    System.out.println("Ingrese la ubicacion geografica de la unidad (String): ");
    String ubicacion = entrada.next();

    System.out.println();
    System.out.println(" === Datos para el brazo robotico ===");
    System.out.println("Ingrese la velocidad nominal (Double): ");
    double velocidadNominal = entrada.nextDouble();
    System.out.println("Ingrese el angulo de extension (Double): ");
    double angulo = entrada.nextDouble();
    System.out.println("Ingrese la cantidad de horas acumuladas (Double): ");
    double horas = entrada.nextDouble();

    System.out.println();
    System.out.println(" === Datos para el operador humano responsable de esta unidad ===");
    System.out.println("Ingrese la ficha (String): ");
    String ficha = entrada.next();
    System.out.println("Ingrese el nombre completo de la persona (String): ");
    String nombre = entrada.next();
    System.out.println("Ingrese el correo del operador (String): ");
    String correo = entrada.next();

    unidad = new UCCI(sector, nave, "Activo", potenciaAux, limitePotencia, false, ubicacion, velocidadNominal, velocidadNominal, angulo, horas); // el false es lo de compensacion climatica
    unidad.setBitacora(bitacora); //agregacion
    unidad.setOperador(new OperadorHumano(ficha,nombre, correo));

    System.out.println("Unidad" + unidad +"configurada correctamente:");



}

public void ejecutarMuestreo(){
    System.out.println("Estado actual del brazo: "+ unidad.consultarDisponibilidadBrazo());
    System.out.println("Ingrese el nivel de ph medido entre 0 y 14 (Double): "); // hay q meter verificacion  0 < entrada < 14
    Double ph = entrada.nextDouble();
    System.out.println("Ingrese el nivel de conductividad medida (Double): "); 
    Double conductividad = entrada.nextDouble();
    System.out.println("Ingrese la duracion que tomara la maniobra (Double): ");
    Double duracion = entrada.nextDouble();

    ReporteMuestreo reporte = unidad.ejecutarMuestreo(ph, conductividad, duracion);
    System.out.println(reporte); 
    System.out.println("Costo en W de la maniobra: " + reporte.getGastoEnergetico() + " W");
    System.out.println("Estado actual de la unidad:" + unidad.getEstado());

}
/* 
public void sincronizarClima(){
    //case 3
}
*/

public void registrarYAnalizar(){
    System.out.println("Analisis del reporte" + reporte.resumenAnomalia());
    if (reporte.anomaliaCritica()){
        System.out.println("Se ha detectado una anomalia, se envia alerta al operador " + unidad.getOperador() + " a la direccion de correo "+ correo.getCorreo() );   
    }
    unidad.registrarReporte(reporte, correo);
}

public void verBitacora(){
    System.out.println(" === Estado de la bitacora ===");
    System.out.println(bitacora.generarResumen());
    System.out.println("Porcentaje de reportes con anomalias:" + bitacora.porcentajeLecturasAnomalas() + "%");
    ArrayList<ReporteMuestreo> reportes = bitacora.getReportes();
    if (reportes.isEmpty()){
        System.out.println("No hay reportes en la bitacora");
        return;
    }
    else{

        for (int i = 0; i< reportes.size(); i++){
            System.out.println(i + ". "+ reportes.get(i));
        }
    }

}


/*
public void simularFallaCritica(){

}

*/


}