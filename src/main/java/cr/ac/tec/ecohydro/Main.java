package cr.ac.tec.ecohydro;

import cr.ac.tec.ecohydro.servicio.ServicioClima;
import cr.ac.tec.ecohydro.servicio.ServicioCorreo;
import cr.ac.tec.ecohydro.ui.Menu;

public class Main{
    public static void main(String[] args) {

        ServicioClima clima = new ServicioClima(); // "POR LA GRAN PUTA, DEJENME TRABAJAR" 
        // LOL YA ESTA XD
        ServicioCorreo correo = new ServicioCorreo(2525, "sandbox.smtp.mailtrap.io", "smtp@mailtrap.io", "c875893e4343119ca6be592f282c8455", "iangabrielcastroesquivel@gmail.com" ); // ese correo no obviamente. ESTO SIGUE SIENDO DE TEST, ES SOLO PARA Q COMPILE

        Menu test = new Menu(clima,correo);
        test.inicio();
    }
}