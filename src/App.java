import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class App {

    static final Pattern P_AEROLINEA = Pattern.compile("^(-)?([A-Z]{2,3})\\s+([A-Za-z]{1,10})$");

    static final Pattern P_CIUDAD = Pattern.compile("^([A-Z]{3})\\s+([^,]+),([^,]+),([^,]+),([^,]+)$");

    static final Pattern P_RUTA = Pattern.compile("^([A-Z]{2,3})\\s+(\\d{4})\\s+([A-Z]{3})\\s+([A-Z]{3})\\s+" + "(\\d+(?:\\.\\d+)?)\\s+(\\d{2}:\\d{2})\\s+(\\d{2}:\\d{2})\\s+([1-7]+)$");

    static final Pattern P_CONSULTA =Pattern.compile("^([A-Z]{3})\\s*->\\s*([A-Z]{3})\\s+(\\d{4}-\\d{2}-\\d{2})(?:\\s+(\\d{4}-\\d{2}-\\d{2}))?$");


    private static void procesarLinea(String linea) {
        linea = linea.replace("<-", "->");


        linea = limpiarLinea(linea);
        if (linea.isEmpty()) return;

        Matcher m;

        m = P_RUTA.matcher(linea);
        if (m.matches()) {
           // procesarRuta(m);
            return;
        }

        m = P_CIUDAD.matcher(linea);
        if (m.matches()) {
            //procesarCiudad(m);
            return;
        }

        m = P_AEROLINEA.matcher(linea);
        if (m.matches()) {
            //procesarAerolinea(m);
            return;
        }

        m = P_CONSULTA.matcher(linea);
        if (m.matches()) {
            //procesarConsulta(m);
            return;
        }

        System.out.println("Línea inválida: " + linea);
    }


    // Prepara la linea leida para asegurarse de que es valida
    // Elimina el espacio en blanco al inicio y al final
    // Si la linea esta vacia o contiene solo un punto, devuelve null
    private static String limpiarLinea(String linea) {

        if (linea == null) return null;

        // Elimina espacios en blanco al inicio y al final
        linea = linea.trim();

        // Almacenara donde se realizara el corte
        int corte = -1;

        // Si la linea esta vacia o contiene solo un punto, devuelve null
        if (linea.isEmpty() || linea.equals(".")) {
            return null;
        }

        // Elimina cualquier cosa despues de un punto
        int punto = linea.lastIndexOf('.');

        // Buscamos el ultimo signo de pregunta
        int pregunta = linea.lastIndexOf('?');

        if (punto != -1) {
            // El punto manda: corta antes del punto
            corte = punto;
        } else if (pregunta != -1) {
            // No hay punto, pero hay pregunta: corta DESPUES del ?
            corte = pregunta + 1;
        }

        // Realiza el corte si es necesario
        if (corte != -1) {
            linea = linea.substring(0, corte);
        }
        
        // Elimina espacios en blanco al inicio y al final nuevamente
        linea = linea.trim();
        // Devuelve la linea limpia o null si esta vacia
        return linea.isEmpty() ? null : linea;
    }



    public static void main(String[] args) {
         Scanner sc = new Scanner(System.in);
        System.out.print("Archivo de entrada: ");
        String nombreArchivo = sc.nextLine();

        try (BufferedReader br = new BufferedReader(new FileReader(nombreArchivo))) {

            String linea;
            while ((linea = br.readLine()) != null) {
                procesarLinea(linea);
            }

        } catch (IOException e) {
            System.out.println("Error al leer el archivo de entrada.");
        }
    }


}
