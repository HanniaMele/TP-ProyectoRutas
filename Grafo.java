import java.util.HashMap;
import java.util.Map;

/**
 * Representa el grafo dirigido de rutas.
 * Ciudades = vertices
 * Rutas = aristas dirigidas
 */
public class Grafo {

    private final Map<String, Ciudad> ciudades = new HashMap<>();
    private final Map<String, LineaAerea> lineasAereas = new HashMap<>();

    /* =========================
       CIUDADES
       ========================= */

    public void agregarCiudad(Ciudad ciudad) {
        if (ciudad != null) {
            ciudades.putIfAbsent(ciudad.getClave(), ciudad);
        }
    }

    public Ciudad buscarCiudad(String clave) {
        return ciudades.get(clave);
    }

    public boolean existeCiudad(String clave) {
        return ciudades.containsKey(clave);
    }

    /* =========================
       RUTAS
       ========================= */

    public void agregarRuta(Ruta ruta) {
        if (ruta == null) return;

        Ciudad origen = ciudades.get(ruta.getClaveCiudadOrigen());
        if (origen != null) {
            origen.agregarRuta(ruta);
        }
    }

    /* =========================
       LINEAS AEREAS
       ========================= */

    public void agregarLineaAerea(LineaAerea linea) {
        if (linea != null) {
            lineasAereas.putIfAbsent(linea.getClave(), linea);
        }
    }

    public LineaAerea buscarLineaAerea(String clave) {
        return lineasAereas.get(clave);
    }

    public void desactivarLineaAerea(String clave) {
        LineaAerea la = lineasAereas.get(clave);
        if (la != null) {
            la.desactivar();
        }
    }


    /**
     * @return true si la linea aerea esta inactiva
     */
    public boolean buscarLineaAereaInactiva(String clave) {
        LineaAerea la = lineasAereas.get(clave);
        return la != null && !la.isActiva();
    }

    /* =========================
       UTILIDAD
       ========================= */

    @Override
    public String toString() {
        return "Grafo{ciudades=" + ciudades.size()
                + ", lineasAereas=" + lineasAereas.size() + "}";
    }
}
