package es.uclm.esi.iso2.becas.dominio;

import java.math.BigDecimal;

public final class Estudiante {
    private final double notaMedia;
    private final BigDecimal rentaFamiliar;
    private final double distanciaCampusKm;
    private final int gradoDiscapacidad;
    private final int numeroHermanos;

    public Estudiante(double notaMedia, BigDecimal rentaFamiliar, double distanciaCampusKm,
                      int gradoDiscapacidad, int numeroHermanos) {
        if (notaMedia < 0.0 || notaMedia > 10.0) {
            throw new DatosEstudianteInvalidosException("La nota media debe estar entre 0 y 10.");
        }
        if (rentaFamiliar == null || rentaFamiliar.signum() < 0) {
            throw new DatosEstudianteInvalidosException("La renta familiar no puede ser negativa.");
        }
        if (distanciaCampusKm < 0) {
            throw new DatosEstudianteInvalidosException("La distancia no puede ser negativa.");
        }
        if (gradoDiscapacidad < 0 || gradoDiscapacidad > 100) {
            throw new DatosEstudianteInvalidosException("El grado de discapacidad debe estar entre 0 y 100.");
        }
        if (numeroHermanos < 0) {
            throw new DatosEstudianteInvalidosException("El número de hermanos no puede ser negativo.");
        }

        this.notaMedia = notaMedia;
        this.rentaFamiliar = rentaFamiliar;
        this.distanciaCampusKm = distanciaCampusKm;
        this.gradoDiscapacidad = gradoDiscapacidad;
        this.numeroHermanos = numeroHermanos;
    }

    public double getNotaMedia() { return notaMedia; }
    public BigDecimal getRentaFamiliar() { return rentaFamiliar; }
    public double getDistanciaCampusKm() { return distanciaCampusKm; }
    public int getGradoDiscapacidad() { return gradoDiscapacidad; }
    public int getNumeroHermanos() { return numeroHermanos; }
}