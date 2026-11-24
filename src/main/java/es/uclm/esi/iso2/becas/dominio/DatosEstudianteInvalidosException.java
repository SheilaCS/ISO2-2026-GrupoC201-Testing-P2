package es.uclm.esi.iso2.becas.dominio;

public class DatosEstudianteInvalidosException extends RuntimeException {
    public DatosEstudianteInvalidosException(String mensaje) {
        super(mensaje);
    }
}