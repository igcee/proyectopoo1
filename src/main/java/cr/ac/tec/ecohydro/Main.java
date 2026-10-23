package cr.ac.tec.ecohydro;

import cr.ac.tec.ecohydro.servicio.ServicioClima;
import cr.ac.tec.ecohydro.servicio.ServicioCorreo;
import cr.ac.tec.ecohydro.ui.Menu;

public class Main{
    public static void main(String[] args) {

        ServicioClima clima = null; // "POR LA GRAN PUTA, DEJENME TRABAJAR"
        ServicioCorreo correo = null; // es solo para testear

        Menu test = new Menu(clima,correo);
        test.inicio();
    }
}