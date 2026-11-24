package es.uclm.esi.iso2.becas.interfaz;

import es.uclm.esi.iso2.becas.dominio.DatosEstudianteInvalidosException;
import es.uclm.esi.iso2.becas.dominio.Estudiante;
import es.uclm.esi.iso2.becas.dominio.GestorBecas;
import es.uclm.esi.iso2.becas.dominio.TipoBeca;

import java.math.BigDecimal;
import java.util.Objects;

public class AplicacionInterfaz {
    private final ILectorDatos lector;
    private final IEscritorPantalla escritor;
    private final GestorBecas gestor;

    public AplicacionInterfaz(ILectorDatos lector, IEscritorPantalla escritor, GestorBecas gestor) {
        this.lector = Objects.requireNonNull(lector);
        this.escritor = Objects.requireNonNull(escritor);
        this.gestor = Objects.requireNonNull(gestor);
    }

    public void ejecutar() {
        escritor.mostrar("Sistema de Recomendación de Becas Universitarias");
        try {
            boolean otra;
            do {
                evaluarEstudiante();
                otra = preguntarOtra();
            } while (otra);
        } catch (FinDeEntradaException e) {
            escritor.mostrar("Fin de la entrada de datos.");
        }
        escritor.mostrar("Hasta pronto.");
    }

    private void evaluarEstudiante() {
        escritor.mostrar("--- Introduce los datos del estudiante ---");
        try {
            double nota = pedirDouble("Nota media (0-10): ");
            BigDecimal renta = pedirBigDecimal("Renta familiar anual (€): ");
            double distancia = pedirDouble("Distancia al campus (km): ");
            int discapacidad = pedirEntero("Grado de discapacidad (0-100%): ");
            int hermanos = pedirEntero("Número de hermanos: ");

            Estudiante estudiante = new Estudiante(nota, renta, distancia, discapacidad, hermanos);
            TipoBeca beca = gestor.determinarBeca(estudiante);
            escritor.mostrar("Resultado: " + beca.getDescripcion());

        } catch (DatosEstudianteInvalidosException | NumberFormatException e) {
            escritor.mostrarError(e.getMessage());
        }
    }

    private double pedirDouble(String etiqueta) {
        while (true) {
            escritor.mostrar(etiqueta);
            try {
                return Double.parseDouble(lector.leerLinea().trim().replace(",", "."));
            } catch (NumberFormatException e) {
                escritor.mostrarError("Introduce un número válido.");
            }
        }
    }

    private BigDecimal pedirBigDecimal(String etiqueta) {
        while (true) {
            escritor.mostrar(etiqueta);
            try {
                return new BigDecimal(lector.leerLinea().trim().replace(",", "."));
            } catch (NumberFormatException e) {
                escritor.mostrarError("Introduce un número válido.");
            }
        }
    }

    private int pedirEntero(String etiqueta) {
        while (true) {
            escritor.mostrar(etiqueta);
            try {
                return Integer.parseInt(lector.leerLinea().trim());
            } catch (NumberFormatException e) {
                escritor.mostrarError("Introduce un número entero válido.");
            }
        }
    }

    private boolean preguntarOtra() {
        while (true) {
            escritor.mostrar("¿Evaluar otro estudiante? (s/n)");
            String respuesta = lector.leerLinea().trim();
            if (respuesta.equalsIgnoreCase("s")) return true;
            if (respuesta.equalsIgnoreCase("n")) return false;
            escritor.mostrarError("Responda s o n.");
        }
    }
}