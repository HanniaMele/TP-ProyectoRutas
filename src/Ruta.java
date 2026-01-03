public class Ruta {

    private String claveAerolinea;
    private int numeroRuta;
    private String claveCiudadOrigen;
    private String claveCiudadDestino;
    private double costo;
    private double horaSalida;
    private double horaLlegada;
    private String frecuencia;

    public Ruta(String claveAerolinea, int numeroRuta, String claveCiudadOrigen, String claveCiudadDestino, double costo, double horaSalida, double horaLlegada, String frecuencia) {
        this.claveAerolinea = claveAerolinea;
        this.numeroRuta = numeroRuta;
        this.claveCiudadOrigen = claveCiudadOrigen;
        this.claveCiudadDestino = claveCiudadDestino;
        this.costo = costo;
        this.horaSalida = horaSalida;
        this.horaLlegada = horaLlegada;
        this.frecuencia = frecuencia;
    }

    public String getclaveCiudadDestino(){
        return claveCiudadDestino;
    }

    public double getCosto(){
        return costo;
    }
    
}
