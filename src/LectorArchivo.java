/*
 * Titulo: LectorArchivo
 * Descripcion: Este programa se encarga de leer un archivo de texto que contiene
 * informacion sobre ciudades, aerolineas, rutas y consultas. Utiliza un parser
 * para procesar cada linea del archivo.
 * @autor: Jared Eliezer Baldenegro Gomez; Jimer Orlando Diaz Murillo y Hannia
 * Materia: tecnologias de programacion
 * Profesor: Dra. María Lucia Barrón Estrada
 * Fecha: 07-01-2026
 *
 *
 */
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

public class LectorArchivo {

    // Parser para procesar las líneas del archivo
    private final ParserLineas parser;

    public LectorArchivo(Grafo repositorioDatos) {
        this.parser = new ParserLineas(repositorioDatos);
    }

    // Método para cargar y procesar el archivo
    // Se ingresa el nombre del archivo a leer (Se espera que exista y sea ".txt")
    public void cargar(String nombreArchivo) {
        try (BufferedReader br = new BufferedReader(new FileReader(nombreArchivo))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                parser.procesarLinea(linea);
            }
        } catch (IOException e) {
            System.out.println("Error al leer el archivo.");
        }
    }

    public int getTotalConsultas() {
        return parser.getConsultas().size();
    }

    public List<Consulta> getConsultas() {
        return parser.getConsultas();
    }

}
