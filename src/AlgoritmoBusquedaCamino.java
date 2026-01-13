/*
 * Titulo: AlgoritmoBusquedaCamino
 * Descripción: Clase que realiza la búsqueda de caminos dentro del grafo
 * @autor: Jared Eliezer Baldenegro Gomez; Jimer Orlando Diaz Murillo y Hannia Melendres Samaniego
 * Materia: Tecnologías de programación
 * Profesor: Dra. María Lucia Barrón Estrada
 * Fecha: 07-01-2026
 *
 * MODIFICACION: la variable "menorDistancia" del metodo "viajeMenorPrecio" se le a cambiado el nombre por "menorCostoDistancia"
 * 
 * MODIFICACION: en el metodo "Top5ViajesBaratos" se a modificado el if (ultimaCiudad.equals(destino)) por
 * if (ultimaCiudad.getClave().equals(destino.getClave()))
 * Esto porque equals compara referencias. Con objetos pregunta si ambos apuntan al mismo objeto, como no es asi, nunca se
 * cumple el condicional
 * 
 * MODIFICACION: Se modifico el if (!caminoActual.contains(siguienteCiudad))
 * y en su lugar se puso if (!caminoActualcontieneCiudad(caminoActual, siguienteCiudad)) porque contains tiene problemas al
 * utilizar objetos, ya que depende de "equals"
 * 
 * MODIFICACION: Se agrego el metodo "caminoActualcontieneCiudad" para resolver el problema de "contains" en el if anteriormente
 * descrito
 * 
 * 
 * MODIFICACION: SE A AGREGADO LA LINEA System.out.println("Expandiendo desde: " + ultimaCiudad.getClave() +" | rutas: " + ultimaCiudad.getRutas().size());
 * PARA SABER PORQUE NO DESARROLLABA EL TOP5
 * 
 * EL PROBLEMA PUEDE SER:
 * 
 * Este if está bloqueando todas las rutas: if (!grafo.buscarLineaAereaInactiva(ruta.getClaveLineaAerea())) {
Y eso significa una sola cosa posible:
buscarLineaAereaInactiva(...) está devolviendo true para TODAS las rutas
O sea:
para el algoritmo, todas las aerolíneas están inactivas
 

 MODIFICACION: SE A AGREGADO LA SIGUIENTE LINEA
 System.out.println("Ruta " + ruta.getClaveLineaAerea() +" activa? " +!grafo.buscarLineaAereaInactiva(ruta.getClaveLineaAerea()));


 y SE HA DESCUBIERTO QUE IMPRIME:
 ----------------------------------
Consulta: MEX -> MZT
Camino más barato: [MEX, MZT]
Expandiendo desde: MEX | rutas: 25
Ruta AM activa? false
Ruta AM activa? false
Ruta AM activa? false
Ruta AM activa? false
Ruta AM activa? false
Ruta AM activa? false
Ruta AM activa? false
Ruta IT activa? false
Ruta IT activa? false
Ruta IT activa? false
Ruta IT activa? false
Ruta IT activa? false
Ruta IT activa? false
Ruta IT activa? false
Ruta CN activa? false
Ruta AM activa? false
Ruta DL activa? false
Ruta DL activa? false
Ruta DL activa? false
Ruta DL activa? false
Ruta DL activa? false
Ruta DL activa? false
Ruta DL activa? false
Ruta DL activa? false
Ruta AF activa? false
Top 5: No disponible.

 El if(!grafo.buscarLineaAereaInactiva(ruta.getClaveLineaAerea()))
 hace que todas las lineas se consideren inactivas y por ende no trabaja

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
            if (ultimaCiudad.getClave().equals(destino.getClave())) {
                resultado.add(viajeActual);
                continue;
            }

            ///////////////////////////////////////////////////////////////////
            //////////Linea DEBUG para revisar si funciona la generacion de 5 rutas
            //System.out.println("Expandiendo desde: " + ultimaCiudad.getClave() +" | rutas: " + ultimaCiudad.getRutas().size());
            ///////////////////////////////////////////////////////////////////
            //Por cada ruta en ultimaCiudad se crea un nuevo Viaje con su correspondiente costo. En caso de no haber ruta entonces ese Viaje no puede llevar al destino y no genera nuevos potenciales Viajes
            for (Ruta ruta : ultimaCiudad.getRutas()) {

                ////////////////////////////////////////////////////////////////
                /// LINEA DEBUG PARA SABER COMO SE GENERAN LAS LINEAS (SI TRUE O FALSE)
                //System.out.println("Ruta " + ruta.getClaveLineaAerea() +" activa? " +!grafo.buscarLineaAereaInactiva(ruta.getClaveLineaAerea()));
                ////////////////////////////////////////////////////////////////
                //Si esa linea no esta activa no se considera
                if(grafo.buscarLineaAereaInactiva(ruta.getClaveLineaAerea())) continue;

                //Se obtiene el destino de la ruta
                String claveSiguienteCiudad = ruta.getclaveCiudadDestino();

                //Se obtiene el objeto Ciudad del destino de la ruta
                Ciudad siguienteCiudad = grafo.buscarCiudad(claveSiguienteCiudad);

                //Para evitar que el algoritmo se atrape en un ciclo se considera que la ciudad destino de la ruta no este actualmente en el camino actual
                if (caminoActualcontieneCiudad(caminoActual, siguienteCiudad)) continue;

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

        return resultado;
    }


    private boolean caminoActualcontieneCiudad (List<Ciudad> caminoActual, Ciudad siguienteCiudad) {

        boolean yaVisitada = false;
        for (Ciudad ciudad : caminoActual) {
            if (ciudad.getClave().equals(siguienteCiudad.getClave())) {
                yaVisitada = true;
                break;
            }
        }

        return yaVisitada;
    }
}