import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class AlgoritmoBusquedaCamino {

public List<String> caminoMasCorto(Grafo grafo, String origen, String destino) {
        // Inicialización
        Map<String, Double> costos = new HashMap<>();
        Map<String, String> anteriores = new HashMap<>();
        Set<String> visitados = new HashSet<>();

        for (Ciudad ciudad : grafo.getCiudades()) {
            costos.put(ciudad.getClave(), Double.POSITIVE_INFINITY);
        }

        costos.put(origen, 0.0);

        String actual = origen;

        while (actual != null) {
            // Paso 2: Actualizar distancias a adyacentes
            Ciudad ciudadActual = grafo.buscarCiudad(actual);
            for (Ruta ruta : ciudadActual.getRutas()) {
                if (!visitados.contains(ruta.getclaveCiudadDestino())) {
                    double nuevoCosto = costos.get(actual) + ruta.getCosto();
                    if (nuevoCosto < costos.get(ruta.getclaveCiudadDestino())) {
                        costos.put(ruta.getclaveCiudadDestino(), nuevoCosto);
                        anteriores.put(ruta.getclaveCiudadDestino(), actual);
                    }
                }
            }

            // Paso 3: Marcar como visitado
            visitados.add(actual);

            // Paso 4: Seleccionar el nodo con menor distancia no visitado
            actual = null;
            double menorDistancia = Double.POSITIVE_INFINITY;
            for (String nodo : costos.keySet()) {
                if (!visitados.contains(nodo) && costos.get(nodo) < menorDistancia) {
                    menorDistancia = costos.get(nodo);
                    actual = nodo;
                }
            }
        }

        // Reconstruir el camino
        List<String> camino = new ArrayList<>();
        String nodo = destino;
        while (nodo != null) {
            camino.add(0, nodo);
            nodo = anteriores.get(nodo);
        }

        return camino;
    }
}
