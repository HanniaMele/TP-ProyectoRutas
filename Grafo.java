/*
 * Titulo: Grafo
 * Descripción: Clase que representa al grafo
 * @autor: Jared Eliezer Baldenegro Gomez; Jimer Orlando Diaz Murillo y Hannia Melendres Samaniego
 * Materia: Tecnologías de programación
 * Profesor: Dra. María Lucia Barrón Estrada
 * Fecha: 07-01-2026
 * MODIFICADO: Se han agregado los metodos "totalCiudades", "totalLineasAereas", "totalRutas" y "totalConsultas"
 *
 * * MODIFICADO: Se han quitado los imports HashMap y Map por que no se utilizan en el presente codigo.
 */

import java.util.ArrayList;
import java.util.List;

public class Grafo {

    //atributos
    private List<Ciudad> catalogoCiudades; //vértices del grafo
    public List<LineaAerea> catalogoLineasAereas; //restricciones
    private List<Ruta> catalogoRutas; //aristas del grafo
    private List<Consulta> catalogoConsultas; //peticiones del usuario
    
    //constructor
    public Grafo() {
        catalogoCiudades = new ArrayList<>();
        catalogoLineasAereas = new ArrayList<>();
        catalogoRutas = new ArrayList<>();
        catalogoConsultas = new ArrayList<>();
    }

    //getter
    public List<Ciudad> getCiudades(){
        return catalogoCiudades;
    }

    //método para validar si una linea aerea está inactiva
    public boolean buscarLineaAereaInactiva(String claveLineaAerea){

        boolean resultado = false;

        for(LineaAerea lineaAerea : catalogoLineasAereas){
            if(lineaAerea.getClave().equals(claveLineaAerea)  && !lineaAerea.getActivo()){ //lineaAerea.getClave().equals(claveLineaAerea)
                resultado = true;
            }
        }

        return resultado;

    }

    //metodo para agregar ciudad al catálogo de ciudades
    public void agregarCiudad(Ciudad ciudad){
        catalogoCiudades.add(ciudad);
    }

    // Recibe una clave de ciudad y la busca en el catalogo de ciudades
    // si existe, regresa la ciudad
    //método para buscar una ciudad
    public Ciudad buscarCiudad(String claveCiudad){

        for (Ciudad ciudad : catalogoCiudades){

            if (ciudad.getClave().equals(claveCiudad)) {
                return ciudad;
            }
        }
        return null;
    }

    //método para agregar una línea aérea
    public void agregarLineaAerea(LineaAerea nuevaLineaAerea){

        catalogoLineasAereas.add(nuevaLineaAerea);

    }

    //método para buscar el nombre de la línea aérea
    public LineaAerea buscarLineaAereaNombre(String nombreLineaAerea){

        for(LineaAerea lineaAerea : catalogoLineasAereas){

            if(lineaAerea.getNombreLineaAerea().equals(nombreLineaAerea)){

                return lineaAerea;
            }
        }

        return null;
    }

    //método para buscar la línea aérea por medio de la clave
    public LineaAerea buscarLineaAereaClave(String claveAeroLinea){

        for(LineaAerea lineaAerea : catalogoLineasAereas){

            if(lineaAerea.getClave().equals(claveAeroLinea)){

                return lineaAerea;
            }
        }

        return null;
    }

    //método para agregar consulta al catalogo de consultas
    public void agregarConsulta (Consulta consulta) {
        catalogoConsultas.add(consulta);
    }

    public List<Consulta> getConsulta(){
        return catalogoConsultas;
    }

    //método para agregar ruta al catalogo de rutas
    public void agregarRuta (Ruta ruta) {
        catalogoRutas.add(ruta);
    }

    public List<Ruta> getRuta() {
        return catalogoRutas;
    }

    public List<LineaAerea> getLineaAereas() {
        return catalogoLineasAereas;
    }

    //método para obtener la cantidad de ciudades
    public int totalCiudades() {
        return catalogoCiudades.size();
    }

    //método para obtener la cantidad de lineas aereas
    public int totalLineasAereas() {
        return catalogoLineasAereas.size();
    }

    //método para obtener la cantidad de rutas
    public int totalRutas() {
        return catalogoRutas.size();
    }

    //método para obtener la cantidad de consultas
    public int totalConsultas() {
        return catalogoConsultas.size();
    }
}