/*
 * Titulo: Rutas
 * Descripcion: Programa principal. Lee un archivo de entrada con:
 *   - Altas/bajas de aerolineas
 *   - Altas de ciudades
 *   - Declaraciones de rutas
 *   - Consultas de viajes
 *
 * Requisito del enunciado:
 * - El nombre del archivo de entrada debe solicitarse al ejecutar el programa.
 * - Declaraciones/rutas terminan con '.'
 * - Consultas terminan con '?'
 * - Despues de '.' o '?' puede venir basura (se ignora)
 * - Las lineas pueden venir en cualquier orden (se procesan en orden del archivo)
 *
 * Autores: Jared Eliezer Baldenegro Gomez; Jimer Orlando Diaz Murillo; Hannia Melendres Samaniego
 * Materia: Tecnologias de programacion
 * Profesor: Dra. Maria Lucia Barron Estrada
 * Fecha: 11-01-2026
 */
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class Rutas {

    private static final DateTimeFormatter FECHA_FMT =
            DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public static void main(String[] args) {

        System.out.println("===== PROYECTO RUTAS (TP MCC 2025) =====");

        String archivo = Keyboard.leerLinea("Nombre del archivo de entrada: ");

        ControladorGrafo controlador = new ControladorGrafo();
        ParserLineas parser = new ParserLineas(controlador);
        LectorArchivo lector = new LectorArchivo(parser);

        // Procesar el archivo y ejecutar consultas en el orden en que aparezcan
        lector.leerArchivo(archivo, (Consulta c) -> ejecutarConsulta(controlador, c));

        System.out.println("\n===== FIN =====");
    }

    private static void ejecutarConsulta(ControladorGrafo controlador, Consulta c) {

        if (c == null) return;

        System.out.println("\n----------------------------------------");System.out.println("CONSULTA: " + c.getCiudadOrigen()
                + " -> " + c.getCiudadDestino()
                + " " + c.getFechaSalida().format(FECHA_FMT)
                + (c.esIdaYVuelta() ? (" " + c.getFechaRegreso().format(FECHA_FMT)) : "")
        );

        // IDA
        imprimirTop5(controlador, c.getCiudadOrigen(), c.getCiudadDestino(), c.getFechaSalida());

        // VUELTA (si aplica)
        if (c.esIdaYVuelta()) {
            System.out.println("\n(VUELTA)");
            imprimirTop5(controlador, c.getCiudadDestino(), c.getCiudadOrigen(), c.getFechaRegreso());
        }
    }

    private static void imprimirTop5(ControladorGrafo controlador,
                                     String origen,
                                     String destino,
                                     LocalDate fechaSalida) {

        System.out.println("\nTOP 5 MAS BARATOS:");
        List<Viaje> baratos = controlador.top5MasBaratos(origen, destino, fechaSalida);
        imprimirLista(baratos);

        System.out.println("\nTOP 5 MENOR DURACION:");
        List<Viaje> cortos = controlador.top5MenorDuracion(origen, destino, fechaSalida);
        imprimirLista(cortos);
    }

    private static void imprimirLista(List<Viaje> viajes) {

        if (viajes == null || viajes.isEmpty()) {
            System.out.println("  (Sin itinerarios disponibles)");
            return;
        }

        int i = 1;
        for (Viaje v : viajes) {
            System.out.println("\n#" + i);
            System.out.println(v);
            i++;
            if (i > 5) break;
        }
    }
}
