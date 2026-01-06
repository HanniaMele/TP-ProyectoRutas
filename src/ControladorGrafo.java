import java.util.List;

public class ControladorGrafo {

    Grafo grafo;

    public ControladorGrafo(){
        grafo = new Grafo();
    }

    public boolean agregarCiudad(String clave, String nombreCiudad, String estado, String pais, String continente){

        boolean resultado = false;

        if(grafo.buscarCiudad(clave) == null){

            Ciudad nuevaCiudad = new Ciudad(clave,nombreCiudad,estado,pais,continente);
            grafo.agregarCiudad(nuevaCiudad);
            resultado = true;
        }

        return resultado;
    }

    public boolean agregarRuta(String claveAerolinea, int numeroRuta, String claveCiudadOrigen, String claveCiudadDestino, double costo, double horaSalida, double horaLlegada, String frecuencia){

        boolean resultado = false;

        if(grafo.buscarCiudad(claveCiudadOrigen) != null && grafo.buscarCiudad(claveCiudadDestino) != null){

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
