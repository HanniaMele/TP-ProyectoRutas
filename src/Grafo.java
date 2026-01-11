import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Grafo {
    private List<Ciudad> catalogoCiudades;
    public List<LineaAerea> catalogoLineasAereas;

    public Grafo() {
        catalogoCiudades = new ArrayList<>();
        catalogoLineasAereas = new ArrayList<>();
    }

    public List<Ciudad> getCiudades(){
        return catalogoCiudades;
    }

    public boolean buscarLineaAereaInactiva(String claveLineaAerea){

        boolean resultado = false;

        for(LineaAerea lineaAerea : catalogoLineasAereas){
            if(lineaAerea.getClave() == claveLineaAerea && !lineaAerea.getActivo()){
                resultado = true;
            }
        }

        return resultado;

    }

    public void agregarCiudad(Ciudad ciudad){
        catalogoCiudades.add(ciudad);
    }

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
}