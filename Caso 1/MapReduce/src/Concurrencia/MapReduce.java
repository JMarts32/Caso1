package Concurrencia;

import java.util.*;

/*
Esta es la clase que tratará cada uno de los procesos
para que entren a los buzones y salgan hasta terminar la
ejecución
 */
public class MapReduce {

    public static void main(String[] args) {

        Scanner input = new Scanner(System.in);

        System.out.println("\tBienvenido a MapReduce\n");

        System.out.println("Ingrese la cantidad de mensajes que desea: ");
        int porProducir = input.nextInt();

        System.out.println("Ingrese el tamanio de los buzones intermedios: ");
        int tamanioIntermedios = input.nextInt();

        System.out.println("Ingrese el tamanio de los buzones extremos e iniciales: ");
        int tamanioExtremos = input.nextInt();

        /* El tamanio de los buzones iniciales y extremos son iguales
        Pero el tamanio de los buzones intermedios puede ser diferente */


        // Se crea el primer thread para pasarle al buzon los primeros mensajes
        Buzon buzonInicial = new Buzon(tamanioExtremos);
        ProcesoProductor inicial = new ProcesoProductor(buzonInicial,porProducir);
        inicial.start();


        while (ejecucion){
            Buzon buzonIntermedio = new Buzon(tamanioIntermedios);
            // Aca se crean los 3 threads que van a recibir los mensajes y por donde se van a transformar
            for (int i = 0; i < 3; i++){
                ProcesoProductorConsumidor primerosThreads = new ProcesoProductorConsumidor(buzonInicial,buzonIntermedio,i+1);
                System.out.println("Se creo el thread: " + (i+1));
                primerosThreads.start();
            }
        }

    }
}
