import java.time.Duration;
import java.util.Collections;
import java.util.List;

/**
 * Representa un viaje completo (itinerario),
 * compuesto por una o mas rutas.
 */
public class Viaje {

    private final List<Ruta> rutas;
    private final double costoTotal;
    private final Duration duracionTotal;

    /**
     * Constructor principal
     *
     * @param rutas lista de rutas que componen el viaje
     * @param costoTotal costo acumulado
     * @param duracionTotal duracion real del viaje
     */
    public Viaje(List<Ruta> rutas, double costoTotal, Duration duracionTotal) {
        this.rutas = rutas;
        this.costoTotal = costoTotal;
        this.duracionTotal = duracionTotal;
    }

    /* =========================
       GETTERS
       ========================= */

    public List<Ruta> getRutas() {
        return Collections.unmodifiableList(rutas);
    }

    public double getCostoTotal() {
        return costoTotal;
    }

    public Duration getDuracionTotal() {
        return duracionTotal;
    }

    /* =========================
       UTILIDADES
       ========================= */

    public long getDuracionEnMinutos() {
        return duracionTotal.toMinutes();
    }

    public int getNumeroEscalas() {
        return Math.max(0, rutas.size() - 1);
    }

    /* =========================
       COMPARADORES
       ========================= */

    public static int compararPorCosto(Viaje v1, Viaje v2) {
        return Double.compare(v1.costoTotal, v2.costoTotal);
    }

    public static int compararPorDuracion(Viaje v1, Viaje v2) {
        return v1.duracionTotal.compareTo(v2.duracionTotal);
    }

    /* =========================
       REPRESENTACION TEXTO
       ========================= */

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("Costo: $").append(String.format("%.2f", costoTotal)).append("\n");
        sb.append("Duracion: ").append(formatearDuracion()).append("\n");
        sb.append("Escalas: ").append(getNumeroEscalas()).append("\n");
        sb.append("Rutas:\n");

        for (Ruta r : rutas) {
            sb.append("  - ")
                    .append(r.getClaveLineaAerea()).append(" ")
                    .append(r.getIdRuta()).append(" ")
                    .append(r.getClaveCiudadOrigen())
                    .append(" -> ")
                    .append(r.getclaveCiudadDestino())
                    .append(" $")
                    .append(String.format("%.2f", r.getCosto()))
                    .append("\n");
        }

        return sb.toString();
    }

    private String formatearDuracion() {
        long minutos = duracionTotal.toMinutes();
        long horas = minutos / 60;
        long resto = minutos % 60;
        return horas + "h " + resto + "m";
    }
}
