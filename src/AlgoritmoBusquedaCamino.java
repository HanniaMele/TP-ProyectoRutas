import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
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

    public List<Viaje> primerosCincoCaminos (Grafo grafo, String origen, String destino) {

        //Cola de prioridad para explorar caminos, de manera que siempre esten ordenados por menor costo
        PriorityQueue<Viaje> cola = new PriorityQueue<>();

        //Se agrega un nuevo objeto Viaje a la cola, este solo tiene un lugar (origen) en su lista de lugares y de costo 0.0
        cola.add(new Viaje(Arrays.asList(origen), 0.0));

        //Lista de objetos Viajes correspondiente al resultado, que tendra los 5 viajes mas baratos
        List<Viaje> resultado = new ArrayList<>();

        //Mientras cola no este vacia (es decir que existan Viajes incompletos para revisar posibles rutas) y aun no se obtengan los 5 Viajes menos costosos
        while (!cola.isEmpty() && resultado.size() < 5) {

            //Saca de la cola el Viaje en la cabecera
            Viaje viajeActual = cola.poll();

            //Obtiene la lista <String> correspondiente a las ciudades de ese Viaje en construccion
            List<String> caminoActual = viajeActual.getCiudades();

            //Obtiene la ultima ciudad que se visita en ese Viaje
            String ultimaCiudad = caminoActual.get(caminoActual.size() - 1);

            //Si la ultima ciudad que se visita es el destino deseado, añadimos el Viaje al resultado y se continua con el ciclo
            if (ultimaCiudad.equals(destino)) {
                resultado.add(viajeActual);
                continue;
            }

            //Se obtiene el objeto Ciudad de la ultima ciudad que se visita para revisar sus rutas
            Ciudad ciudadActual = grafo.buscarCiudad(ultimaCiudad);

            //Por cada ruta se crea un nuevo Viaje con su correspondiente costo. En caso de no haber ruta entonces ese Viaje no puede llevar al destino y no genera nuevos potenciales Viajes
            for (Ruta ruta : ciudadActual.getRutas()) {

                //Se obtiene el destino de la ruta
                String siguienteCiudad = ruta.getclaveCiudadDestino();

                //Para evitar que el algoritmo se atrape en un ciclo se considera que la ciudad destino no este actualmente en el camino del Viaje
                if (!caminoActual.contains(siguienteCiudad)) {

                    //Se crea nueva lista que corresponde al camino que se seguira en el nuevo objeto Viaje
                    List<String> nuevoCamino = new ArrayList<>(caminoActual);

                    //Se le agrega el destino de la ruta al nuevo camino
                    nuevoCamino.add(siguienteCiudad);

                    //Se calcula el costo correspondiente a este nuevo potencial Viaje
                    double nuevoCosto = viajeActual.getCostoTotal() + ruta.getCosto();

                    //El potencial Viaje se agrega a la cola de prioridad
                    cola.add(new Viaje(nuevoCamino, nuevoCosto));
                }
            }
        }

        return resultado;
    } 
}
