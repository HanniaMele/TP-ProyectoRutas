/*
 * Titulo: Consulta
 * Descripción: Clase que representa una consulta de viaje con origen, destino, fecha de salida y fecha de regreso.
 * @autor: Jared Eliezer Baldenegro Gomez; Jimer Orlando Diaz Murillo y Hannia Melendres Samaniego
 * Materia: Tecnologías de programación
 * Profesor: Dra. María Lucia Barrón Estrada
 * Fecha: 07-01-2026
 * 
 * MODIFICACION: Agregado la variable DEBUG_INTRODUCIR_NOMBRE_ARCHIVO para controlar la entrada del nombre del archivo
 * 
 * MODIFICACION: La impresion de las rutas baratas y el top5 se han movido a "ControladorGrafo" asi como la impresion del resumen
 * de los datos ingresados
 * 
 * MODIFICACION: Se ha quitado el objeto grafo, agregandose directamente en "controladorGrafro" para que se utilice desde alli
 * y solo regrese resultados
 * 
 * MODIFICACION: El import java.util.List se a quitado puesto que ya no se manipulan objetos List desde aqui
 */

import java.io.File;


public class Rutas {

    // true = se introduce el nombre del archivo
    // false = el nombre del archivo esta establecido 
    public static final boolean DEBUG_INTRODUCIR_NOMBRE_ARCHIVO = false;

    public static void main(String[] args) {


        String nombreArchivo;

        // si es true se  introduce el nombre del archivo
        // si es false se utiliza el indicado en codigo
        if (DEBUG_INTRODUCIR_NOMBRE_ARCHIVO) {
            
            System.out.print("Archivo de entrada: ");
            nombreArchivo = Keyboard.readString();


        } else {
            //nombreArchivo = "prueba 1.txt";
            nombreArchivo = "PRUEBA 2.txt";
        }

        

        File archivo = new File(nombreArchivo);

        // SE COMPRUEBA SI EL ARCHIVO EXISTE
        if (!archivo.exists()) {
            System.out.println("El archivo NO existe.");

        } else {

            System.out.println("El archivo existe.\n");

            // Repositorio central de datos (Grafo real)
            //Grafo repositorioDatos = new Grafo();

            // Cargar archivo
            //LectorArchivo lector = new LectorArchivo(repositorioDatos);
            //lector.cargar(nombreArchivo);
            
            // Ejecutar consultas con el algoritmo (a través del controlador)
            ControladorGrafo controlador = new ControladorGrafo();

            // Se lee el archivo .txt 
            controlador.leerArchivo(nombreArchivo);

            // Los datos del archivo .txt capturados se almacenan en el grafo
            controlador.llenarGrafo();

            // imprime los datos ingresados en el grafo
            controlador.resumenDatosIngresados();

            controlador.ImprimirCaminoBarato();

            //controlador.imprimirTop5Caminos();

            ////////////////////////////////////////////////////////////////////////////////
            /// los datos del controlador se usan para obtener los viajes baratos
            
            

            /// //////////////////////////////////////////////////////////////////////////////

           

        }
        
    }
}
