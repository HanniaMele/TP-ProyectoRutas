import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
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
            System.out.println("Se detecto Ruta");
           // procesarRuta(m);
            return;
        }

        m = P_CIUDAD.matcher(linea);
        if (m.matches()) {
            System.out.println("Se detecto ciudad");
            //procesarCiudad(m);
            return;
        }

        m = P_AEROLINEA.matcher(linea);
        if (m.matches()) {
            System.out.println("Se detecto aerolinea");
            //procesarAerolinea(m);
            return;
        }

        m = P_CONSULTA.matcher(linea);
        if (m.matches()) {
            System.out.println("Se detecto consulta");
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
        
        //Codigo para el escaneo del archivo con lineas
        /*Scanner sc = new Scanner(System.in);
        System.out.print("Archivo de entrada: ");
        String nombreArchivo = sc.nextLine();

        try (BufferedReader br = new BufferedReader(new FileReader(nombreArchivo))) {

            String linea;
            while ((linea = br.readLine()) != null) {
                procesarLinea(linea);
            }

        } catch (IOException e) {
            System.out.println("Error al leer el archivo de entrada.");
        }*/

        Grafo grafo = new Grafo();

        /*
        //Problema de PowerPoint
        // Crear ciudades
        Ciudad A = new Ciudad("A","A","A","A","A");
        Ciudad B = new Ciudad("B","B","B","B","B");
        Ciudad C = new Ciudad("C","C","C","C","C");
        Ciudad D = new Ciudad("D","D","D","D","D");
        Ciudad E = new Ciudad("E","E","E","E","E");
        Ciudad F = new Ciudad("F","F","F","F","F");
        Ciudad G = new Ciudad("G","G","G","G","G");
        Ciudad H = new Ciudad("H","H","H","H","H");
        Ciudad I = new Ciudad("I","I","I","I","I");

        // Agregar rutas
        A.agregarRuta(new Ruta("AEROLINEA",1,"CIUDADORIGEN","B",3.0,0.0,0.0,"FRECUENCIA"));
        A.agregarRuta(new Ruta("AEROLINEA",1,"CIUDADORIGEN","C",5.0,0.0,0.0,"FRECUENCIA"));
        A.agregarRuta(new Ruta("AEROLINEA",1,"CIUDADORIGEN","D",4.0,0.0,0.0,"FRECUENCIA"));
        B.agregarRuta(new Ruta("AEROLINEA",1,"CIUDADORIGEN","E",2.0,0.0,0.0,"FRECUENCIA"));
        C.agregarRuta(new Ruta("AEROLINEA",1,"CIUDADORIGEN","E",1.0,0.0,0.0,"FRECUENCIA"));
        C.agregarRuta(new Ruta("AEROLINEA",1,"CIUDADORIGEN","F",2.0,0.0,0.0,"FRECUENCIA"));
        D.agregarRuta(new Ruta("AEROLINEA",1,"CIUDADORIGEN","F",1.0,0.0,0.0,"FRECUENCIA"));
        E.agregarRuta(new Ruta("AEROLINEA",1,"CIUDADORIGEN","F",3.0,0.0,0.0,"FRECUENCIA"));
        E.agregarRuta(new Ruta("AEROLINEA",1,"CIUDADORIGEN","G",2.0,0.0,0.0,"FRECUENCIA"));
        F.agregarRuta(new Ruta("AEROLINEA",1,"CIUDADORIGEN","G",7.0,0.0,0.0,"FRECUENCIA"));
        F.agregarRuta(new Ruta("AEROLINEA",1,"CIUDADORIGEN","H",5.0,0.0,0.0,"FRECUENCIA"));
        G.agregarRuta(new Ruta("AEROLINEA",1,"CIUDADORIGEN","I",1.0,0.0,0.0,"FRECUENCIA"));
        H.agregarRuta(new Ruta("AEROLINEA",1,"CIUDADORIGEN","I",2.0,0.0,0.0,"FRECUENCIA"));

        // Agregar ciudades al grafo
        grafo.agregarCiudad(A);
        grafo.agregarCiudad(B);
        grafo.agregarCiudad(C);
        grafo.agregarCiudad(D);
        grafo.agregarCiudad(E);
        grafo.agregarCiudad(F);
        grafo.agregarCiudad(G);
        grafo.agregarCiudad(H);
        grafo.agregarCiudad(I);
        */

        //Problema de PowerPoint con modificacion donde de G a I vale 100 
        // Crear ciudades
        Ciudad A = new Ciudad("A","A","A","A","A");
        Ciudad B = new Ciudad("B","B","B","B","B");
        Ciudad C = new Ciudad("C","C","C","C","C");
        Ciudad D = new Ciudad("D","D","D","D","D");
        Ciudad E = new Ciudad("E","E","E","E","E");
        Ciudad F = new Ciudad("F","F","F","F","F");
        Ciudad G = new Ciudad("G","G","G","G","G");
        Ciudad H = new Ciudad("H","H","H","H","H");
        Ciudad I = new Ciudad("I","I","I","I","I");

        // Agregar rutas
        A.agregarRuta(new Ruta("AEROLINEA",1,"CIUDADORIGEN","B",3.0,0.0,0.0,"FRECUENCIA"));
        A.agregarRuta(new Ruta("AEROLINEA",1,"CIUDADORIGEN","C",5.0,0.0,0.0,"FRECUENCIA"));
        A.agregarRuta(new Ruta("AEROLINEA",1,"CIUDADORIGEN","D",4.0,0.0,0.0,"FRECUENCIA"));
        B.agregarRuta(new Ruta("AEROLINEA",1,"CIUDADORIGEN","E",2.0,0.0,0.0,"FRECUENCIA"));
        C.agregarRuta(new Ruta("AEROLINEA",1,"CIUDADORIGEN","E",1.0,0.0,0.0,"FRECUENCIA"));
        C.agregarRuta(new Ruta("AEROLINEA",1,"CIUDADORIGEN","F",2.0,0.0,0.0,"FRECUENCIA"));
        D.agregarRuta(new Ruta("AEROLINEA",1,"CIUDADORIGEN","F",1.0,0.0,0.0,"FRECUENCIA"));
        E.agregarRuta(new Ruta("AEROLINEA",1,"CIUDADORIGEN","F",3.0,0.0,0.0,"FRECUENCIA"));
        E.agregarRuta(new Ruta("AEROLINEA",1,"CIUDADORIGEN","G",2.0,0.0,0.0,"FRECUENCIA"));
        F.agregarRuta(new Ruta("AEROLINEA",1,"CIUDADORIGEN","G",7.0,0.0,0.0,"FRECUENCIA"));
        F.agregarRuta(new Ruta("AEROLINEA",1,"CIUDADORIGEN","H",5.0,0.0,0.0,"FRECUENCIA"));
        G.agregarRuta(new Ruta("AEROLINEA",1,"CIUDADORIGEN","I",100.0,0.0,0.0,"FRECUENCIA"));
        H.agregarRuta(new Ruta("AEROLINEA",1,"CIUDADORIGEN","I",2.0,0.0,0.0,"FRECUENCIA"));

        // Agregar ciudades al grafo
        grafo.agregarCiudad(A);
        grafo.agregarCiudad(B);
        grafo.agregarCiudad(C);
        grafo.agregarCiudad(D);
        grafo.agregarCiudad(E);
        grafo.agregarCiudad(F);
        grafo.agregarCiudad(G);
        grafo.agregarCiudad(H);
        grafo.agregarCiudad(I);

        // Ejecutar algoritmo
        AlgoritmoBusquedaCamino algoritmo = new AlgoritmoBusquedaCamino();
        List<String> camino = algoritmo.caminoMasCorto(grafo, "A", "I");

        // Mostrar resultado
        System.out.println("Camino más corto: " + camino);
    }


}
