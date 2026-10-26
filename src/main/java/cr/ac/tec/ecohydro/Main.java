package cr.ac.tec.ecohydro;
import java.util.Properties;
import java.io.InputStream;
import java.io.IOException;

import cr.ac.tec.ecohydro.servicio.ServicioClima;
import cr.ac.tec.ecohydro.servicio.ServicioCorreo;
import cr.ac.tec.ecohydro.ui.Menu;

public class Main{
    public static void main(String[] args) {

        ServicioClima clima = new ServicioClima();
        ServicioCorreo correo = null;
        Properties config = new Properties();
        try (InputStream entrada = Main.class.getResourceAsStream("/config.properties")) {
            if (entrada != null){
                config.load(entrada);
            }
        } catch (IOException e){
            System.out.println("No se pudo leer el archivo con las propiedades de correo");
        }
        String host = config.getProperty("smtp.host");
        String puertoTexto = config.getProperty("smtp.port");
        String usuario = config.getProperty("smtp.user");
        String clave = config.getProperty("smtp.pass");
        String remitente = config.getProperty("smtp.from");
        if (host == null || puertoTexto == null || usuario == null || clave == null || remitente == null){
            System.out.println("Servicio de correo no disponible (revise que exista src/main/resources/config.properties con smtp.host, smtp.port, smtp.user, smtp.pass y smtp.from)");
        }
        else{
            correo = new ServicioCorreo(Integer.parseInt(puertoTexto.trim()), host.trim(), usuario.trim(), clave.trim(), remitente.trim());
        }
        Menu test = new Menu(clima,correo);
        test.inicio();
    }
}