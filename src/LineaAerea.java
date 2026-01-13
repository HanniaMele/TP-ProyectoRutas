/*
 * Titulo: LineaAerea
 * Descripción: Clase que representa una línea aérea
 * @autor: Jared Eliezer Baldenegro Gomez; Jimer Orlando Diaz Murillo y Hannia Melendres Samaniego
 * Materia: Tecnologías de programación
 * Profesor: Dra. María Lucia Barrón Estrada
 * Fecha: 07-01-2026
 */

public class LineaAerea {

    //atributos
    private String clave;
    private String nombreLineaAerea;
    private boolean activo;

    //constructor
    public LineaAerea(String clave, String nombre, boolean activo) {
        this.clave = clave.toUpperCase();
        this.nombreLineaAerea = nombre;
        this.activo = activo;
    }

    //getters
    public String getClave() {
        return clave;
    }

    public String getNombreLineaAerea(){
        return nombreLineaAerea;
    }

    public boolean getActivo(){
        return activo;
    }

    @Override
    public boolean equals(Object obj) {
        return this == obj || //si this y object apuntan al mismo objeto, son iguales
                (obj instanceof LineaAerea otra && //o, si obj es una instancia de lineaAerea, lo guarda en una nueva referencia
                        this.nombreLineaAerea.equals(otra.nombreLineaAerea)); //compara el valor lógico (contenido) de ambos
    }

    @Override
    public int hashCode(){ return nombreLineaAerea.hashCode();}

    public String toString(){
        return "Clave Linea aérea: "+clave+ ", Nombre: "+nombreLineaAerea;
    }
}