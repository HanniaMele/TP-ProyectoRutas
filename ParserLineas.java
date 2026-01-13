/*
 * Titulo: ParserLineas
 * Descripcion: Este archivo se encarga de parsear y validar las lineas leidas del archivo de entrada
 * @autor: Jared Eliezer Baldenegro Gomez; Jimer Orlando Diaz Murillo y Hannia
 * Materia: tecnologias de programacion
 * Profesor: Dra. María Lucia Barrón Estrada
 * Fecha: 07-01-2026
 *
 * MODIFICADO: se a quitado el grafo y se ha hecho que ParserLineas almacene las lineas que va leyendo.
 * 
 * ATENCION: ES POSIBLE QUE LAS VARIABLES COLECTION RUTAS Y CONSULTAS DEJEN DE SER LISTAS Y SE VUELVAN HASHMAP
 * 
 * 
 * MODIFICADO: En el metodo procesarAerolinea se a agregado la linea:
 * boolean baja = m.group(1) != null; // si viene "-" al inicio

   oolean activo = !baja;

   Esto para asegurar que las erolineas inicien activas
 */
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    //private final Grafo repositorioDatos;

    private final List<LineaAerea> aerolineas;
    private final List<Ciudad> ciudades;
    private final List<Ruta> rutas;
    private final List<Consulta> consultas;


    //--NEW--
    //private final List<Consulta> consultas = new ArrayList<>();
    
    
    
    //-----

    public ParserLineas() { //Grafo repositorioDatos
        //this.repositorioDatos = repositorioDatos;
        this.aerolineas = new ArrayList<>();
        this.ciudades = new ArrayList<>();
        this.rutas = new ArrayList<>();
        this.consultas = new ArrayList<>();

    }

    //////////////////////////////////////////////////////
    /// GETTERS (PARA OBTENER LOS RESULTADOS Y DEVOLVERLOS)
    /////////////////////////////////////////////////////

    public List<LineaAerea> getAerolineas() { 
        return aerolineas; 
    }
    public List<Ciudad> getCiudades() { 
        return ciudades; 
    }
    public List<Ruta> getRutas() { 
        return rutas; 
    }
    public List<Consulta> getConsultas() {
         return consultas; 
        }




    //---
    //public List<Consulta> getConsultas() {
    //    return consultas;
    //}

    /////////////////////////////////////////////////////////////////////////////////
    /// PROCESAR LINEAS
    /////////////////////////////////////////////////////////////////////////////////

    private void procesarAerolinea(Matcher m) {
        boolean baja = m.group(1) != null; // si viene "-" al inicio

        boolean activo = !baja;

        String clave = m.group(2);
        String nombre = m.group(3);

        // SE GENERA UNA NUEVA AEROLINEA PARA ALMACENAR LOS DATOS
        LineaAerea aeroLinea = new LineaAerea(clave, nombre, activo); //

        this.aerolineas.add(aeroLinea);

        // Si ya existe, la quitamos y la re-agregamos con el estado correcto (no hay setter para activo)
        //if (existente != null) {
          //  repositorioDatos.catalogoLineasAereas.removeIf(a -> a.getClave().equals(clave));
        //}

        // '-' => inactiva (activo=false); sin '-' => activa (activo=true)
        //LineaAerea aerolinea = new LineaAerea(clave, nombre, !baja);
        //repositorioDatos.agregarLineaAerea(aerolinea);
    }



    /// Procesa la linea que contiene la informacion de una ciudad
    /// @param lineaCiudad Matcher con la linea ya validada
    private void procesarCiudad(Matcher lineaCiudad) {
        
        String clave = lineaCiudad.group(1);
        
        boolean existe = ciudades.stream().anyMatch(c -> c.getClave().equals(clave));

        if (existe) return;

        Ciudad ciudad = new Ciudad(clave,lineaCiudad.group(2).trim(),lineaCiudad.group(3).trim(),lineaCiudad.group(4).trim(),lineaCiudad.group(5).trim());

        ciudades.add(ciudad);

        /* 
        String clave = lineaCiudad.group(1);

        if (repositorioDatos.buscarCiudad(clave) != null) {
            return; // ya existe, se ignora
        }

        Ciudad ciudad = new Ciudad(
                clave,
                lineaCiudad.group(2).trim(),
                lineaCiudad.group(3).trim(),
                lineaCiudad.group(4).trim(),
                lineaCiudad.group(5).trim()
        );

        repositorioDatos.agregarCiudad(ciudad);
        */
    }


    private void procesarRuta(Matcher lineaRuta, boolean invertida) {
        String claveOrigen = lineaRuta.group(3);
        String claveDestino = lineaRuta.group(4);

        if (invertida) {
            String temporal = claveOrigen;
            claveOrigen = claveDestino;
            claveDestino = temporal;
        }

        String clave = lineaRuta.group(1);
        int numeroRuta = Integer.parseInt(lineaRuta.group(2));
        String claveCiudadOrigen = lineaRuta.group(3);
        String claveCiudadDestino = lineaRuta.group(4);
        double costo = Double.parseDouble(lineaRuta.group(5));

        LocalTime horaSalida = LocalTime.parse(lineaRuta.group(6), FORMATO_HORA);
        LocalTime horaLlegada = LocalTime.parse(lineaRuta.group(7), FORMATO_HORA);

        String frecuencia = lineaRuta.group(8);

        Ruta ruta = new Ruta(clave, numeroRuta, claveCiudadOrigen, claveCiudadDestino, costo, horaSalida, horaLlegada, lineaRuta.group(8));

        rutas.add(ruta);

        // Validaciones mínimas para no guardar basura
        /*
        if (repositorioDatos.buscarLineaAereaClave(clave) == null) {
            System.out.println("Ruta ignorada: aerolínea no registrada -> " + clave);
            return;
        }

        Ciudad origen = repositorioDatos.buscarCiudad(claveCiudadOrigen);
        Ciudad destino = repositorioDatos.buscarCiudad(claveCiudadDestino);
        if (origen == null || destino == null) {
            System.out.println("Ruta ignorada: ciudad origen/destino no registrada -> "
                    + claveCiudadOrigen + " -> " + claveCiudadDestino);
            return;
        }

        // Convertir HH:mm a horas decimales
        double hSalida = horaSalida.getHour() + (horaSalida.getMinute() / 60.0);
        double hLlegada = horaLlegada.getHour() + (horaLlegada.getMinute() / 60.0);

        Ruta ruta = new Ruta(clave, numeroRuta, claveCiudadOrigen, claveCiudadDestino,
                costo, hSalida, hLlegada, frecuencia);

        // En tu modelo, las rutas viven dentro de la Ciudad origen
        origen.agregarRuta(ruta);
         */
    }


    private void procesarConsulta(Matcher lineaConsulta, boolean invertida) {
        String origen = lineaConsulta.group(1);
        String destino = lineaConsulta.group(2);
        String fechaSalida = lineaConsulta.group(3);
        String fechaRegreso = lineaConsulta.group(4);

        if (invertida) {
            String temporal = origen;
            origen = destino;
            destino = temporal;
        }

        Consulta consulta = new Consulta(origen, destino, fechaSalida, fechaRegreso);
        consultas.add(consulta);

        System.out.println("DEBUG Consulta parseada: " + origen + " -> " + destino + " (invertida=" + invertida + ")");

    }

    //////////////////////////////////////////////////////////////////////////////////
    /// LEER LINEAS
    /////////////////////////////////////////////////////////////////////////////////

    // Prepara la linea leida para asegurarse de que es valida
    // Elimina el espacio en blanco al inicio y al final
    // Si la linea esta vacia o contiene solo un punto, devuelve null
    private String limpiarLinea(String linea) {

        if (linea == null) {
            return null;
        }

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
        // LA LINEA REGRESADA ESTA POTENCIALMENTE DISEÑADA PARA SER VALIDADA POR LOS PATRONES
        return linea.isEmpty() ? null : linea;
    }


    public void procesarLinea(String linea) {

        // Verifica si la linea es una ruta invertida
        boolean invertida = linea.contains("<-");
        linea = linea.replace("<-", "->");


        linea = limpiarLinea(linea);
        if (linea == null || linea.isEmpty()) return;

        Matcher lineaPatron;

        lineaPatron = P_RUTA.matcher(linea);
        if (lineaPatron.matches()) {
            procesarRuta(lineaPatron, invertida);
            return;
        }

        lineaPatron = P_CIUDAD.matcher(linea);
        if (lineaPatron.matches()) {
            procesarCiudad(lineaPatron);
            return;
        }

        lineaPatron = P_AEROLINEA.matcher(linea);
        if (lineaPatron.matches()) {
            procesarAerolinea(lineaPatron);
            return;
        }

        lineaPatron = P_CONSULTA.matcher(linea);
        if (lineaPatron.matches()) {
            procesarConsulta(lineaPatron, invertida);
            return;
        }

        System.out.println("Línea inválida: " + linea);
    }
}
