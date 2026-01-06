/*
 * Titulo: Ruta
 * Descripcion: Clase que representa una ruta con su clave, número de ruta, ciudades de origen y destino, costo, hora de salida y llegada, y frecuencia semanal.
 * @autor: Jared Eliezer Baldenegro Gomez; Jimer Orlando Diaz Murillo y Hannia
 * Materia: tecnologias de programacion
 * Profesor: Dra. María Lucia Barrón Estrada
 * Fecha: 07-01-2026
 * 
 * 
 */
import java.time.LocalTime;
public class Ruta {

    private String claveAerolinea;
    private int numeroRuta;
    private String claveCiudadOrigen;
    private String claveCiudadDestino;
    private double costo;
    private LocalTime horaSalida;
    private LocalTime horaLlegada;
    private String frecuencia;

    public Ruta(String claveAerolinea, int numeroRuta, String claveCiudadOrigen, String claveCiudadDestino, double costo, LocalTime horaSalida, LocalTime horaLlegada, String frecuencia) {
        this.claveAerolinea = claveAerolinea;
        this.numeroRuta = numeroRuta;
        this.claveCiudadOrigen = claveCiudadOrigen;
        this.claveCiudadDestino = claveCiudadDestino;
        this.costo = costo;
        this.horaSalida = horaSalida;
        this.horaLlegada = horaLlegada;
        this.frecuencia = frecuencia;
    }

    public String getclaveCiudadDestino(){
        return claveCiudadDestino;
    }

    public double getCosto(){
        return costo;
    }
    
}
