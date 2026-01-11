/**
 * Representa una linea aerea.
 * Puede estar activa o inactiva.
 */
public class LineaAerea {

    private final String clave;   // AM, AA, etc.
    private final String nombre;  // Nombre descriptivo
    private boolean activa;

    /**
     * Constructor
     *
     * @param clave codigo de la aerolinea
     * @param nombre nombre de la aerolinea
     * @param activa estado inicial
     */
    public LineaAerea(String clave, String nombre, boolean activa) {
        this.clave = clave;
        this.nombre = nombre;
        this.activa = activa;
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

    public boolean isActiva() {
        return activa;
    }

    /* =========================
       CONTROL DE ESTADO
       ========================= */

    public void activar() {
        this.activa = true;
    }

    public void desactivar() {
        this.activa = false;
    }

    @Override
    public String toString() {
        return clave + " - " + nombre +
                (activa ? " (ACTIVA)" : " (INACTIVA)");
    }
}
