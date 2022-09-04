package Concurrencia;

import java.util.*;

/*
Esta es la clase que tratara cada uno de los procesos
para que entren a los buzones y salgan hasta terminar la
ejecucion
 */
public class MapReduce {

    // Se crea la variable de matrizBuz donde se almacenaran los buzones intermedios
    private static final Buzon[][] matrizBuz = new Buzon[3][2];

    // Se crea la variable matrizProc donde se almacenaran los procesos intermedios
    private static final ProcesoProductorConsumidor[][] matrizProc = new ProcesoProductorConsumidor[3][3];

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

        //Se crean los buzones intermedios
        for (int i = 0; i < 3; i++){
            for (int j = 0; j <2; j++){
                // Se crean los buzones y se agregan a la matriz
                matrizBuz[i][j] = new Buzon(tamanioIntermedios);
            }
        }

        // Se crea el primer buzon que recibira los mensajes creados en el primer Thread
        Buzon buzonInicial = new Buzon(tamanioExtremos);

        // Se crea el buzon final que recibira de los procesos los mensajes con sus transformaciones
        Buzon buzonFinal = new Buzon(tamanioExtremos);

        // Se crea el primer thread para pasarle al buzon inicial los mensajes
        ProcesoProductor inicial = new ProcesoProductor(buzonInicial,porProducir);
        inicial.start();

        // Se inicia el proceso final para que reciba los mensajes que seran transformados
        ProcesoConsumidor procesoFinal = new ProcesoConsumidor(buzonFinal);
        procesoFinal.start();



        // Se crean los procesos intermedios
        for (int i = 0; i < 3; i++){
            for (int j = 0; j <3; j++){
                // Si esta en el primer nivel de transformacion recibe del buzon inicial
                if (j == 0){
                    matrizProc[i][j] = new ProcesoProductorConsumidor(buzonInicial,matrizBuz[i][j],i+1,j+1);
                    // De alguna forma si no se hace el print, no se llega a esta parte :D
                    System.out.print("");
                    matrizProc[i][j].start();
                }
                // Si se esta en el ultimo proceso, se envia al buzon final
                else if (j == 2){
                    matrizProc[i][j] = new ProcesoProductorConsumidor(matrizBuz[i][j-1],buzonFinal,i+1,j+1);
                    System.out.print("");
                    matrizProc[i][j].start();
                }else {
                    matrizProc[i][j] = new ProcesoProductorConsumidor(matrizBuz[i][j-1],matrizBuz[i][j],i+1,j+1);
                    System.out.print("");
                    matrizProc[i][j].start();
                }

            }
        }

    }
}
