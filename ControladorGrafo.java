/* Titulo: ControladorGrafo
 * Descripción: Clase controlador de grafo
 * @autor: Jared Eliezer Baldenegro Gomez; Jimer Orlando Diaz Murillo y Hannia Melendres Samaniego
 * Materia: Tecnologías de programación
 * Profesor: Dra. María Lucia Barrón Estrada
 * Fecha: 07-01-2026
 *
 * MODIFICADO: Se ha agregado el metodo "cargar" con el objetivo de que lea el archivo ".txt"
 * 
 * MODIFICADO: Se ha quitado del constructor la posibilidad de recibir un grafo del exterior.
 * Esto para crear y controlar el grafo completamente desde este archivo
 * 
 * MODIFICADO: Los metodos "viajemenorPrecio" y "top5viajesBaratos" han cambiado de public a private
 */

// IMPORTS NECESARIOS PARA CARGAR ARCHIVO ".txt"
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalTime;
import java.util.List;

public class ControladorGrafo {

    //atributos
    private Grafo grafo;
    private final ParserLineas parser;

    // CONSTRUCTOR
    public ControladorGrafo(){
        this.grafo = new Grafo();
        this.parser = new ParserLineas();
        
    }

    // CONSTRUCTOR QUE RECIBE VARIABLE GRAFO
    /*
    public ControladorGrafo(Grafo grafo) {
        this.grafo = grafo;
        this.parser = new ParserLineas();
    }
    */

    //////////////////////////////////////////////////////////////////////////////////////////
    /// LECTOR ARCHIVO
    //////////////////////////////////////////////////////////////////////////////////////////

    // Se lee linea por linea el archivo .txt
    public void leerArchivo(String nombreArchivo) {
        try (BufferedReader br = new BufferedReader(new FileReader(nombreArchivo))) {
            String linea;
            // el ciclo se repetira hasta terminar de leer todas las lineas
            while ((linea = br.readLine()) != null) {
                
                // cada linea es procesada y si es valida se almacena en parser, preparada para ser cargada
                parser.procesarLinea(linea);
            }
        } catch (IOException e) {
            System.out.println("Error al leer el archivo.");
        }
    }

    // Los datos con los que se lleno parser se pasan al grafo
    public void llenarGrafo() {

        // =========================
        // PASAR CIUDADES
        // =========================
        // Se tranfiere cada ciudad guardada en parser a grafo introduciendola una a una
        for (Ciudad ciudad : parser.getCiudades()) {
            // Si la ciudad no esta dentro de grafo, se almacena
            if (grafo.buscarCiudad(ciudad.getClave()) == null) {
                grafo.agregarCiudad(ciudad);
            }
        }

        // =========================
        // PASAR AEROLINEAS
        // =========================
        // Se almacenan las aerolineas, una a una, en grafo
        for (LineaAerea aerolinea : parser.getAerolineas()) {
            // Si la clave de la aerolinea no esta en grafo, esta se almacena
            if (grafo.buscarLineaAereaClave(aerolinea.getClave()) == null) {
                grafo.agregarLineaAerea(aerolinea);
            }
        }

        // =========================
        // PASAR RUTAS AEREAS
        // =========================
        // Se almacenan las rutas, una a una, en grafo
        for (Ruta ruta : parser.getRutas()) {

            // Revisa si la ciudad de origen y la ciudad de destino estan en grafo
            // LAS CIUDADES FUERON AGREGADAS AL PRINCIPIO DE ESTE METODO, POR LO QUE SI NO SE ENCUENTRAN
            // ENTONCES NO EXISTEN
            Ciudad origen = grafo.buscarCiudad(ruta.getclaveCiudadOrigen());
            Ciudad destino = grafo.buscarCiudad(ruta.getclaveCiudadDestino());

            // Solo agregar la ruta si ambas ciudades existen
            if (origen != null && destino != null) {
                // Se le agrega a la ciudad de origen las rutas de viaje
                // que surgen desde ella hacia otras ciudades
                origen.agregarRuta(ruta);     // modelo actual
                // se agrega la ruta a grafo
                grafo.agregarRuta(ruta);      // catalogo general
            }
        }

        // =========================
        // PASAR CONSULTAS
        // =========================
        // Las consultas se pasan, una a una, a grafo
        // estas almacenan almacenan los datos referentes a viajes posibles
        for (Consulta consulta : parser.getConsultas()) {
            grafo.agregarConsulta(consulta);
        }
    }

    /////////////////////////////////////////////////////////////////////////////////////////
    /// Pasar Consultas, Rutas, Ciudades y Lineas Aereas
    /// ///////////////////////////////////////////////////////////////////////////////////

    public List<LineaAerea> getAerolineas() {
        return grafo.getLineaAereas(); 
    }
    public List<Ciudad> getCiudades() { 
        return grafo.getCiudades(); 
    }
    public List<Ruta> getRutas() { 
        return grafo.getRuta(); 
    }
    public List<Consulta> getConsultas() {
         return grafo.getConsulta(); 
        }

    /////////////////////////////////////////////////////////////////////////////////////////
    /// METODO PARA IMPRIMIR LOS DATOS INGRESADOS
    /////////////////////////////////////////////////////////////////////////////////////////
    
    public void resumenDatosIngresados() {
        System.out.println("=== Resumen de carga ===");
        System.out.println("Ciudades cargadas: " + grafo.totalCiudades());
        System.out.println("Aerolineas cargadas: " + grafo.totalLineasAereas());
        System.out.println("Rutas cargadas: " + grafo.totalRutas());
        System.out.println("Consultas cargadas: " + grafo.totalConsultas());
        System.out.println("===========================");


        //////////////////////////////////////////////////
        /// linea DEBUG para comprobar la carga de las aerolineas si era true o false
        /*
        for (LineaAerea l : grafo.getLineaAereas()) {
            System.out.println("Linea " + l.getClave() +" activo=" + l.getActivo());
        }
        */
    }

    /////////////////////////////////////////////////////////////////////////////////////////
    /// IMPRIMIR RESULTADOS DE CONSULTAS
    /////////////////////////////////////////////////////////////////////////////////////////

    public void ImprimirCaminoBarato() {
        for (Consulta consulta : grafo.getConsulta()) {

            System.out.println("----------------------------------");
            
            System.out.println("Consulta: " + consulta.getOrigen() + " -> " + consulta.getDestino());
            
            //--------------------------------------------------------------------------------------------
            //////////////////// CAMINO MAS BARATO ///////////////////////////////////////////////////////
            // Se genera el camino más barato
            List<String> camino = viajeMenorPrecio(consulta.getOrigen(),consulta.getDestino());

            // Si no existe un camino posible, se imprime un mensaje de que no hay ruta posible
            // De lo contrario, si existe un camino posible, se imprimira el mas barato encontrado
            if (camino == null || camino.isEmpty()) {
                System.out.println("No hay ruta disponible.");
            } else {
                System.out.println("Camino más barato: " + camino);
            }
            //---------------------------------------------------------------------------------------------
            //////////////////////////// 5 CAMINOS  MAS BARATOS ///////////////////////////////////////////
            // Se obtiene los 5 caminos mas baratos para realizar el viaje
            List<Viaje> top5 = top5ViajesBaratos(consulta.getOrigen(),consulta.getDestino());

            // Si no existen caminos disponibles para realizar el viaje, se imprime un mensaje que las rutas no disponibles
            // Si pudieron ser obtenidos, seran impresos
            if (top5 == null || top5.isEmpty()) {
                System.out.println("Top 5: No disponible.");
            } else {
                System.out.println("Top 5 viajes baratos:");
                for (Viaje viaje : top5) {
                    System.out.println("  - " + viaje);
                }
            }
            
        }
    }

    /*
    public void imprimirTop5Caminos() {

        for (Consulta consulta : grafo.getConsulta()) {

            // Se obtiene los 5 caminos mas baratos para realizar el viaje
            List<Viaje> top5 = top5ViajesBaratos(consulta.getOrigen(),consulta.getDestino());

            // Si no existen caminos disponibles para realizar el viaje, se imprime un mensaje que las rutas no disponibles
            // Si pudieron ser obtenidos, seran impresos
            if (top5 == null || top5.isEmpty()) {
                System.out.println("Top 5: No disponible.");
            } else {
                System.out.println("Top 5 viajes baratos:");
                for (Viaje viaje : top5) {
                    System.out.println("  - " + viaje);
                }
            }

        }
        
    }
     */



    /////////////////////////////////////////////////////////////////////////////////////////
    /// METODOS PARA INTRODUCIR DIRECTAMENTE LINEAS Y AGREGARLAS LA GRAFO
    /////////////////////////////////////////////////////////////////////////////////////////


    public boolean agregarLineaAerea(String clave, String nombreLineaAerea, boolean activo){

        boolean resultado = false;

        if(grafo.buscarLineaAereaNombre(nombreLineaAerea) == null){

            LineaAerea nuevaLineaAerea = new LineaAerea(clave,nombreLineaAerea,activo);
            grafo.agregarLineaAerea(nuevaLineaAerea);
            resultado = true;
        }else{
            System.out.println("La Linea Aerea ya estaba en el catalogo de Lineas Aereas");
        }

        return resultado;

    }

    public boolean agregarCiudad(String clave, String nombreCiudad, String estado, String pais, String continente){

        boolean resultado = false;

        if(grafo.buscarCiudad(clave) == null){

            Ciudad nuevaCiudad = new Ciudad(clave,nombreCiudad,estado,pais,continente);
            grafo.agregarCiudad(nuevaCiudad);
            resultado = true;
        }else{
            System.out.println("La ciudad ya estaba en el catalogo de ciudades");
        }

        return resultado;
    }

    public boolean agregarRuta(String claveAerolinea, int numeroRuta, String claveCiudadOrigen, String claveCiudadDestino, double costo, LocalTime horaSalida, LocalTime horaLlegada, String frecuencia){

        boolean resultado = false;

        if(grafo.buscarCiudad(claveCiudadOrigen) != null && grafo.buscarCiudad(claveCiudadDestino) != null && grafo.buscarLineaAereaClave(claveAerolinea)!=null){

            Ciudad ciudadParaNuevaRuta = grafo.buscarCiudad(claveCiudadOrigen);
            Ruta nuevaRuta = new Ruta(claveAerolinea,numeroRuta,claveCiudadOrigen,claveCiudadDestino,costo,horaSalida,horaLlegada,frecuencia);
            ciudadParaNuevaRuta.agregarRuta(nuevaRuta);
            resultado = true;
        }

        return resultado;
    }


    //////////////////////////////////////////////////////////////////////////////////////////
    /// CALCULAR EL CAMINO DE MENOR PRECIO
    //////////////////////////////////////////////////////////////////////////////////////////

    private List<String> viajeMenorPrecio(String claveCiudadOrigen, String claveCiudadDestino){

        List<String> camino = null;

        // Si ambas ciudades existen, realiza una busqueda del camino que las conecte a ambas
        if(grafo.buscarCiudad(claveCiudadOrigen) != null && grafo.buscarCiudad(claveCiudadDestino) != null){

            AlgoritmoBusquedaCamino algoritmo = new AlgoritmoBusquedaCamino();
            camino = algoritmo.viajeMenorPrecio(grafo, claveCiudadOrigen, claveCiudadDestino);
        }

        return camino;
    }

    /////////////////////////////////////////////////////////////////////////////////////////
    /// 5 VIAJES MAS BARATOS
    /////////////////////////////////////////////////////////////////////////////////////////
    private List<Viaje> top5ViajesBaratos(String claveCiudadOrigen, String claveCiudadDestino){

        List<Viaje> viajes = null;

        if(grafo.buscarCiudad(claveCiudadOrigen) != null && grafo.buscarCiudad(claveCiudadDestino) != null){

            Ciudad ciudadOrigen = grafo.buscarCiudad(claveCiudadOrigen);
            Ciudad ciudadDestino = grafo.buscarCiudad(claveCiudadDestino);

            AlgoritmoBusquedaCamino algoritmo = new AlgoritmoBusquedaCamino();
            viajes = algoritmo.top5ViajesBaratos(grafo, ciudadOrigen, ciudadDestino);
        }

        return viajes;
    }
}