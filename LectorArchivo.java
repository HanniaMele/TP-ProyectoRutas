import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.FileInputStream;
import java.nio.charset.Charset;
import java.util.function.Consumer;

/**
 * Se encarga de leer el archivo de entrada,
 * limpiar las lineas y enviarlas al ParserLineas.
 *
 * Segun enunciado:
 * - Declaraciones/rutas terminan con '.'
 * - Consultas terminan con '?'
 * - Despues de '.' o '?' puede venir basura que se ignora
 * - Puede haber puntos decimales (1000.00), por eso usamos lastIndexOf('.') para declaraciones.
 */
public class LectorArchivo {

    private final ParserLineas parser;

    public LectorArchivo(ParserLineas parser) {
        this.parser = parser;
    }

    public void leerArchivo(String nombreArchivo, Consumer<Consulta> onConsulta) {

        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(new FileInputStream(nombreArchivo),
                        Charset.defaultCharset()))) {

            procesar(br, onConsulta);

        } catch (IOException e) {
            System.out.println("No se pudo leer el archivo: " + e.getMessage());
        }
    }

    /* =========================================================
       PROCESAMIENTO DE LINEAS
       ========================================================= */

    private void procesar(BufferedReader br, Consumer<Consulta> onConsulta) throws IOException {
        String linea;

        // El archivo del profe viene por secciones (.ciudades, .aerolineas, .rutas, .consultas)
        // y cada registro suele estar por linea. A veces las rutas no traen '.' al final si hay comentario.
        // Por eso usamos un modo/estado: dentro de .rutas y .consultas procesamos cada linea aun sin terminador.
        enum Seccion { NINGUNA, CIUDADES, AEROLINEAS, RUTAS, CONSULTAS }
        Seccion seccion = Seccion.NINGUNA;

        while ((linea = br.readLine()) != null) {
            linea = linea.trim();
            if (linea.isEmpty()) continue;

            // Cambios de seccion: .ciudades / .aerolineas / .rutas / .consultas
            if (linea.startsWith(".")) {
                String h = linea.toLowerCase();
                if (h.equals(".")) {
                    seccion = Seccion.NINGUNA;
                } else if (h.startsWith(".ciudades")) {
                    seccion = Seccion.CIUDADES;
                } else if (h.startsWith(".aerolineas")) {
                    seccion = Seccion.AEROLINEAS;
                } else if (h.startsWith(".rutas")) {
                    seccion = Seccion.RUTAS;
                } else if (h.startsWith(".consultas")) {
                    seccion = Seccion.CONSULTAS;
                }
                continue;
            }

            // =========================
            // CONSULTA
            // - Termina con '?', pero en seccion .consultas la linea puede traer basura y/o '.' despues.
            // =========================
            if (seccion == Seccion.CONSULTAS || linea.contains("->") || linea.contains("<-")) {
                int idxQ = linea.indexOf('?');
                if (idxQ >= 0) {
                    linea = linea.substring(0, idxQ).trim();
                }
                // Quitar basura despues de un '.' (p.ej. "?   . comentario")
                int idxP = linea.lastIndexOf('.');
                if (idxP >= 0) {
                    linea = linea.substring(0, idxP).trim();
                }
                Consulta c = parser.parsearLinea(linea);
                if (c != null && onConsulta != null) onConsulta.accept(c);
                continue;
            }

            // =========================
            // RUTAS/DECLARACIONES
            // - En seccion .rutas, procesamos la linea completa aunque falte '.' por comentarios.
            // - Fuera de seccion, seguimos usando el ultimo '.' como terminador de declaracion.
            // =========================
            if (seccion == Seccion.RUTAS) {
                // Si trae terminador, quitarselo. Si no, igual procesar.
                int idxP = linea.lastIndexOf('.');
                if (idxP >= 0) linea = linea.substring(0, idxP).trim();
                parser.parsearLinea(linea);
                continue;
            }

            // Declaraciones fuera de seccion (compatibilidad)
            int idxP = linea.lastIndexOf('.');
            if (idxP >= 0) {
                String limpia = linea.substring(0, idxP).trim();
                if (!limpia.isEmpty()) parser.parsearLinea(limpia);
            }
        }
    }
}
