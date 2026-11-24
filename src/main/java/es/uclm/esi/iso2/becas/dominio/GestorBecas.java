package es.uclm.esi.iso2.becas.dominio;

import java.math.BigDecimal;

public final class GestorBecas {
    private static final BigDecimal RENTA_EXCELENCIA = new BigDecimal("30000");
    private static final BigDecimal RENTA_RESIDENCIA = new BigDecimal("20000");
    private static final BigDecimal RENTA_FAMILIA = new BigDecimal("15000");

    public TipoBeca determinarBeca(Estudiante estudiante) {
        if (estudiante == null) {
            throw new DatosEstudianteInvalidosException("El estudiante no puede ser nulo.");
        }

        // 1. Beca de Excelencia
        if (estudiante.getNotaMedia() >= 8.5 
                && estudiante.getRentaFamiliar().compareTo(RENTA_EXCELENCIA) < 0) {
            return TipoBeca.EXCELENCIA;
        }

        // 2. Beca de Residencia
        if (estudiante.getNotaMedia() >= 6.5 && estudiante.getNotaMedia() <= 8.4
                && estudiante.getRentaFamiliar().compareTo(RENTA_RESIDENCIA) < 0
                && estudiante.getDistanciaCampusKm() > 50.0) {
            return TipoBeca.RESIDENCIA;
        }

        // 3. Beca de Familia Numerosa
        if (estudiante.getNotaMedia() >= 5.0
                && estudiante.getRentaFamiliar().compareTo(RENTA_FAMILIA) < 0
                && estudiante.getNumeroHermanos() >= 3) {
            return TipoBeca.FAMILIA_NUMEROSA;
        }

        // 4. Beca de Inclusión
        if (estudiante.getGradoDiscapacidad() >= 33
                && estudiante.getNotaMedia() >= 5.0) {
            return TipoBeca.INCLUSION;
        }

        return TipoBeca.NINGUNA;
    }
}