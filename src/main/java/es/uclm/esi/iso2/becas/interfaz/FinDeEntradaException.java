package es.uclm.esi.iso2.becas.interfaz;

public class FinDeEntradaException extends RuntimeException {
    public FinDeEntradaException() {
        super("No hay más datos de entrada.");
    }
}