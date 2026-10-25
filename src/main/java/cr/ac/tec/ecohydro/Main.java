package cr.ac.tec.ecohydro;

import cr.ac.tec.ecohydro.servicio.ServicioClima;
import cr.ac.tec.ecohydro.servicio.ServicioCorreo;
import cr.ac.tec.ecohydro.ui.Menu;

public class Main{
    public static void main(String[] args) {

        ServicioClima clima = new ServicioClima(); // "POR LA GRAN PUTA, DEJENME TRABAJAR" 
        // LOL YA ESTA XD
        ServicioCorreo correo = null; // es solo para testear

        Menu test = new Menu(clima,correo);
        test.inicio();
    }
}