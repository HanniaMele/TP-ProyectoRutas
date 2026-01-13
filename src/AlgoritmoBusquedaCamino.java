/*
 *
 * MODIFICACION: la variable "menorDistancia" del metodo "viajeMenorPrecio" se le a cambiado el nombre por "menorCostoDistancia"
 *  
 */


import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;

public class AlgoritmoBusquedaCamino {

    //Metodo que obtiene el viaje con menor precio
    // origen = clave de la ciudad de origen
    // destino = clave de la ciudad de destino
    public List<String> viajeMenorPrecio(Grafo grafo, String origen, String destino) {

        // Map para guardar las ciudades del grafo con su costo mas barato
        Map<String, Double> menorCostoPorCiudad = new HashMap<>();

        //Map para guardar la ciudad anterior mas conveniente de cada ciudad
        Map<String, String> anteriorMasBarato = new HashMap<>();

        //Set para guardar las ciudades que se marcan como visitadas
        Set<String> ciudadesVisitadas = new HashSet<>();

        //Todas las ciudades del grafo se añaden al map menorCostoPorCiudad con un costo de infinito
        for (Ciudad ciudad : grafo.getCiudades()) {
            menorCostoPorCiudad.put(ciudad.getClave(), Double.POSITIVE_INFINITY);
        }

        //Se establece al origen un costo de 0 en el map menorCostoPorCiudad
        menorCostoPorCiudad.put(origen, 0.0);

        //Se establce a actual como origen
        String actual = origen;

        //Ciclo para revisar todas las rutas de las ciudades y obtener asi la relacion del map anteriores
        while (actual != null) {

            //Se obtiene la Ciudad correspondiente a actual
            Ciudad ciudadActual = grafo.buscarCiudad(actual);

            // Ciclo para todas la rutas de ciudadActual
            // Toma una ruta a la vez y la va comparando
            for (Ruta ruta : ciudadActual.getRutas()) {

                //Si la ciudad destino de la ruta no esta marcada como visitada avanza
                if (!ciudadesVisitadas.contains(ruta.getclaveCiudadDestino())) {

                    //Se calcula el nuevo costo al sumar el costo de esta ruta con el acumulado actual
                    double nuevoCosto = menorCostoPorCiudad.get(actual) + ruta.getCosto();

                    // Si el nuevo costo es menor que un posible costo registrado anteriormente para llegar a la ciudad destino 
                    // de esta ruta en el map menorCostoPorCiudad, entonces se reemplaza el costo anterior por este
                    if (nuevoCosto < menorCostoPorCiudad.get(ruta.getclaveCiudadDestino())) {

                        //Se reemplaza el costo anterior por uno mas barato
                        menorCostoPorCiudad.put(ruta.getclaveCiudadDestino(), nuevoCosto);

                        //La actualizacion del costo implica que entonces tambien debe actualizarse desde que ciudad es mas barato llegar a este destino
                        anteriorMasBarato.put(ruta.getclaveCiudadDestino(), actual);
                    }
                }
            }

            //Despues de recorrer todas las rutas se marca la ciudad como visitada
            ciudadesVisitadas.add(actual);

            //Se establece actual en null, para terminar con el ciclo en caso de que ya no queden ciudades por visitar
            actual = null;

            //Se establece menorDistancia como infinito para posteriormente buscar un costo menor
            double menorCostoDistancia = Double.POSITIVE_INFINITY;

            //Se recorre los keys del map menorCostoPorCiudad
            for (String ciudad : menorCostoPorCiudad.keySet()) {

                //Si aun hay ciudades sin visitar y que es posible llegar a ellas, se obtiene la de menor costo acumulado
                if (!ciudadesVisitadas.contains(ciudad) && menorCostoPorCiudad.get(ciudad) < menorCostoDistancia) {

                    menorCostoDistancia = menorCostoPorCiudad.get(ciudad);

                    //La ciudad se guarda en actual para revisar sus rutas
                    actual = ciudad;
                }
            }
        }

        //Despues de terminar se reconstruye el camino mas barato comenzando en el destino para ir obteniendo las ciudades anteriores mas convenientes
        List<String> camino = new ArrayList<>();

        //Se comienza con ciudad destino
        String ciudad = destino;

        while (ciudad != null) {

            //Se agregan a la lista en el indice 0 y recorriendose para asi obtener el viaje ordenado
            camino.add(0, ciudad);

            //ciudad se llena ahora con la ciudad anterior mas conveniente hasta llegar a la ciudad origen
            ciudad = anteriorMasBarato.get(ciudad);
        }

        return camino;
    }

    //Metodo que obtiene el top 5 de viajes menos costosos
    public List<Viaje> top5ViajesBaratos (Grafo grafo, Ciudad origen, Ciudad destino) {

        //Cola de prioridad para explorar caminos, de manera que siempre esten ordenados por menor costo
        PriorityQueue<Viaje> cola = new PriorityQueue<>();

        //Se agrega un nuevo objeto Viaje a la cola, este solo tiene un lugar (origen) en su lista de lugares y de costo 0.0
        cola.add(new Viaje(Arrays.asList(origen), 0.0));

        //Lista de objetos Viajes correspondiente al resultado, que tendra los 5 viajes menos costosos
        List<Viaje> resultado = new ArrayList<>();

        //Mientras cola no este vacia (es decir que existan Viajes incompletos para revisar posibles rutas) y aun no se obtengan los 5 Viajes menos costosos
        while (!cola.isEmpty() && resultado.size() < 5) {

            //Saca de la cola el Viaje en la cabecera
            Viaje viajeActual = cola.poll();

            //Obtiene la lista <Ciudad> correspondiente a las ciudades de ese Viaje en construccion
            List<Ciudad> caminoActual = viajeActual.getCiudades();

            //Obtiene la ultima ciudad que se visita en ese Viaje
            Ciudad ultimaCiudad = caminoActual.get(caminoActual.size() - 1);

            //Si la ultima ciudad que se visita es el destino deseado, añadimos el Viaje al resultado y se continua con el ciclo
            if (ultimaCiudad.equals(destino)) {
                resultado.add(viajeActual);
                continue;
            }

            //Por cada ruta en ultimaCiudad se crea un nuevo Viaje con su correspondiente costo. En caso de no haber ruta entonces ese Viaje no puede llevar al destino y no genera nuevos potenciales Viajes
            for (Ruta ruta : ultimaCiudad.getRutas()) {

                //Si esa linea no esta activa no se considera
                if(!grafo.buscarLineaAereaInactiva(ruta.getClaveLineaAerea())){

                    //Se obtiene el destino de la ruta
                    String claveSiguienteCiudad = ruta.getclaveCiudadDestino();

                    //Se obtiene el objeto Ciudad del destino de la ruta
                    Ciudad siguienteCiudad = grafo.buscarCiudad(claveSiguienteCiudad);

                    //Para evitar que el algoritmo se atrape en un ciclo se considera que la ciudad destino de la ruta no este actualmente en el camino actual
                    if (!caminoActual.contains(siguienteCiudad)) {

                        //Se crea nueva lista que corresponde al camino que se seguira en el nuevo objeto Viaje
                        List<Ciudad> nuevoCamino = new ArrayList<>(caminoActual);

                        //Se le agrega la ciudad destino de la ruta al nuevo camino
                        nuevoCamino.add(siguienteCiudad);

                        //Se calcula el costo correspondiente a este nuevo potencial Viaje
                        double nuevoCosto = viajeActual.getCostoTotal() + ruta.getCosto();

                        //El potencial Viaje se agrega a la cola de prioridad
                        cola.add(new Viaje(nuevoCamino, nuevoCosto));
                    }

                }

            }
        }

        return resultado;
    }
}