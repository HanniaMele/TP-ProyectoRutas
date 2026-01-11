import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.Arrays;

/**
 * Representa una ruta (arista del grafo).
 * La hora de salida esta en horario local de la ciudad origen,
 * y la hora de llegada en horario local de la ciudad destino.
 */
public class Ruta {

    private final String idRuta;
    private final String claveLineaAerea;
    private final String claveCiudadOrigen;
    private final String claveCiudadDestino;
    private final double costo;
    private final LocalTime horaSalida;
    private final LocalTime horaLlegada;

    /**
     * Frecuencia semanal.
     * Indice 1 = lunes ... 7 = domingo
     */
    private final boolean[] diasOperacion = new boolean[8];

    /**
     * @param idRuta identificador de la ruta (ej. 1200)
     * @param claveLineaAerea codigo de aerolinea (AA, AM, etc.)
     * @param claveCiudadOrigen ciudad origen (MEX, CUL, etc.)
     * @param claveCiudadDestino ciudad destino
     * @param costo costo de la ruta
     * @param horaSalida hora local en la ciudad origen
     * @param horaLlegada hora local en la ciudad destino
     * @param frecuencia cadena tipo "1234567"
     */
    public Ruta(String idRuta,
                String claveLineaAerea,
                String claveCiudadOrigen,
                String claveCiudadDestino,
                double costo,
                LocalTime horaSalida,
                LocalTime horaLlegada,
                String frecuencia) {

        this.idRuta = idRuta;
        this.claveLineaAerea = claveLineaAerea;
        this.claveCiudadOrigen = claveCiudadOrigen;
        this.claveCiudadDestino = claveCiudadDestino;
        this.costo = costo;
        this.horaSalida = horaSalida;
        this.horaLlegada = horaLlegada;

        interpretarFrecuencia(frecuencia);
    }

    /* =========================
       LOGICA DE FRECUENCIA
       ========================= */

    private void interpretarFrecuencia(String frecuencia) {
        Arrays.fill(diasOperacion, false);

        if (frecuencia == null) return;

        for (char c : frecuencia.toCharArray()) {
            int dia = c - '0';
            if (dia >= 1 && dia <= 7) {
                diasOperacion[dia] = true;
            }
        }
    }

    /**
     * Indica si la ruta opera en el dia dado
     */
    public boolean operaEnDia(DayOfWeek dia) {
        return diasOperacion[dia.getValue()];
    }

    /* =========================
       GETTERS
       ========================= */

    public String getIdRuta() {
        return idRuta;
    }

    public String getClaveLineaAerea() {
        return claveLineaAerea;
    }

    public String getClaveCiudadOrigen() {
        return claveCiudadOrigen;
    }

    public String getclaveCiudadDestino() {
        return claveCiudadDestino;
    }

    public double getCosto() {
        return costo;
    }

    public LocalTime getHoraSalida() {
        return horaSalida;
    }

    public LocalTime getHoraLlegada() {
        return horaLlegada;
    }

    /* =========================
       UTILIDAD
       ========================= */

    @Override
    public String toString() {
        return claveLineaAerea + " " + idRuta + " "
                + claveCiudadOrigen + " -> " + claveCiudadDestino
                + " $" + String.format("%.2f", costo)
                + " " + horaSalida + "-" + horaLlegada;
    }
}
