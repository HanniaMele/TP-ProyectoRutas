import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Grafo {
    //private List<Ciudad> ciudades;
    Map<String, Ciudad> ciudades = new HashMap<>();
    Map<String, LineaAerea> aerolineas = new HashMap<>();
    List<Ruta> rutas = new ArrayList<>();
    List<Consulta> consultas = new ArrayList<>();
    
    public Grafo() {
        //ciudades = new ArrayList<>();
    }

    /*
    public List<Ciudad> getCiudades(){
        return ciudades;
    }
    */

    public Map<String, Ciudad> getCiudades(){
        return ciudades;
    }

    public void agregarCiudad(Ciudad ciudad){
        //ciudades.add(ciudad);
        ciudades.put(ciudad.getClave(), ciudad);
    }

    public Ciudad buscarCiudad(String claveCiudad){
        /*
        for (Ciudad ciudad : ciudades){
            
            if (ciudad.getClave().equals(claveCiudad)) {
                return ciudad;
            }
        }
        */

        for (Ciudad ciudad : ciudades.values()){
            
            if (ciudad.getClave().equals(claveCiudad)) {
                return ciudad;
            }
        }

        return null;
    }
}
