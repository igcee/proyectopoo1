package cr.ac.tec.ecohydro.modelo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import cr.ac.tec.ecohydro.servicio.ServicioClimaFijo;

class UCCITest {

    private UCCI ucciDePrueba() {
        return new UCCI("A", 1, "Activo", 100.0, 1000.0, false,
                "Cartago", 10.0, 10.0, 5.0, 10.0);
    }

    @Test
    void ejecutarMuestreoConCompensacionMultiplicaConductividad() {
        UCCI unidad = ucciDePrueba();
        unidad.setCompensacionClimatica(true);
        ReporteMuestreo reporte = unidad.ejecutarMuestreo(6.0, 2.0, 1.0);
        assertEquals(2.1, reporte.getConductividad(), 0.001);
    }

    @Test
    void ejecutarMuestreoSinCompensacionNoModificaConductividad() {
        UCCI unidad = ucciDePrueba();
        unidad.setCompensacionClimatica(false);
        ReporteMuestreo reporte = unidad.ejecutarMuestreo(6.0, 2.0, 1.0);
        assertEquals(2.0, reporte.getConductividad(), 0.001);
    }

    @Test
    void procesarDatosClimaTemperaturaMayorA32ActivaCompensacion() {
        UCCI unidad = ucciDePrueba();
        String mensaje = unidad.procesarDatosClima(new ServicioClimaFijo(new double[] {33, 60}));
        assertTrue(unidad.isCompensacionClimatica());
        assertTrue(mensaje.contains("Compensacion"));
    }

    @Test
    void procesarDatosClimaTemperatura32NoActivaCompensacion() {
        UCCI unidad = ucciDePrueba();
        unidad.procesarDatosClima(new ServicioClimaFijo(new double[] {32, 60}));
        assertFalse(unidad.isCompensacionClimatica());
    }

    @Test
    void procesarDatosClimaConsultaFallidaNoCambiaCompensacion() {
        UCCI unidad = ucciDePrueba();
        unidad.setCompensacionClimatica(true);
        String mensaje = unidad.procesarDatosClima(new ServicioClimaFijo(null));
        assertTrue(mensaje.contains("No se pudo"));
        assertTrue(unidad.isCompensacionClimatica());
    }
}
