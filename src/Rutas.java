/*
 * Titulo: Consulta
 * Descripción: Clase que representa una consulta de viaje con origen, destino, fecha de salida y fecha de regreso.
 * @autor: Jared Eliezer Baldenegro Gomez; Jimer Orlando Diaz Murillo y Hannia Melendres Samaniego
 * Materia: Tecnologías de programación
 * Profesor: Dra. María Lucia Barrón Estrada
 * Fecha: 07-01-2026
 *
 *
 */

import java.io.File;
import java.util.List;
//import java.util.Scanner;

public class Rutas {

    public static void main(String[] args) {

        //Scanner sc = new Scanner(System.in);

        System.out.print("Archivo de entrada: ");
        //String nombreArchivo = sc.nextLine().trim();
        String nombreArchivo = Keyboard.readString();

        File archivo = new File(nombreArchivo);
        if (!archivo.exists()) {
            System.out.println("El archivo NO existe.");
            //return;
        } else {

            System.out.println("El archivo existe.\n");

            // Repositorio central de datos (Grafo real)
            Grafo repositorioDatos = new Grafo();

            // Cargar archivo
            //LectorArchivo lector = new LectorArchivo(repositorioDatos);
            //lector.cargar(nombreArchivo);
            
            // Ejecutar consultas con el algoritmo (a través del controlador)
            ControladorGrafo controlador = new ControladorGrafo(repositorioDatos);

            controlador.leerArchivo(nombreArchivo);
            controlador.llenarGrafo();

            ////////////////////////////////////////////////////////////////////////////////
            /// los datos del controlador se usan para obtener los viajes baratos
            
            
            System.out.println("=== Resultados de consultas ===");

            for (Consulta c : controlador.getConsultas()) {

                System.out.println("----------------------------------");
                System.out.println("Consulta: " + c.getOrigen() + " -> " + c.getDestino());

                // Camino más barato
                List<String> camino = controlador.viajeMenorPrecio(
                        c.getOrigen(),
                        c.getDestino()
                );

                if (camino == null || camino.isEmpty()) {
                    System.out.println("No hay ruta disponible.");
                } else {
                    System.out.println("Camino más barato: " + camino);
                }

                // Top 5
                List<Viaje> top5 = controlador.top5ViajesBaratos(
                        c.getOrigen(),
                        c.getDestino()
                );

                if (top5 == null || top5.isEmpty()) {
                    System.out.println("Top 5: No disponible.");
                } else {
                    System.out.println("Top 5 viajes baratos:");
                    for (Viaje v : top5) {
                        System.out.println("  - " + v);
                    }
                }
            }
            



            /// //////////////////////////////////////////////////////////////////////////////

            // Resumen
           // System.out.println("=== Resumen de carga ===");
            //System.out.println("Ciudades cargadas: " + repositorioDatos.getCiudades().size());
            //System.out.println("Aerolineas cargadas: " + repositorioDatos.catalogoLineasAereas.size());

            //int totalRutas = 0;
            //for (Ciudad ciudad : repositorioDatos.getCiudades()) {
              //  totalRutas += ciudad.getRutas().size();
            //}
            //System.out.println("Rutas cargadas: " + totalRutas);

            //List<Consulta> consultas = repositorioDatos.getConsulta(); //lector.getConsultas()
            //System.out.println("Consultas cargadas: " + consultas.size());
            //System.out.println();

            // Ejecutar consultas con el algoritmo (a través del controlador)
            //ControladorGrafo controlador = new ControladorGrafo(repositorioDatos);

            //System.out.println("=== Resultados de consultas ===");
            //for (Consulta c : consultas) {
              //  System.out.println("----------------------------------");
               // System.out.println("Consulta: " + c.getOrigen() + " -> " + c.getDestino());

                // Camino más barato (lista de claves de ciudad / o como lo construya tu algoritmo)
                //List<String> camino = controlador.viajeMenorPrecio(c.getOrigen(), c.getDestino());
                //if (camino == null || camino.isEmpty()) {
                  //  System.out.println("No hay ruta disponible.");
                //} else {
                  //  System.out.println("Camino más barato: " + camino);
                //}

                // Top 5 (si aplica)
                //List<Viaje> top5 = controlador.top5ViajesBaratos(c.getOrigen(), c.getDestino());
                //if (top5 == null || top5.isEmpty()) {
                //    System.out.println("Top 5: No disponible.");
                //} else {
                  //  System.out.println("Top 5 viajes baratos:");
                   // for (Viaje v : top5) {
                     //   System.out.println("  - " + v);
                    //}
               // }
            //}
            //System.out.println("----------------------------------");

        }
        
    }
}
