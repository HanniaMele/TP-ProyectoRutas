public class LineaAerea {

    private String clave;
    private String nombreLineaAerea;
    private boolean activo;

    public LineaAerea(String clave, String nombre, boolean activo) {
        this.clave = clave.toUpperCase();
        this.nombreLineaAerea = nombre;
        this.activo = activo;
    }

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
    public int hashCode(){
        return this.clave.hashCode();
    }

    @Override
    public boolean equals(Object obj){

        boolean resultado = false;

        if(obj instanceof LineaAerea){

            LineaAerea otraLineaAerea = (LineaAerea) obj;

            resultado = this.clave.equals(otraLineaAerea.clave);

        }

        return resultado;

    }

    public String toString(){
        return "Clave Linea aerea: "+clave+ ", Nombre: "+nombreLineaAerea;
    }
}