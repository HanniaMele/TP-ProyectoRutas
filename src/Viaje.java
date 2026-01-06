import java.util.List;

public class Viaje implements Comparable<Viaje>{

    private List<String> ciudades;
    private double costoTotal;

    public Viaje(List<String> ciudades, double costoTotal) {
        this.ciudades = ciudades;
        this.costoTotal = costoTotal;
    }

    public List<String> getCiudades() {
        return ciudades;
    }

    public double getCostoTotal() {
        return costoTotal;
    }

    @Override
    public int compareTo(Viaje otro) {
        return Double.compare(this.costoTotal, otro.costoTotal);
    }

    @Override
    public String toString() {
        return "Ruta: " + ciudades + " / Costo: " + costoTotal;
    }
}