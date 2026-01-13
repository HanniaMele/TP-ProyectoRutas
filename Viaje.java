/*
 * Titulo: Viaje
 * Descripción: Clase que representa un viaje
 * @autor: Jared Eliezer Baldenegro Gomez; Jimer Orlando Diaz Murillo y Hannia Melendres Samaniego
 * Materia: Tecnologías de programación
 * Profesor: Dra. María Lucia Barrón Estrada
 * Fecha: 07-01-2026
 */

import java.util.List;

public class Viaje implements Comparable<Viaje>{

    //Atributos
    private List<Ciudad> ciudades;
    private double costoTotal;

    //constructor
    public Viaje(List<Ciudad> ciudades, double costoTotal) {
        this.ciudades = ciudades;
        this.costoTotal = costoTotal;
    }

    //getters
    public List<Ciudad> getCiudades() {
        return ciudades;
    }

    public double getCostoTotal() {
        return costoTotal;
    }

    //Implementacion requerida para poder usar PriorityQueue en el algoritmo de busqueda para el top 5
    @Override
    public int compareTo(Viaje otro) {
        return Double.compare(this.costoTotal, otro.costoTotal);
    }

    @Override
    public String toString() {
        return "Ruta: " + ciudades + " / Costo: " + costoTotal;
    }
}
