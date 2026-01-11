import java.time.LocalDate;

/**
 * Representa una consulta de viaje.
 * Puede ser solo ida o ida y vuelta.
 */
public class Consulta {

    private final String ciudadOrigen;
    private final String ciudadDestino;
    private final LocalDate fechaSalida;
    private final LocalDate fechaRegreso; // null si es solo ida

    /**
     * Constructor para consulta solo ida
     */
    public Consulta(String ciudadOrigen,
                    String ciudadDestino,
                    LocalDate fechaSalida) {
        this(ciudadOrigen, ciudadDestino, fechaSalida, null);
    }

    /**
     * Constructor para consulta ida y vuelta
     */
    public Consulta(String ciudadOrigen,
                    String ciudadDestino,
                    LocalDate fechaSalida,
                    LocalDate fechaRegreso) {
        this.ciudadOrigen = ciudadOrigen;
        this.ciudadDestino = ciudadDestino;
        this.fechaSalida = fechaSalida;
        this.fechaRegreso = fechaRegreso;
    }

    /* =========================
       GETTERS
       ========================= */

    public String getCiudadOrigen() {
        return ciudadOrigen;
    }

    public String getCiudadDestino() {
        return ciudadDestino;
    }

    public LocalDate getFechaSalida() {
        return fechaSalida;
    }

    public LocalDate getFechaRegreso() {
        return fechaRegreso;
    }

    /* =========================
       UTILIDAD
       ========================= */

    public boolean esIdaYVuelta() {
        return fechaRegreso != null;
    }

    public boolean fechasValidas() {
        if (fechaSalida == null) return false;
        if (fechaRegreso == null) return true;
        return !fechaRegreso.isBefore(fechaSalida);
    }

    @Override
    public String toString() {
        if (esIdaYVuelta()) {
            return ciudadOrigen + " -> " + ciudadDestino +
                    " (" + fechaSalida + " / " + fechaRegreso + ")";
        }
        return ciudadOrigen + " -> " + ciudadDestino +
                " (" + fechaSalida + ")";
    }
}

