/*
 * Titulo: ParserLineas 
 * Descripcion: Este archivo se encarga de parsear y validar las lineas leidas del archivo de entrada
 * @autor: Jared Eliezer Baldenegro Gomez; Jimer Orlando Diaz Murillo y Hannia
 * Materia: tecnologias de programacion
 * Profesor: Dra. María Lucia Barrón Estrada
 * Fecha: 07-01-2026
 * 
 * 
 */
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ParserLineas {
   ////////////
    /// Funciones auxiliares
    /////////////
    private static final DateTimeFormatter FORMATO_HORA = DateTimeFormatter.ofPattern("HH:mm");

    ///////////
    // Expresiones regulares para validar las lineas de entrada
    ///////////
    
    /* 
     * Formato de linea para AEROLINEA:
     * ^ : Inicio de la línea. Nada antes, ni espacios.
     * (-)? : Un guion opcional. Puede existir o no. Sirve, por ejemplo, para marcar aerolíneas deshabilitadas.
     * ([A-Z]{2,3}) : Código de aerolínea; exactamente 2 o 3 letras mayúsculas (AM, AA, KLM, etc.) 
     * \s+ : Uno o más espacios. Se obliga a que haya separación clara.
     * ([A-Za-z]{1,10}) : Nombre de la aerolínea (entre 1 y 10 letras, mayúsculas o minúsculas).
     * $ : Fin de la línea. Nada después.
     */
    private static final Pattern P_AEROLINEA = Pattern.compile("^(-)?([A-Z]{2,3})\\s+([A-Za-z](?:[A-Za-z ]{0,8}[A-Za-z])?)$");

    /*
     * Formato de linea para CIUDAD:
     * ^ : Inicio de la línea. Nada antes, ni espacios.
     * ([A-Z]{3}) : Código de ciudad; exactamente 3 letras mayúsculas (MEX, CUL, etc.)
     * \s+ : Uno o más espacios. Se obliga a que haya separación clara.
     * ([^,]+),([^,]+),([^,]+),([^,]+) : Nombre de la ciudad, país, continente y zona.
     * $ : Fin de la línea. Nada después.
     */
    private static final Pattern P_CIUDAD = Pattern.compile("^([A-Z]{3})\\s+([^,]+),([^,]+),([^,]+),([^,]+)$");

    /*
     * Formato de linea para RUTA:
     * ^ : Inicio de la línea. Nada antes, ni espacios.
     * ([A-Z]{2,3}) : Código de aerolínea; exactamente 2 o 3 letras mayúsculas (AM, AA, KLM, etc.)
     * \s+ : Uno o más espacios. Se obliga a que haya separación clara.
     * (\\d{4}) : Número de ruta; exactamente 4 dígitos.
     * \s+ : Uno o más espacios. Se obliga a que haya separación clara.
     * ([A-Z]{3}) : Código de ciudad de origen; exactamente 3 letras mayúsculas (MEX, CUL, etc.)
     * \s+ : Uno o más espacios. Se obliga a que haya separación clara.
     * ([A-Z]{3}) : Código de ciudad de destino; exactamente 3 letras mayúsculas (MEX, CUL, etc.)
     * \s+ : Uno o más espacios. Se obliga a que haya separación clara.
     * (\\d+(?:\\.\\d+)?) : Costo del vuelo; número entero o decimal.
     * \s+ : Uno o más espacios. Se obliga a que haya separación clara.
     * (\\d{2}:\\d{2}) : Hora de salida en formato HH:MM.
     * \s+ : Uno o más espacios. Se obliga a que haya separación clara.
     * (\\d{2}:\\d{2}) : Hora de llegada en formato HH:MM.
     * \s+ : Uno o más espacios. Se obliga a que haya separación clera.
     * ([1-7]+) : Frecuencia semanal del vuelo; dígitos del 1 al 7 representando días de la semana (1=Lunes, ..., 7=Domingo).
     * $ : Fin de la línea. Nada luego.
     */
    private static final Pattern P_RUTA = Pattern.compile("^([A-Z]{2,3})\\s+(\\d{4})\\s+([A-Z]{3})\\s+([A-Z]{3})\\s+" + "(\\d+(?:\\.\\d+)?)\\s+(\\d{2}:\\d{2})\\s+(\\d{2}:\\d{2})\\s+([1-7]+)$");

    /*
     * Formato de linea para CONSULTA:
     * ^ : Inicio de la línea. Nada antes, ni espacios.
     * ([A-Z]{3}) : Código de ciudad de origen; exactamente 3 letras mayúsculas (MEX, CUL, etc.)
     * \s*->\s* : Separador "->" con posibles espacios antes
     * ([A-Z]{3}) : Código de ciudad de destino; exactamente 3 letras mayúsculas (MEX, CUL, etc.)
     * \s+ : Uno o más espacios. Se obliga a que haya separación clara.
     * (\\d{2}/\\d{2}/\\d{4}) : Fecha de salida en formato DD/MM/AAAA.
     * (?:\s+(\\d{2}/\\d{2}/\\d{4}))? : Fecha de regreso en formato DD/MM/AAAA (opcional, con espacios antes).
     * $ : Fin de la línea. Nada luego.
     */

    //private static final Pattern P_CONSULTA = Pattern.compile("^([A-Z]{3})\\s*->\\s*([A-Z]{3})\\s+"+"(\\d{2}/\\d{2}/\\d{4})"+"(?:\\s+(\\d{2}/\\d{2}/\\d{4}))?"+"\\?$");

    private static final Pattern P_CONSULTA = Pattern.compile("^([A-Z]{3})\\s*->\\s*([A-Z]{3})\\s+" + "(\\d{2}/\\d{2}/\\d{4})" + "(?:\\s+(\\d{2}/\\d{2}/\\d{4}))?" + "\\s*\\?$");

    // variable final que representa el grafo donde se almacenan los datos parseados
    private final Grafo repositorioDatos;

    public ParserLineas(Grafo repositorioDatos) {
        this.repositorioDatos = repositorioDatos;
    }

 

    private void procesarAerolinea(Matcher m) {
        boolean baja = m.group(1) != null;
        String clave = m.group(2);
        String nombre = m.group(3);

        if (baja) {
            if (!repositorioDatos.aerolineas.containsKey(clave)) {
                System.out.println("Baja ignorada, aerolínea inexistente: " + clave);
                return;
            }
            repositorioDatos.aerolineas.remove(clave);
        } else {

            LineaAerea aerolinea = new LineaAerea(clave, nombre, baja);
            repositorioDatos.aerolineas.put(clave, aerolinea);
        }
    }

    
    /// Procesa la linea que contiene la informacion de una ciudad
    /// @param lineaCiudad Matcher con la linea ya validada
    private void procesarCiudad(Matcher lineaCiudad) {
        String clave = lineaCiudad.group(1);

        if (repositorioDatos.ciudades.containsKey(clave)) {
            return; // ya existe, se ignora
        }

        Ciudad ciudad = new Ciudad(clave,lineaCiudad.group(2).trim(),lineaCiudad.group(3).trim(),lineaCiudad.group(4).trim(),lineaCiudad.group(5).trim());

        repositorioDatos.ciudades.put(clave, ciudad);
    }

    private void procesarRuta(Matcher m) {
        String clave = m.group(1);
        int numeroRuta = Integer.parseInt(m.group(2));
        String claveCiudadOrigen = m.group(3);
        String claveCiudadDestino = m.group(4);
        double costo = Double.parseDouble(m.group(5));
        LocalTime HoraSalida = LocalTime.parse(m.group(6), FORMATO_HORA);
        LocalTime HoraLlegada = LocalTime.parse(m.group(7), FORMATO_HORA);
        String frecuencia = m.group(8);

        Ruta ruta = new Ruta(clave, numeroRuta, claveCiudadOrigen, claveCiudadDestino, costo, HoraSalida, HoraLlegada, frecuencia);
        repositorioDatos.rutas.add(ruta);
    }

    private void procesarConsulta(Matcher m, boolean invertida) {
        String origen = m.group(1);
        String destino = m.group(2);
        String fechaSalida = m.group(3);
        String fechaRegreso = m.group(4);

        if (invertida) {
            // Intercambia origen y destino
            String origenTemporal = origen;
            origen = destino;
            destino = origenTemporal;
        }

        Consulta consulta = new Consulta(origen, destino, fechaSalida, fechaRegreso);
        repositorioDatos.consultas.add(consulta);
    }

    // Prepara la linea leida para asegurarse de que es valida
    // Elimina el espacio en blanco al inicio y al final
    // Si la linea esta vacia o contiene solo un punto, devuelve null
    private String limpiarLinea(String linea) {

        if (linea == null) return null;

        // Elimina espacios en blanco al inicio y al final
        linea = linea.trim();

        // Almacenara donde se realizara el corte
        int corte = -1;

        // Si la linea esta vacia o contiene solo un punto, devuelve null
        if (linea.isEmpty() || linea.equals(".")) {
            return null;
        }

        // Elimina cualquier cosa despues del ultimo punto
        int punto = linea.lastIndexOf('.');

        // Buscamos el ultimo signo de pregunta
        int pregunta = linea.lastIndexOf('?');

        if (punto != -1) {
            // El punto manda: corta antes del punto
            corte = punto;
        } else if (pregunta != -1) {
            // No hay punto, pero hay pregunta: corta DESPUES del ?
            corte = pregunta + 1;
        }

        // Realiza el corte si es necesario
        if (corte != -1) {
            linea = linea.substring(0, corte);
        }
        
        // Elimina espacios en blanco al inicio y al final nuevamente
        linea = linea.trim();
        // Devuelve la linea limpia o null si esta vacia
        return linea.isEmpty() ? null : linea;
    }
   

    public void procesarLinea(String linea) {

        // Verifica si la linea es una ruta invertida
        boolean invertida = linea.contains("<-");
        linea = linea.replace("<-", "->");


        linea = limpiarLinea(linea);
        if (linea == null || linea.isEmpty()) return;

        Matcher m;

        m = P_RUTA.matcher(linea);
        if (m.matches()) {
           procesarRuta(m);
            return;
        }

        m = P_CIUDAD.matcher(linea);
        if (m.matches()) {
            procesarCiudad(m);
            return;
        }

        m = P_AEROLINEA.matcher(linea);
        if (m.matches()) {
            procesarAerolinea(m);
            return;
        }

        m = P_CONSULTA.matcher(linea);
        if (m.matches()) {
            procesarConsulta(m, invertida);
            return;
        }

        System.out.println("Línea inválida: " + linea);
    }
}

