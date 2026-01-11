import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Representa una ciudad (vertice del grafo).
 */
public class Ciudad {

    private final String clave;          // MEX, CUL, etc.
    private final String nombre;         // opcional
    private final String pais;           // opcional
    private final ZoneId zonaHoraria;

    private final List<Ruta> rutas = new ArrayList<>();

    /**
     * Constructor basico
     */
    public Ciudad(String clave, String nombre, String pais, ZoneId zonaHoraria) {
        this.clave = clave;
        this.nombre = nombre;
        this.pais = pais;
        this.zonaHoraria = zonaHoraria;
    }

    /**
     * Constructor minimo (si tu parser no manda nombre/pais)
     */
    public Ciudad(String clave, ZoneId zonaHoraria) {
        this(clave, "", "", zonaHoraria);
    }

    /* =========================
       MANEJO DE RUTAS
       ========================= */

    public void agregarRuta(Ruta ruta) {
        if (ruta != null) {
            rutas.add(ruta);
        }
    }

    public List<Ruta> getRutas() {
        return Collections.unmodifiableList(rutas);
    }

    /* =========================
       GETTERS
       ========================= */

    public String getClave() {
        return clave;
    }

    public String getNombre() {
        return nombre;
    }

    public String getPais() {
        return pais;
    }

    public ZoneId getZonaHoraria() {
        return zonaHoraria;
    }

    /* =========================
       UTILIDAD
       ========================= */

    @Override
    public String toString() {
        return clave + " (" + zonaHoraria.getId() + ")";
    }
}
