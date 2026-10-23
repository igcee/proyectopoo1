package cr.ac.tec.ecohydro.servicio;
// aca tambien los chunches de la api q hay q buscar

public class ServicioClima {
    private String ubicacion;


    public double consultarTemperatura(String ubicacion){
        // saca de api
        return 0.0; // para q deje compilar
    }

    public double obtenerHumedad(String ubicacion){
        // saca de api tambien
        return 0.0; // para q deje compilar
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }
    
    
}
