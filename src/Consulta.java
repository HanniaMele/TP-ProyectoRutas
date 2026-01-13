/*
 * Titulo: Consulta
 * Descripción: Clase que representa una consulta de viaje con origen, destino, fecha de salida y fecha de regreso.
 * @autor: Jared Eliezer Baldenegro Gomez; Jimer Orlando Diaz Murillo y Hannia Melendres Samaniego
 * Materia: Tecnologías de programación
 * Profesor: Dra. María Lucia Barrón Estrada
 * Fecha: 07-01-2026
 */

public class Consulta {

    //atributos
    private final String origen;
    private final String destino;
    private final String fechaSalida;
    private final String fechaRegreso;

    //constructor
    public Consulta(String origen, String destino, String fechaSalida, String fechaRegreso) {
        this.origen = origen;
        this.destino = destino;
        this.fechaSalida = fechaSalida;
        this.fechaRegreso = fechaRegreso;
    }

    //getters
    public String getOrigen() { return origen; }
    public String getDestino() { return destino; }
    public String getFechaSalida() { return fechaSalida; }
    public String getFechaRegreso() { return fechaRegreso; }
}

