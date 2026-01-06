/*
 * Titulo: Consulta
 * Descripcion: Clase que representa una consulta de viaje con origen, destino, fecha de salida y fecha de regreso.
 * @autor: Jared Eliezer Baldenegro Gomez; Jimer Orlando Diaz Murillo y Hannia
 * Materia: tecnologias de programacion
 * Profesor: Dra. María Lucia Barrón Estrada
 * Fecha: 07-01-2026
 * 
 * 
 */
public class Consulta {

    private String origen;
    private String destino;
    private String fechaSalida;
    private String fechaRegreso;

    public Consulta(String origen, String destino, String fechaSalida, String fechaRegreso) {
        this.origen = origen;
        this.destino = destino;
        this.fechaSalida = fechaSalida;
        this.fechaRegreso = fechaRegreso;
    }

}
