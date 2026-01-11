import java.time.LocalDate;
import java.util.List;

/**
 * Controlador principal del grafo.
 * Orquesta carga de datos y ejecucion de consultas.
 */
public class ControladorGrafo {

    private final Grafo grafo;
    private final AlgoritmoBusquedaCamino algoritmo;

    public ControladorGrafo() {
        this.grafo = new Grafo();
        this.algoritmo = new AlgoritmoBusquedaCamino(grafo);
    }

    /* =========================
       CARGA DE DATOS
       ========================= */

    public void agregarCiudad(Ciudad ciudad) {
        grafo.agregarCiudad(ciudad);
    }

    public void agregarLineaAerea(LineaAerea linea) {
        grafo.agregarLineaAerea(linea);
    }

    public void agregarRuta(Ruta ruta) {
        grafo.agregarRuta(ruta);
    }

    /* =========================
       CONSULTAS
       ========================= */

    public List<Viaje> top5MasBaratos(String origen,
                                      String destino,
                                      LocalDate fechaSalida) {
        return algoritmo.top5MasBaratos(origen, destino, fechaSalida);
    }

    public List<Viaje> top5MenorDuracion(String origen,
                                         String destino,
                                         LocalDate fechaSalida) {
        return algoritmo.top5MenorDuracion(origen, destino, fechaSalida);
    }

    
    /* =========================
       LINEAS AEREAS (BAJA)
       ========================= */

    public void desactivarLineaAerea(String clave) {
        grafo.desactivarLineaAerea(clave);
    }

/* =========================
       UTILIDAD
       ========================= */

    public Grafo getGrafo() {
        return grafo;
    }
}
