package cr.ac.tec.ecohydro.modelo;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class BitacoraHistoricaTest {

    @Test
    void porcentajeConBitacoraVaciaDevuelveCero() {
        BitacoraHistorica bitacora = new BitacoraHistorica();
        assertEquals(0.0, bitacora.porcentajeLecturasAnomalas());
    }

    @Test
    void porcentajeUnAnomaloYUnoNormalDevuelveCincuenta() {
        BitacoraHistorica bitacora = new BitacoraHistorica();
        bitacora.agregarReporte(new ReporteMuestreo(7.0, 1.5, 10.0));
        bitacora.agregarReporte(new ReporteMuestreo(6.0, 1.5, 10.0));
        assertEquals(50.0, bitacora.porcentajeLecturasAnomalas(), 0.01);
    }
}
