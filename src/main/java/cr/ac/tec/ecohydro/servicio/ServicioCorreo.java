package cr.ac.tec.ecohydro.servicio;

import cr.ac.tec.ecohydro.modelo.OperadorHumano;
import cr.ac.tec.ecohydro.modelo.ReporteMuestreo;


import java.util.ArrayList;
import java.util.Properties;
import java.time.format.DateTimeFormatter;

//chunches api jakarta
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.Session;
import jakarta.mail.Authenticator;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;


public class ServicioCorreo{
    private int puerto;
    private String servidorSmtp;
    private String correo;

    private String usuario;
    private String clave;
    //private String destino = "iangabrielcastroesquivel@gmail.com"; // era para tests

    public ServicioCorreo(int puerto, String servidorSmtp, String usuario, String clave, String correo) {
        this.puerto = puerto;
        this.servidorSmtp = servidorSmtp;
        this.usuario = usuario;
        this.clave = clave;
        //this.destino = destino;
        this.correo = correo;
    }

    public boolean enviarAlertaCritica(OperadorHumano operador, ReporteMuestreo reporte, String ubicacion){ // le meti q tambien reciba de param la ubi
        // este chunche tiene q enviar un correo al operador humano con el reporte de anomalia critica
        if (operador == null || reporte == null){
            throw new IllegalArgumentException("No se puede enviar la alerta sin un operador y un reporte");
        }
        else if (operador.getCorreo() == null){ // hay q meter en operador/menu/aca verificaciones de si correo es valido
            throw new IllegalArgumentException("No se puede enviar la alerta a un operador sin correo asignado");
        }
        else if (ubicacion == null){
            throw new IllegalArgumentException("No se puede enviar la alerta sin la ubicacion de la unidad");
        }
        else if (servidorSmtp == null || usuario == null || clave == null || correo == null){
            //System.out.println("test sirve 3");
            throw new IllegalStateException("El servicio de correo no esta configurado completamente"); // no deberia de salir esto normalmente, solo para asegurar tests
        }
        else{
            //Transport transp = null; // transport agarra un obj Message y lo manda al servidor del chunche de correos
            // ^ RECORDATORIO: lo puse dsps de lo de armar MimeMessage
            Properties prop = new Properties();
            prop.put("mail.smtp.auth", "true");
            prop.put("mail.smtp.starttls.enable", "true");
            prop.put("mail.smtp.host", this.servidorSmtp); //no servia sin smtp, lo puse asi para no tener q cambiar enviarAlertaCritica en los otros modulos ni nada
            prop.put("mail.smtp.port", String.valueOf(this.puerto));

            // tiempos de espera, se ocupan pq sino el programa podria quedar congelado
            prop.put("mail.smtp.connectiontimeout", "5000");
            prop.put("mail.smtp.timeout", "10000");
            prop.put("mail.smtp.writetimeout", "10000");

            // si se ocupaba, sino el jakarta no tiene como autenficiar las credenciales
            Session session = Session.getInstance(prop, new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(usuario, clave);
                    //System.out.println("test sirve 2");
                }

            //System.out.println("test sirve 5");
            });
            try{
                DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"); //DateTimeFormatter es para formatear fechas y horas, como de LocalDate x ejemplo. Es para cuando quiera meter hora y fecha en cuerpo de corrreo
                String asunto = ("ALERTA CRITICA: anomalia quimica en " + ubicacion);
                String cuerpo = "Estimado/a/e " + operador.getNombreCompleto() + ",\n\n"
                              + "Se ha detectado una anomalia critica en el sistema,\n"
                              + "Ubicacion de la unidad: " + ubicacion +  "\n"
                              + "Fecha y hora del incidente: " + reporte.getFechaMedicion().format(formato) + "\n"
                              + "Ph medido: " + reporte.getPh() + "\n"
                              + "Conductividad medida: " + reporte.getConductividad() + " dS/m" + "\n\n"
                              + "Detalle: " + reporte.resumenAnomalia() + "\n\n"
                              + "Por favor, tome las medidas correspondientes de inmediato o sufra las consecuencias del sistema corporativo.";

                MimeMessage msj = new MimeMessage(session);
                msj.setFrom(new InternetAddress(this.correo));
                msj.setRecipient(Message.RecipientType.TO, new InternetAddress(operador.getCorreo()));
                msj.setSubject(asunto);
                msj.setText(cuerpo);
                Transport.send(msj);
                //System.out.println("test sirve 4");
                return true;
            }
            catch(MessagingException e){
                // este exception es para cuando: no internet, timeout, credenciales rechazadas, mala direccion, o cuando se le antoje invnetarse nuevos errores :/
                throw new IllegalStateException("Por alguna razon no se pudo enviar correo");
                //return false;
            }
            
            
        }


        
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