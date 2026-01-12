
/* MODIFICADO:  se a cambiado el tipo de variable de horaSalida y horaLlegada de double a LocalTime
 */

import java.time.LocalTime;
public class Ruta {

    private String claveLineaAerea;
    private int numeroRuta;
    private String claveCiudadOrigen;
    private String claveCiudadDestino;
    private double costo;
    private LocalTime horaSalida;
    private LocalTime horaLlegada;
    private String frecuencia;

    public Ruta(String claveLineaAerea, int numeroRuta, String claveCiudadOrigen, String claveCiudadDestino, double costo, LocalTime horaSalida, LocalTime horaLlegada, String frecuencia) {
        this.claveLineaAerea = claveLineaAerea;
        this.numeroRuta = numeroRuta;
        this.claveCiudadOrigen = claveCiudadOrigen;
        this.claveCiudadDestino = claveCiudadDestino;
        this.costo = costo;
        this.horaSalida = horaSalida;
        this.horaLlegada = horaLlegada;
        this.frecuencia = frecuencia;
    }

    public String getClaveLineaAerea() {
        return claveLineaAerea;
    }

    public String getclaveCiudadDestino(){
        return claveCiudadDestino;
    }

    public double getCosto(){
        return costo;
    }

    public String getclaveCiudadOrigen() {
        return claveCiudadOrigen;
    }

}