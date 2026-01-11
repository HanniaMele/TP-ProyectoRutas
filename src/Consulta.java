public class Consulta {

    private final String origen;
    private final String destino;
    private final String fechaSalida;
    private final String fechaRegreso;

    public Consulta(String origen, String destino, String fechaSalida, String fechaRegreso) {
        this.origen = origen;
        this.destino = destino;
        this.fechaSalida = fechaSalida;
        this.fechaRegreso = fechaRegreso;
    }

    public String getOrigen() { return origen; }
    public String getDestino() { return destino; }
    public String getFechaSalida() { return fechaSalida; }
    public String getFechaRegreso() { return fechaRegreso; }
}

