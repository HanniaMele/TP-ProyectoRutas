import java.util.List;

public class ControladorGrafo {

    public Grafo grafo;

    public ControladorGrafo(){
        grafo = new Grafo();
    }

    public ControladorGrafo(Grafo grafo) {
        this.grafo = grafo;
    }

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

    public boolean agregarRuta(String claveAerolinea, int numeroRuta, String claveCiudadOrigen, String claveCiudadDestino, double costo, double horaSalida, double horaLlegada, String frecuencia){

        boolean resultado = false;

        if(grafo.buscarCiudad(claveCiudadOrigen) != null && grafo.buscarCiudad(claveCiudadDestino) != null && grafo.buscarLineaAereaClave(claveAerolinea)!=null){

            Ciudad ciudadParaNuevaRuta = grafo.buscarCiudad(claveCiudadOrigen);
            Ruta nuevaRuta = new Ruta(claveAerolinea,numeroRuta,claveCiudadOrigen,claveCiudadDestino,costo,horaSalida,horaLlegada,frecuencia);
            ciudadParaNuevaRuta.agregarRuta(nuevaRuta);
            resultado = true;
        }

        return resultado;
    }

    public List<String> viajeMenorPrecio(String claveCiudadOrigen, String claveCiudadDestino){

        List<String> camino = null;

        if(grafo.buscarCiudad(claveCiudadOrigen) != null && grafo.buscarCiudad(claveCiudadDestino) != null){

            AlgoritmoBusquedaCamino algoritmo = new AlgoritmoBusquedaCamino();
            camino = algoritmo.viajeMenorPrecio(grafo, claveCiudadOrigen, claveCiudadDestino);
        }

        return camino;
    }

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