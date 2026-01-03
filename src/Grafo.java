import java.util.ArrayList;
import java.util.List;

public class Grafo {
    private List<Ciudad> ciudades;
    
    public Grafo() {
        ciudades = new ArrayList<>();
    }

    public List<Ciudad> getCiudades(){
        return ciudades;
    }

    public void agregarCiudad(Ciudad ciudad){
        ciudades.add(ciudad);
    }

    public Ciudad buscarCiudad(String claveCiudad){

        for (Ciudad ciudad : ciudades){
            
            if (ciudad.getClave().equals(claveCiudad)) {
                return ciudad;
            }
        }

        return null;
    }
}
