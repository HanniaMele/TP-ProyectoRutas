/*
 * Titulo: ParserLineas
 * Descripcion: Este archivo se encarga de parsear y validar las lineas leidas del archivo de entrada
 * Autor: Jared Eliezer Baldenegro Gomez; Jimer Orlando Diaz Murillo y Hannia Melendres Samaniego
 * Materia: Tecnologias de programacion
 * Profesor: Dra. Maria Lucia Barron Estrada
 * Fecha: 07-01-2026
 */
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

/**
 * Se encarga de interpretar una linea de texto (sin terminadores '.' o '?')
 * y convertirla en objetos del dominio.
 *
 * IMPORTANTE (segun enunciado):
 * - Declaraciones y rutas terminan con '.'
 * - Consultas terminan con '?'
 * - Despues de '.' o '?' puede venir basura que se ignora (lo hace LectorArchivo)
 * - Las lineas pueden venir en cualquier orden (se procesa en el orden del archivo)
 */
public class ParserLineas {

    private final ControladorGrafo controlador;

    private static final DateTimeFormatter FECHA_FMT =
            DateTimeFormatter.ofPattern("dd/MM/yyyy");

    private static final DateTimeFormatter HORA_FMT =
            DateTimeFormatter.ofPattern("H:mm"); // acepta 6:00 y 06:00

    public ParserLineas(ControladorGrafo controlador) {
        this.controlador = controlador;
    }

    /**
     * Parsea una linea ya "limpia" (sin '.' ni '?' al final).
     *
     * @return una Consulta si la linea era consulta, o null si era declaracion/ruta.
     */
    public Consulta parsearLinea(String linea) {

        if (linea == null) return null;

        linea = linea.trim();
        if (linea.isEmpty()) return null;

        // =========================
        // CONSULTA
        // Solo es consulta si trae fecha (dd/MM/yyyy). Si no, puede ser una "ruta" tipo MEX -> CUL (enunciado)
        // =========================
        if ((linea.contains("->") || linea.contains("<-"))
                && linea.matches(".*\\d{2}/\\d{2}/\\d{4}.*")) {
            return parsearConsulta(linea);
        }

        // Tokenizacion general
        String[] tokens = linea.split("\s+");

        // =========================
        // RUTA (AA 0167 CUL MEX 1000.00 06:00 08:50 12345)
        // Nota: en los archivos de prueba hay rutas con comentarios al final (sin punto)
        // y tambien casos con un espacio faltante: "MEX100.00".
        // =========================
        if (tokens.length >= 8 && tokens[1].matches("\\d{1,4}")) {
            parsearRuta(linea, tokens);
            return null;
        }

        // Caso especial: destino pegado con costo, ej: "CUN MEX100.00 18:00 19:00 1234567"
        if (tokens.length == 7 && tokens[1].matches("\\d{1,4}") && tokens[3].matches("[A-Za-z]{3}\\d.*")) {
            parsearRuta(linea, tokens);
            return null;
        }

        // =========================
        // CIUDAD (AAA nombre, estado, pais, continente)
        // =========================
        // IMPORTANTE: una ciudad tambien inicia con 3 letras (MEX, CUL, ...),
        // asi que debemos detectarla ANTES de interpretar como aerolinea.
        if (tokens.length >= 2 && tokens[0].length() == 3 && linea.contains(",")) {
            parsearCiudad(linea);
            return null;
        }

        // =========================
        // LINEA AEREA (alta / baja)
        // - Alta: AA NOMBRE
        // - Baja: -AA
        // =========================
        if (tokens.length >= 1) {
            String claveRaw = tokens[0].trim();

            // Baja: "-AA" o "-AAA"
            if (claveRaw.startsWith("-") && claveRaw.length() >= 3) {
                String clave = claveRaw.substring(1);
                controlador.desactivarLineaAerea(clave);
                return null;
            }

            // Alta: clave + nombre (puede traer espacios)
            // Las aerolineas normalmente no traen comas.
            if (tokens.length >= 2 && claveRaw.length() <= 3 && !linea.contains(",")) {
                String clave = claveRaw;
                String nombre = linea.substring(linea.indexOf(clave) + clave.length()).trim();
                controlador.agregarLineaAerea(new LineaAerea(clave, nombre, true));
                return null;
            }
        }

        // Si no entra en nada, se ignora.
        return null;
    }

    /* =========================================================
       PARSERS ESPECIFICOS
       ========================================================= */

    private void parsearRuta(String lineaOriginal, String[] t) {
        try {
            // Normalizar tokens: quitar '.' al final y quedarnos con los primeros campos.
            for (int i = 0; i < t.length; i++) {
                t[i] = t[i].trim();
                if (t[i].endsWith(".")) t[i] = t[i].substring(0, t[i].length() - 1);
            }

            String claveLinea = t[0];
            String idRuta = t[1];
            String origen = t[2];

            String destino;
            String costoStr;
            String hsStr;
            String hlStr;
            String frecuencia;

            if (t.length >= 8) {
                destino = t[3];
                costoStr = t[4];
                hsStr = t[5];
                hlStr = t[6];
                frecuencia = t[7];
            } else if (t.length == 7) {
                // t[3] = "MEX100.00" => destino=MEX, costo=100.00
                String dc = t[3];
                destino = dc.substring(0, 3);
                costoStr = dc.substring(3);
                hsStr = t[4];
                hlStr = t[5];
                frecuencia = t[6];
            } else {
                throw new IllegalArgumentException("Tokens insuficientes");
            }

            // frecuencia puede venir con basura (por comentario), quedarnos solo con digitos 1-7
            frecuencia = frecuencia.replaceAll("[^1-7]", "");

            double costo = Double.parseDouble(costoStr);
            LocalTime horaSalida = LocalTime.parse(hsStr, HORA_FMT);
            LocalTime horaLlegada = LocalTime.parse(hlStr, HORA_FMT);

            Ruta ruta = new Ruta(idRuta, claveLinea, origen, destino, costo,
                    horaSalida, horaLlegada, frecuencia);

            controlador.agregarRuta(ruta);

        } catch (Exception ex) {
            System.err.println("[WARN] Ruta invalida ignorada: " + lineaOriginal);
        }
    }

    private void parsearCiudad(String linea) {
        // Ejemplo:
        // MEX  CIUDAD DE MEXICO, DF, MEXICO, AMERICA
        try {
            String clave = linea.substring(0, 3).trim();
            String resto = linea.substring(3).trim();

            String[] partes = resto.split(",");
            String nombre = partes.length > 0 ? partes[0].trim() : "";
            String pais = partes.length > 2 ? partes[2].trim() : (partes.length > 1 ? partes[1].trim() : "");

            ZoneId zona = inferirZonaHoraria(clave, pais);

            Ciudad ciudad = new Ciudad(clave, nombre, pais, zona);
            controlador.agregarCiudad(ciudad);

        } catch (Exception ex) {
            System.err.println("[WARN] Ciudad invalida ignorada: " + linea);
        }
    }

    private Consulta parsearConsulta(String linea) {

        String limpia = linea.replace("->", " ")
                .replace("<-", " ")
                .replace("?", "")
                .trim();

        String[] t = limpia.split("\s+");
        if (t.length < 3) {
            System.err.println("[WARN] Consulta invalida ignorada: " + linea);
            return null;
        }

        String origen = t[0];
        String destino = t[1];

        LocalDate salida;
        try {
            salida = LocalDate.parse(t[2], FECHA_FMT);
        } catch (Exception ex) {
            System.err.println("[WARN] Consulta ignorada (fechaSalida invalida): " + linea);
            return null;
        }

        if (t.length >= 4) {
            try {
                LocalDate regreso = LocalDate.parse(t[3], FECHA_FMT);
                return new Consulta(origen, destino, salida, regreso);
            } catch (Exception ex) {
                System.err.println("[WARN] Consulta ignorada (fechaRegreso invalida): " + linea);
                return null;
            }
        }

        return new Consulta(origen, destino, salida);
    }

    /* =========================================================
       ZONAS HORARIAS
       ========================================================= */

    /**
     * El archivo de ciudades no incluye zona horaria.
     * Para cumplir el requisito, inferimos ZoneId con reglas practicas para el catalogo entregado.
     * Si no se reconoce, se usa UTC (evita que el programa falle).
     */
    private ZoneId inferirZonaHoraria(String claveCiudad, String pais) {

        String c = claveCiudad == null ? "" : claveCiudad.trim().toUpperCase(Locale.ROOT);
        String p = pais == null ? "" : pais.trim().toUpperCase(Locale.ROOT);

        // Reglas por clave (mas confiables que por pais)
        switch (c) {
            // MEXICO
            case "CUL":
            case "MZT":
            case "HMO":
                return ZoneId.of("America/Mazatlan"); // Noroeste
            case "CUN":
                return ZoneId.of("America/Cancun");
            case "MEX":
            case "GDJ":
            case "MTY":
            case "ACA":
            case "OAX":
            case "CUU":
                return ZoneId.of("America/Mexico_City");

            // USA
            case "JFK":
            case "BOS":
            case "MIA":
            case "ORL":
            case "ATL":
                return ZoneId.of("America/New_York");
            case "CHI":
            case "DFW":
            case "SAT":
                return ZoneId.of("America/Chicago");
            case "LAX":
                return ZoneId.of("America/Los_Angeles");

            // CANADA
            case "YEA": // Edmonton
                return ZoneId.of("America/Edmonton");
            case "YOW": // Ottawa
                return ZoneId.of("America/Toronto");
        }

        // Reglas por pais (si no hay clave especifica)
        if (p.contains("MEX")) return ZoneId.of("America/Mexico_City");
        if (p.contains("USA") || p.contains("ESTADOS UNIDOS")) return ZoneId.of("America/New_York");
        if (p.contains("CANADA")) return ZoneId.of("America/Toronto");

        if (p.contains("ITAL")) return ZoneId.of("Europe/Rome");
        if (p.contains("FRAN")) return ZoneId.of("Europe/Paris");
        if (p.contains("ESPA")) return ZoneId.of("Europe/Madrid");
        if (p.contains("ALEM") || p.contains("GERM")) return ZoneId.of("Europe/Berlin");
        if (p.contains("ING") || p.contains("REINO UNIDO")) return ZoneId.of("Europe/London");
        if (p.contains("HOLAN") || p.contains("PAISES BAJOS") || p.contains("NETHER")) return ZoneId.of("Europe/Amsterdam");
        if (p.contains("JAPON")) return ZoneId.of("Asia/Tokyo");
        if (p.contains("CHINA")) return ZoneId.of("Asia/Shanghai");
        if (p.contains("INDIA")) return ZoneId.of("Asia/Kolkata");
        if (p.contains("SUDAFR")) return ZoneId.of("Africa/Johannesburg");
        if (p.contains("MARRUE")) return ZoneId.of("Africa/Casablanca");
        if (p.contains("MADAG")) return ZoneId.of("Indian/Antananarivo");
        if (p.contains("SENEG")) return ZoneId.of("Africa/Dakar");
        if (p.contains("EGIP")) return ZoneId.of("Africa/Cairo");
        if (p.contains("CHECA") || p.contains("CZECH")) return ZoneId.of("Europe/Prague");

        // Fallback seguro
        return ZoneId.of("UTC");
    }
}
