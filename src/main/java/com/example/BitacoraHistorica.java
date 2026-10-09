package com.example;
import java.utils.ArrayList;

public class BitacoraHistorica {
    private ArrayList<ReporteMuestreo> reportes;


    public BitacoraHistorica(ReporteMuestreo reporte){

    }

    public void agregarReporte(ReporteMuestreo reporte){
        // bitacora mantiene coleccion ordenada de todos los reportes

    }

    public double porcentajeLecturasAnomalas(){
        // evalua totalidad de reportes, determina porcentaje de anomalas
        
    }

    public String generarResumen(){
        //"Se requiere la capacidad de generar un resumen de síntesis en texto que muestre el historial acumulado, detallando cuántas muestras han cumplido los parámetros y cuántas han presentado desviaciones críticas."

    }

    public void borrarBitacora(){
        // cuando se borra la bitacora NO se eliminan los reportes, esos siguen existiendo

    }






    public ArrayList<ReporteMuestreo> getReportes() {
        return reportes;
    }


    /* 
    public void setReportes(ArrayList<ReporteMuestreo> reportes) {
        this.reportes = reportes;
    }
    */

    // por lo q he investigado, en teoria los ArrayList no ocupan setters (?). Igual lo dejo aca comentado por lo de q todo ocupa getters y setters y noseque
    
}
