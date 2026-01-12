/*
 *
 * MODIFICADO: Se a agregado el metodo "cargar" con el objetivo de que lea el archivo ".txt"
 */

import java.util.List;

// IMPORTS NECESARIOS PARA CARGAR ARCHIVO ".txt"
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalTime;
import java.util.List;

public class ControladorGrafo {

    public Grafo grafo;
    private final ParserLineas parser;

    // CONSTRUCTOR VACIO
    public ControladorGrafo(){
        this.grafo = new Grafo();
        this.parser = new ParserLineas();
        
    }

    // CONSTRUCTOR QUE RECIBE VARIABLE GRAFO
    public ControladorGrafo(Grafo grafo) {
        this.grafo = grafo;
        this.parser = new ParserLineas();
    }

    //////////////////////////////////////////////////////////////////////////////////////////
    /// LECTOR ARCHIVO
    //////////////////////////////////////////////////////////////////////////////////////////

    public void leerArchivo(String nombreArchivo) {
        try (BufferedReader br = new BufferedReader(new FileReader(nombreArchivo))) {
            String linea;
            while ((linea = br.readLine()) != null) {
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
        for (Ciudad ciudad : parser.getCiudades()) {
            if (grafo.buscarCiudad(ciudad.getClave()) == null) {
                grafo.agregarCiudad(ciudad);
            }
        }

        // =========================
        // PASAR AEROLINEAS
        // =========================
        for (LineaAerea aerolinea : parser.getAerolineas()) {
            if (grafo.buscarLineaAereaClave(aerolinea.getClave()) == null) {
                grafo.agregarLineaAerea(aerolinea);
            }
        }

        // =========================
        // PASAR RUTAS AEREAS
        // =========================
        for (Ruta ruta : parser.getRutas()) {

            Ciudad origen = grafo.buscarCiudad(ruta.getclaveCiudadOrigen());
            Ciudad destino = grafo.buscarCiudad(ruta.getclaveCiudadDestino());

            // Solo agregar la ruta si ambas ciudades existen
            if (origen != null && destino != null) {
                origen.agregarRuta(ruta);     // modelo actual
                grafo.agregarRuta(ruta);      // catalogo general
            }
        }

        // =========================
        // PASAR CONSULTAS
        // =========================
        for (Consulta consulta : parser.getConsultas()) {
            grafo.agregarConsulta(consulta);
        }
    }

    /////////////////////////////////////////////////////////////////////////////////////////
    ///
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

    public List<String> viajeMenorPrecio(String claveCiudadOrigen, String claveCiudadDestino){

        List<String> camino = null;

        if(grafo.buscarCiudad(claveCiudadOrigen) != null && grafo.buscarCiudad(claveCiudadDestino) != null){

            AlgoritmoBusquedaCamino algoritmo = new AlgoritmoBusquedaCamino();
            camino = algoritmo.viajeMenorPrecio(grafo, claveCiudadOrigen, claveCiudadDestino);
        }

        return camino;
    }

    /////////////////////////////////////////////////////////////////////////////////////////
    /// 5 VIAJES MAS BARATOS
    /////////////////////////////////////////////////////////////////////////////////////////
    public List<Viaje> top5ViajesBaratos(String claveCiudadOrigen, String claveCiudadDestino){

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