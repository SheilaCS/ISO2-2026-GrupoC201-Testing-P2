package es.uclm.esi.iso2.becas.interfaz;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class LectorInterfaz implements ILectorDatos {
    private final Scanner scanner;

    public LectorInterfaz(InputStream entrada) {
        this.scanner = new Scanner(entrada, StandardCharsets.UTF_8);
    }

    @Override
    public String leerLinea() {
        if (!scanner.hasNextLine()) {
            throw new FinDeEntradaException();
        }
        return scanner.nextLine();
    }
}