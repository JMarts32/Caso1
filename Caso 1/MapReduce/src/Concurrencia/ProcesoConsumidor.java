package Concurrencia;

/*
Esta es la clase que modela el proceso final, que recibe los mensajes
ya transformados y los imprime
 */
public class ProcesoConsumidor extends Thread{

    // Este es el buzon final que tiene los mensajes transformados
    private final Buzon buzonRecibir;

    // Variable que permite saber si ya se termino el trabajo del proceso
    private boolean terminado;

    // Contador para saber si ya llegaron todos los mensajes de fin
    private int contador = 0;

    // Constructor del Thread
    public ProcesoConsumidor(Buzon b){
        this.terminado = false;
        this.buzonRecibir = b;
    }

    @Override
    public void run(){
        // La ejecucion se da hasta que llegan los tres mensajes de FIN
        while (!terminado){
            String retirado = buzonRecibir.remove();
            // Se revisa si el mensaje retirado ya es de FIN
            if (retirado.equals("FIN")){
                // Se suma al contador de mensajes de FIN
                contador ++;
                System.out.println(retirado);
                // Cuando hayan llegado los 3 mensajes de FIN se debe dar por terminada la ejecucion
                if (contador == 3){
                    terminado = true;
                }
            }
            // Si no es mensaje de Fin se imprime la transformacion Final
            else {
                System.out.println("El mensaje totalmente transformado es: " + retirado);
            }
        }
    }
}