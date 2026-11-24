package es.uclm.esi.iso2.becas.dominio;

public enum TipoBeca {
    EXCELENCIA("Beca de Excelencia (exención total de tasas + 1.000 €)"),
    RESIDENCIA("Beca de Residencia (exención total + ayuda de alojamiento)"),
    FAMILIA_NUMEROSA("Beca de Familia Numerosa (exención total de tasas)"),
    INCLUSION("Beca de Inclusión (exención total + apoyo tecnológico)"),
    NINGUNA("No cumple los requisitos para ninguna beca");

    private final String descripcion;

    TipoBeca(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getDescripcion() {
        return descripcion;
    }
}