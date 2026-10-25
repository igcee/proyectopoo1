package cr.ac.tec.ecohydro.modelo;
import java.util.ArrayList;

public class BitacoraHistorica {
    private ArrayList<ReporteMuestreo> reportes;


    public BitacoraHistorica(){
        this.reportes = new ArrayList<>();

    }

    public void agregarReporte(ReporteMuestreo reportito){
        // bitacora mantiene coleccion ordenada de todos los reportes
        reportes.add(reportito);
    }

    public double porcentajeLecturasAnomalas(){
        // evalua totalidad de reportes, determina porcentaje de anomalas
        int anom = 0;

        if (reportes.size() == 0){
            return 0.0;
        }

        for (ReporteMuestreo reportito:reportes){ // saca cantidad de reportes con anomalias
            if (reportito.anomaliaCritica()){
                anom ++;
            }
        }

        int cantidadreportes = reportes.size();
        return anom * 100.00 / cantidadreportes; // si el resultado sale mal es culpa de la formula q busque, no culpa mia

        
    }

    public String generarResumen(){
        // segun enunciado "Se requiere la capacidad de generar un resumen de síntesis en texto que muestre el historial acumulado, detallando cuántas muestras han cumplido los parámetros y cuántas han presentado desviaciones críticas."
        int total = reportes.size();
        int anomalas = 0;
        for (ReporteMuestreo reportito:reportes){ // saca cantidad de reportes con anomalias
            if (reportito.anomaliaCritica()){
                anomalas ++;
            }
        }
        int normales = total - anomalas;

        return "Miniresumen de bitacora: " + total + " reportes guardados. " + normales + " dentro de los parametros y " + anomalas + " con desviaciones criticas";
    }

    public void borrarBitacora(){
        // cuando se borra la bitacora NO se eliminan los reportes, esos siguen existiendo
        reportes.clear();
        // estoy asumiendo q el enunciado pide borrado de toda la bitacora, no de reportes ni nada por el estilo
        // PORQUE ESTE ENUNCIADO NO EXPLICA NADA, CADA VES QUE LO RELEO TERMINO MAS PERDIDO

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
    
     @Override
    public String toString() {
        return "BitacoraHistorica{reportes="+ reportes.size()+"}";
    }


}
