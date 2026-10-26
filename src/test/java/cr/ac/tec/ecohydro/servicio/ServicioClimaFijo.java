package cr.ac.tec.ecohydro.servicio;

public class ServicioClimaFijo extends ServicioClima {
    private final double[] datos;

    public ServicioClimaFijo(double[] datos) {
        this.datos = datos;
    }

    @Override
    public double[] consultarClima(String ubicacion) {
        return datos;
    }
}
