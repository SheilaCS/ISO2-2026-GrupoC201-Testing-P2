package es.uclm.esi.iso2.becas.interfaz;

import es.uclm.esi.iso2.becas.dominio.GestorBecas;

public class Main {
    public static void main(String[] args) {
        new AplicacionInterfaz(
                new LectorInterfaz(System.in),
                new EscritorInterfaz(System.out),
                new GestorBecas()
        ).ejecutar();
    }
}