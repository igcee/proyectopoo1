// sacar los chunches de la api o lo q sea 
package cr.ac.tec.ecohydro.servicio;

import cr.ac.tec.ecohydro.modelo.OperadorHumano;
import cr.ac.tec.ecohydro.modelo.ReporteMuestreo;

public class ServicioCorreo{
    private int puerto;
    private String servidorSmtp;
    private String correo;


    public boolean enviarAlertaCritica(OperadorHumano operador, ReporteMuestreo reporte){
        // este chunche tiene q enviar un correo al operador humano con el reporte de anomalia critica
        return true; // para q me deje compilar :P
    }

    public int getPuerto() {
        return puerto;
    }

    public void setPuerto(int puerto) {
        this.puerto = puerto;
    }

    public String getServidorSmtp() {
        return servidorSmtp;
    }

    public void setServidorSmtp(String servidorSmtp) {
        this.servidorSmtp = servidorSmtp;
    }

    public String getCorreo(){
        return correo;
    }

    public void setCorreo(String correo){
        this.correo = correo;
    }


}