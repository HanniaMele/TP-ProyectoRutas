/*
 * Titulo: Rutas 
 * Descripcion: Programa que lee un archivo con ciudades, aerolineas, rutas y consultas. Almacena los
 * datos en un repositorio y muestra las primeras 5 caminos disponibles.
 * @autor: Jared Eliezer Baldenegro Gomez; Jimer Orlando Diaz Murillo y Hannia
 * Materia: tecnologias de programacion
 * Profesor: Dra. María Lucia Barrón Estrada
 * Fecha: 07-01-2026
 * 
 * MODIFICACIONES: Eh agregado SALIDA_DEBUG y LLEGADA_DEBUG para pruebas de las horas de salida y llegada en las rutas.
 * Si se desea usar horas reales, cambiar las llamadas a agregarRuta en main para usar Local
 */


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalTime;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/// Metodo para verificar si un archivo existe
import java.nio.file.Files;
import java.nio.file.Path;

public class App {

    /// DEBUG: Valores de hora de salida y llegada por defecto para pruebas
    /// Si quiere utilizarse realmente debe usarse LocalTime.of(hora, minuto), ejemplo:
    /// LocalTime.of(10, 0) // 10:00 AM
    /// LocalTime.of(15, 30) // 3:30 PM
    /// LocalTime.MIDNIGHT // 00:00 AM
    /// 
    private static final LocalTime SALIDA_DEBUG  = LocalTime.MIDNIGHT;
    private static final LocalTime LLEGADA_DEBUG = LocalTime.MIDNIGHT;

    /// Metodo para comprobar si un archivo existe
    public static boolean archivoExiste(String nombreArchivo) {
        Path ruta = Path.of(nombreArchivo);
        return Files.exists(ruta);
    }

    public static void main(String[] args) {
        
        //Codigo para el escaneo del archivo con lineas
        /*Scanner sc = new Scanner(System.in);
        System.out.print("Archivo de entrada: ");
        String nombreArchivo = sc.nextLine();

        */
       String nombreArchivo;
       // Variable que almacena el nombre del archivo a leer
        //nombreArchivo = "TestCiudades.txt";
        //nombreArchivo = "prueba 1.txt";
        //nombreArchivo = "PRUEBA 2.txt";

        System.out.println("Ingrese el nombre del archivo de entrada (con extension .txt): ");
        nombreArchivo = Keyboard.readString();

        if (!nombreArchivo.endsWith(".txt")) {
            System.out.println("El archivo debe tener extension .txt");
            return;
        }

        Path ruta = Path.of(nombreArchivo);

        if (!archivoExiste(nombreArchivo)) {

            System.out.println("El archivo no existe.");

        } else {
            System.out.println("El archivo existe.");

             // Repositorio central de datos
            Grafo repositorioDatos = new Grafo();
            
            // Lector de archivo
            LectorArchivo lector = new LectorArchivo(repositorioDatos);
            // Carga el archivo
            lector.cargar(nombreArchivo);

            // Muestra un resumen de los datos cargados
            System.out.println("Ciudades cargadas: " + repositorioDatos.ciudades.size());
            System.out.println("Aerolineas cargadas: " + repositorioDatos.aerolineas.size());
            System.out.println("Rutas cargadas: " + repositorioDatos.rutas.size());
            System.out.println("Consultas cargadas: " + repositorioDatos.consultas.size());
            System.out.println();

        }

    
        
       
        /*---------------------------------------------------------------------------------------
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

        /*---------------------------------------------------------------------------------------------------
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
        */

        //Problema de PowerPoint pero con controlador grafos--------------------------------------------------------
        // Crear ciudades

        /*
        ControladorGrafo controladorGrafo = new ControladorGrafo();

        // Agregar ciudades al grafo
        controladorGrafo.agregarCiudad("A","Ciudad A","A","A","A");
        controladorGrafo.agregarCiudad("B","Ciudad B","B","B","B");
        controladorGrafo.agregarCiudad("C","Ciudad C","C","C","C");
        controladorGrafo.agregarCiudad("D","Ciudad D","D","D","D");
        controladorGrafo.agregarCiudad("E","Ciudad E","E","E","E");
        controladorGrafo.agregarCiudad("F","Ciudad F","F","F","F");
        controladorGrafo.agregarCiudad("G","Ciudad G","G","G","G");
        controladorGrafo.agregarCiudad("H","Ciudad H","H","H","H");
        controladorGrafo.agregarCiudad("I","Ciudad I","I","I","I");

        // Agregar rutas
        controladorGrafo.agregarRuta("AEROLINEA",1,"A","B",3.0,SALIDA_DEBUG,LLEGADA_DEBUG,"FRECUENCIA");
        controladorGrafo.agregarRuta("AEROLINEA",1,"A","C",5.0,SALIDA_DEBUG,LLEGADA_DEBUG,"FRECUENCIA");
        controladorGrafo.agregarRuta("AEROLINEA",1,"A","D",4.0,SALIDA_DEBUG,LLEGADA_DEBUG,"FRECUENCIA");
        controladorGrafo.agregarRuta("AEROLINEA",1,"B","E",2.0,SALIDA_DEBUG,LLEGADA_DEBUG,"FRECUENCIA");
        controladorGrafo.agregarRuta("AEROLINEA",1,"C","E",1.0,SALIDA_DEBUG,LLEGADA_DEBUG,"FRECUENCIA");
        controladorGrafo.agregarRuta("AEROLINEA",1,"C","F",2.0,SALIDA_DEBUG,LLEGADA_DEBUG,"FRECUENCIA");
        controladorGrafo.agregarRuta("AEROLINEA",1,"D","F",1.0,SALIDA_DEBUG,LLEGADA_DEBUG,"FRECUENCIA");
        controladorGrafo.agregarRuta("AEROLINEA",1,"E","F",3.0,SALIDA_DEBUG,LLEGADA_DEBUG,"FRECUENCIA");
        controladorGrafo.agregarRuta("AEROLINEA",1,"E","G",2.0,SALIDA_DEBUG,LLEGADA_DEBUG,"FRECUENCIA");
        controladorGrafo.agregarRuta("AEROLINEA",1,"F","E",3.0,SALIDA_DEBUG,LLEGADA_DEBUG,"FRECUENCIA");
        controladorGrafo.agregarRuta("AEROLINEA",1,"F","G",7.0,SALIDA_DEBUG,LLEGADA_DEBUG,"FRECUENCIA");
        controladorGrafo.agregarRuta("AEROLINEA",1,"F","H",5.0,SALIDA_DEBUG,LLEGADA_DEBUG,"FRECUENCIA");
        controladorGrafo.agregarRuta("AEROLINEA",1,"G","I",1.0,SALIDA_DEBUG,LLEGADA_DEBUG,"FRECUENCIA");
        controladorGrafo.agregarRuta("AEROLINEA",1,"H","I",2.0,SALIDA_DEBUG,LLEGADA_DEBUG,"FRECUENCIA");

        List <String> caminoDijkstra = controladorGrafo.viajeMenorPrecio("A", "I");

        List <Viaje> caminoTop5 = controladorGrafo.top5ViajesBaratos("A", "I");

        // Mostrar resultado
        System.out.println("Camino más corto Dijkstra");
        System.out.println(caminoDijkstra);

        System.out.println("Top 5 caminos mas cortos");

        for (Viaje viaje : caminoTop5) {
            System.out.println(viaje+"\n");
        }

        */
    }
}
