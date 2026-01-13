/*
 * Titulo: Ciudad
 * Descripción: Clase que representa una ciudad
 * @autor: Jared Eliezer Baldenegro Gomez; Jimer Orlando Diaz Murillo y Hannia Melendres Samaniego
 * Materia: Tecnologías de programación
 * Profesor: Dra. María Lucia Barrón Estrada
 * Fecha: 07-01-2026
 */

import java.util.ArrayList;
import java.util.List;

public class Ciudad {

    //atributos
    private String clave;
    private String nombreCiudad;
    private String estado;
    private String pais;
    private String continente;
    private List<Ruta> rutas; //Lista de rutas

    //constructor
    public Ciudad(String clave, String nombreCiudad, String estado, String pais, String continente) {
        this.clave = clave;
        this.nombreCiudad = nombreCiudad;
        this.estado = estado;
        this.pais = pais;
        this.continente = continente;
        this.rutas = new ArrayList<>();
    }

    //getters
    public String getClave(){
        return clave;
    }

    //Metodo para agregar rutas a la lista de rutas de la ciudad.
    public void agregarRuta(Ruta ruta){
        rutas.add(ruta);
    }

    //Metodo para obtener las rutas de  la ciudad.
    public List<Ruta> getRutas(){
        return rutas;
    }

    public String toString(){
        return nombreCiudad;
    }


}