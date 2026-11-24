# ISO2-2026-GrupoC201-Testing-P2

Problema 2 de la segunda parte del trabajo teórico de Ingeniería del Software II: **sistema de recomendación de becas universitarias**.

Enunciado: [docs/T2_ESP_2026-27v1.1.pdf](docs/T2_ESP_2026-27v1.1.pdf)

## Reparto por pasos

| Paso | Qué se hace | Pareja |
|---|---|---|
| 1 | Implementación del programa (este proyecto) | Pareja 2: Yasmine Hamzat y Miriam Gómez |
| 2 | Diseño de casos de prueba (ejercicio teórico) | Pareja 3: Mateo Bretaño, Juan Manuel Ferrero y Tomás Rodríguez |
| 3 | Implementación de las pruebas en JUnit | Pareja 1: Sheila Calzado y Celia Barba |

## Requisitos

- Java 17
- Maven 3.9 o superior

## Compilar y probar

```
mvn clean verify
```

El informe de cobertura queda en `target/site/jacoco/index.html`. La cobertura mínima exigida es del 80 % de líneas.

## Ejecutar

```
mvn clean compile
java -cp target/classes es.uclm.esi.iso2.becas.interfaz.Main
```


## Estructura

- `src/main/java/.../dominio`: lógica de negocio (sin entrada ni salida de datos).
- `src/main/java/.../interfaz`: interfaz de línea de comandos.
- `src/test/java`: pruebas (las implementa la pareja 1 en el Paso 3).
- `docs`: enunciado.

## Decisiones de diseño


### Reglas de negocio

| Decisión | Elección | Justificación |
|---|---|---|
| Tipos de datos numéricos | Notas y distancias en `double`; renta familiar en `BigDecimal` | `BigDecimal` evita problemas de precisión con los decimales de los euros en los límites exactos de renta. |
| Orden de evaluación | Se evalúan en el orden del enunciado: Excelencia, Residencia, Familia Numerosa, Inclusión | Si un estudiante cumple los requisitos de varias becas, se le asigna la primera que aparezca en el enunciado para evitar ambigüedades. |
| Límites exactos de nota | Se usan mayor/menor o igual (>= / <=) según indique el texto | El enunciado especifica "mayor o igual a 8,5" o "entre 6,5 y 8,4", por lo que se implementan exactamente así. |
| Límite de distancia | Estrictamente mayor a 50 km | El enunciado indica "supera los 50 km", por lo que exactamente 50 km no da derecho a la Beca de Residencia. |
| Hermanos y discapacidad | Hermanos >= 3 y Discapacidad >= 33% | El enunciado indica "3 o más hermanos" y "igual o superior al 33%". |

Ejemplos de asignación con las reglas por defecto:

| Nota | Renta (€) | Distancia (km) | Discapacidad (%) | Hermanos | Beca asignada |
|---|---|---|---|---|---|
| 9,0 | 25.000 | 10 | 0 | 0 | Excelencia |
| 7,5 | 18.000 | 60 | 0 | 0 | Residencia |
| 6,0 | 12.000 | 10 | 0 | 3 | Familia Numerosa |
| 5,5 | 40.000 | 10 | 35 | 0 | Inclusión |
| 7,0 | 40.000 | 10 | 0 | 0 | Ninguna |

### Datos y validación

- **Tipo de datos:** Se usa `double` para la nota media, distancia y el porcentaje de discapacidad. Se usa `BigDecimal` para la renta familiar para comparar con exactitud matemática en los umbrales (30.000, 20.000 y 15.000).
- **Validación en el constructor:** `Estudiante` es inmutable y comprueba sus datos al crearse. Un `Estudiante` que existe siempre es correcto.
- **Excepciones propias, no comprobadas:** `DatosEstudianteInvalidosException` para cuando los datos introducidos están fuera de rango (por ejemplo, nota negativa, renta nula o distancia negativa).
- **Método principal:** `GestorBecas.determinarBeca(Estudiante)`, que devuelve un enumerado `TipoBeca`.
- **Sin dependencias externas:** la lógica de negocio no depende de la interfaz, no usa `Scanner` ni `System.out`, lo que permite que las pruebas unitarias se hagan de forma limpia.

### Arquitectura

- **Dominio separado de la interfaz:** el paquete `dominio` no usa `System.in` ni `System.out` y no conoce el paquete `interfaz`.
- **Interfaces `ILectorDatos` e `IEscritorPantalla`:** el flujo (`AplicacionInterfaz`) depende de ellas y no de la consola. Las implementaciones reciben el `InputStream` y el `PrintStream`, así se puede probar con datos simulados.
- **Excepciones para el flujo de la interfaz:**
  - Un formato numérico incorrecto se captura y se vuelve a pedir el dato.
  - Si el dominio rechaza los datos del estudiante, se muestra el mensaje de error y se termina la evaluación de ese estudiante.
  - Si se acaba la entrada de datos, `FinDeEntradaException` cierra el programa sin error.

### Limitaciones conocidas

- No se tiene en cuenta la Comunidad Autónoma ni otras variables externas que podrían afectar a las becas, solo los datos aportados en el enunciado.
- Los datos no se guardan en base de datos: cada ejecución evalúa a un estudiante de forma aislada.

