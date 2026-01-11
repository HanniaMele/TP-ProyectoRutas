import java.time.DayOfWeek;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.*;

/**
 * Algoritmo de busqueda de caminos considerando:
 * - Costo total
 * - Duracion total real (con esperas y escalas)
 * - Fechas reales
 * - Dias de operacion
 * - Escala minima de 1 hora
 * - Horarios locales con zona horaria por ciudad (ZoneId)
 *
 * NOTA:
 * Las rutas guardan horaSalida en horario local del ORIGEN y horaLlegada en horario local del DESTINO.
 * Para comparar/ordenar usamos instantes (Instant) derivados de ZonedDateTime.
 */
public class AlgoritmoBusquedaCamino {

    private final Grafo grafo;

    public AlgoritmoBusquedaCamino(Grafo grafo) {
        this.grafo = grafo;
    }

    /**
     * Estado interno del algoritmo (nodo de busqueda)
     */
    private static class Estado {
        String ciudad;
        ZonedDateTime fechaHoraActual;   // tiempo "real" en la zona horaria de la ciudad actual
        double costo;
        List<Ruta> rutas;
        Instant inicioViajeInstant;      // inicio real del viaje (primer despegue), en instant

        Estado(String ciudad,
               ZonedDateTime fechaHoraActual,
               double costo,
               List<Ruta> rutas,
               Instant inicioViajeInstant) {
            this.ciudad = ciudad;
            this.fechaHoraActual = fechaHoraActual;
            this.costo = costo;
            this.rutas = rutas;
            this.inicioViajeInstant = inicioViajeInstant;
        }
    }

    /* =========================================================
       TOP 5 MAS BARATOS
       ========================================================= */
    public List<Viaje> top5MasBaratos(String origen, String destino, LocalDate fechaSalida) {
        return buscar(origen, destino, fechaSalida, Comparator.comparingDouble(e -> e.costo));
    }

    /* =========================================================
       TOP 5 MENOR DURACION
       ========================================================= */
    public List<Viaje> top5MenorDuracion(String origen, String destino, LocalDate fechaSalida) {
        return buscar(origen, destino, fechaSalida,
                Comparator.comparing(e -> Duration.between(e.inicioViajeInstant, e.fechaHoraActual.toInstant())));
    }

    /* =========================================================
       BUSQUEDA GENERICA
       ========================================================= */
    private List<Viaje> buscar(String origen,
                               String destino,
                               LocalDate fechaSalida,
                               Comparator<Estado> criterio) {

        Ciudad ciudadOrigen = grafo.buscarCiudad(origen);
        if (ciudadOrigen == null) return Collections.emptyList();

        ZoneId zonaOrigen = ciudadOrigen.getZonaHoraria();

        PriorityQueue<Estado> cola = new PriorityQueue<>(criterio);
        List<Viaje> resultados = new ArrayList<>();

        // Comenzamos en el inicio del dia de salida, en zona horaria del origen
        ZonedDateTime inicioDia = fechaSalida.atStartOfDay(zonaOrigen);

        // inicioViajeInstant = null al inicio: lo fijamos cuando se tome la primera ruta
        cola.add(new Estado(origen, inicioDia, 0.0, new ArrayList<>(), null));

        // Para evitar explosion infinita por ciclos, limitamos profundidad de rutas (ajustable)
        final int MAX_TRAMOS = 8;

        while (!cola.isEmpty() && resultados.size() < 5) {

            Estado actual = cola.poll();

            if (actual.rutas.size() > MAX_TRAMOS) continue;

            // Llegamos al destino
            if (actual.ciudad.equals(destino) && !actual.rutas.isEmpty()) {
                Duration dur = Duration.between(actual.inicioViajeInstant, actual.fechaHoraActual.toInstant());
                resultados.add(new Viaje(actual.rutas, actual.costo, dur));
                continue;
            }

            Ciudad ciudadActual = grafo.buscarCiudad(actual.ciudad);
            if (ciudadActual == null) continue;

            for (Ruta ruta : ciudadActual.getRutas()) {

                // Linea aerea inactiva
                if (grafo.buscarLineaAereaInactiva(ruta.getClaveLineaAerea())) {
                    continue;
                }

                // Zonas horarias origen/destino
                Ciudad cOrigen = grafo.buscarCiudad(ruta.getClaveCiudadOrigen());
                Ciudad cDestino = grafo.buscarCiudad(ruta.getclaveCiudadDestino());
                if (cOrigen == null || cDestino == null) continue;

                ZoneId zonaO = cOrigen.getZonaHoraria();
                ZoneId zonaD = cDestino.getZonaHoraria();

                // Estamos parados en la ciudad actual: ajustamos estado a su zona
                ZonedDateTime tActual = actual.fechaHoraActual.withZoneSameInstant(zonaO);

                // Escala minima de 1 hora si ya hay rutas
                ZonedDateTime salidaMinima = tActual;
                if (!actual.rutas.isEmpty()) {
                    salidaMinima = salidaMinima.plusHours(1);
                }

                // Buscar siguiente salida valida
                ZonedDateTime salidaReal = calcularSalidaReal(
                        salidaMinima,
                        ruta.getHoraSalida(),
                        ruta,
                        zonaO
                );

                if (salidaReal == null) continue;

                // Determinar llegada real (en zona destino)
                ZonedDateTime llegadaReal = calcularLlegadaReal(
                        salidaReal,
                        ruta.getHoraLlegada(),
                        zonaD
                );

                List<Ruta> nuevasRutas = new ArrayList<>(actual.rutas);
                nuevasRutas.add(ruta);

                Instant inicioViaje = actual.inicioViajeInstant;
                if (inicioViaje == null) {
                    // el viaje inicia cuando tomas el primer vuelo (salidaReal)
                    inicioViaje = salidaReal.toInstant();
                }

                cola.add(new Estado(
                        ruta.getclaveCiudadDestino(),
                        llegadaReal,
                        actual.costo + ruta.getCosto(),
                        nuevasRutas,
                        inicioViaje
                ));
            }
        }

        return resultados;
    }

    /* =========================================================
       CALCULO DE HORARIOS (con zonas horarias)
       ========================================================= */

    private ZonedDateTime calcularSalidaReal(ZonedDateTime base,
                                             LocalTime horaSalida,
                                             Ruta ruta,
                                             ZoneId zonaOrigen) {

        LocalDate fecha = base.toLocalDate();

        for (int i = 0; i < 7; i++) {
            LocalDate candidata = fecha.plusDays(i);
            DayOfWeek dia = candidata.getDayOfWeek();

            if (!ruta.operaEnDia(dia)) continue;

            ZonedDateTime salida = ZonedDateTime.of(candidata, horaSalida, zonaOrigen);

            if (!salida.isBefore(base)) {
                return salida;
            }
        }
        return null;
    }

    private ZonedDateTime calcularLlegadaReal(ZonedDateTime salidaOrigen,
                                              LocalTime horaLlegada,
                                              ZoneId zonaDestino) {

        // Convertimos el instante de salida a la zona destino para obtener la "fecha" local de referencia
        ZonedDateTime salidaEnDestino = salidaOrigen.withZoneSameInstant(zonaDestino);
        LocalDate fechaDestino = salidaEnDestino.toLocalDate();

        ZonedDateTime llegada = ZonedDateTime.of(fechaDestino, horaLlegada, zonaDestino);

        // Si la llegada (instant) no es posterior al despegue, es al dia siguiente en destino
        if (!llegada.toInstant().isAfter(salidaOrigen.toInstant())) {
            llegada = llegada.plusDays(1);
        }
        return llegada;
    }
}
