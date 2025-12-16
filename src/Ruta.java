public class Ruta {

    private String clave;
    private int numeroRuta;
    private String claveCiudadOrigen;
    private String claveCiudadDestino;
    private double costo;
    private double HoraSalida;
    private double HoraLlegada;
    private String frecuencia;

    public Ruta(String clave, int numeroRuta, String claveCiudadOrigen, String claveCiudadDestino, double costo, double HoraSalida, double HoraLlegada, String frecuencia) {
        this.clave = clave;
        this.numeroRuta = numeroRuta;
        this.claveCiudadOrigen = claveCiudadOrigen;
        this.claveCiudadDestino = claveCiudadDestino;
        this.costo = costo;
        this.HoraSalida = HoraSalida;
        this.HoraLlegada = HoraLlegada;
        this.frecuencia = frecuencia;
    }
    
}
