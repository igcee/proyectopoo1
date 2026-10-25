package cr.ac.tec.ecohydro;

import cr.ac.tec.ecohydro.servicio.ServicioClima;
import cr.ac.tec.ecohydro.servicio.ServicioCorreo;
import cr.ac.tec.ecohydro.ui.Menu;

public class Main{
    public static void main(String[] args) {

        ServicioClima clima = new ServicioClima(); // "POR LA GRAN PUTA, DEJENME TRABAJAR" 
        // LOL YA ESTA XD
        ServicioCorreo correo = new ServicioCorreo(2525, "sandbox.smtp.mailtrap.io", "927d0a66b95462", "5326a55ff34d8c", "alertas@ecohydro.com" ); // le puse q de remitente sea uno inventado, es solo para q se muestre de "FROM"

        Menu test = new Menu(clima,correo);
        test.inicio();
    }
}