/*
 * MODIFICADO: Se han agregado los metodos "totalCiudades", "totalLineasAereas", "totalRutas" y "totalConsultas"
 *
 * * MODIFICADO: Se han quitado los imports HashMap y Map por que no se utilizan en el presente codigo.
 */

import java.util.ArrayList;
import java.util.List;

public class Grafo {
    private List<Ciudad> catalogoCiudades;
    public List<LineaAerea> catalogoLineasAereas;
    private List<Ruta> catalogoRutas;
    private List<Consulta> catalogoConsultas;
    

    public Grafo() {
        catalogoCiudades = new ArrayList<>();
        catalogoLineasAereas = new ArrayList<>();
        catalogoRutas = new ArrayList<>();
        catalogoConsultas = new ArrayList<>();
    }

    public List<Ciudad> getCiudades(){
        return catalogoCiudades;
    }

    public boolean buscarLineaAereaInactiva(String claveLineaAerea){

        boolean resultado = false;

        for(LineaAerea lineaAerea : catalogoLineasAereas){
            if(lineaAerea.getClave().equals(claveLineaAerea)  && !lineaAerea.getActivo()){ //lineaAerea.getClave().equals(claveLineaAerea)
                resultado = true;
            }
        }

        return resultado;

    }

    public void agregarCiudad(Ciudad ciudad){
        catalogoCiudades.add(ciudad);
    }

    // Recibe una clave de ciudad y la busca en el catalogo de ciudades
    // si existe, regresa la ciudad
    public Ciudad buscarCiudad(String claveCiudad){

        for (Ciudad ciudad : catalogoCiudades){

            if (ciudad.getClave().equals(claveCiudad)) {
                return ciudad;
            }
        }

        return null;
    }

    public void agregarLineaAerea(LineaAerea nuevaLineaAerea){

        catalogoLineasAereas.add(nuevaLineaAerea);

    }

    public LineaAerea buscarLineaAereaNombre(String nombreLineaAerea){

        for(LineaAerea lineaAerea : catalogoLineasAereas){

            if(lineaAerea.getNombreLineaAerea().equals(nombreLineaAerea)){

                return lineaAerea;
            }
        }

        return null;
    }

    public LineaAerea buscarLineaAereaClave(String claveAeroLinea){

        for(LineaAerea lineaAerea : catalogoLineasAereas){

            if(lineaAerea.getClave().equals(claveAeroLinea)){

                return lineaAerea;
            }
        }

        return null;
    }

    public void agregarConsulta (Consulta consulta) {
        catalogoConsultas.add(consulta);
    }

    public List<Consulta> getConsulta(){
        return catalogoConsultas;
    }

    public void agregarRuta (Ruta ruta) {
        catalogoRutas.add(ruta);
    }

    public List<Ruta> getRuta() {
        return catalogoRutas;
    }

    public List<LineaAerea> getLineaAereas() {
        return catalogoLineasAereas;
    }

    public int totalCiudades() {
        return catalogoCiudades.size();
    }

    public int totalLineasAereas() {
        return catalogoLineasAereas.size();
    }

    public int totalRutas() {
        return catalogoRutas.size();
    }

    public int totalConsultas() {
        return catalogoConsultas.size();
    }
}