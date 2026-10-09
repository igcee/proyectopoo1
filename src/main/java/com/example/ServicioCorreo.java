// sacar los chunches de la api o lo q sea 

public class ServicioCorreo{
    private int puerto;
    private String servidorSmtp;


    public boolean enviarAlertaCritica(OperadorHumano operador, ReporteMuestreo reporte){
        // este chunche tiene q enviar un correo al operador humano con el reporte de anomalia critica
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

    


}