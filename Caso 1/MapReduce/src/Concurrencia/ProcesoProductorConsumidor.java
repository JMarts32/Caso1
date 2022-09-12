package Concurrencia;

import java.util.Random;

/*
Clase en la que se modelan las transformaciones internas
Como primero se hace una transformacion que se recibe de un buzon
el thread se comporta como productor y como consumidor al mismo tiempo.
De esta forma, toca poner las restricciones dentro del run.
 */
public class ProcesoProductorConsumidor extends Thread{

    //Este es el buzon por el que se comunican los productores y consumidores
    private final Buzon buzonRecibir;

    // Este es el buzon donde se entregara el mensaje
    private final Buzon buzonEntregar;

    // id del thread
    private final int id;

    // Variable para saber que nivel de transformacion se esta aplicando
    private final int nivel;

    // Variable que permite saber si ya se termino el trabajo del proceso
    private boolean terminado;

    public ProcesoProductorConsumidor(Buzon b,Buzon b2,int pid, int pnivel) {
        this.buzonRecibir = b;
        this.id = pid;
        this.buzonEntregar = b2;
        this.nivel = pnivel;
        this.terminado = false;
    }

    @Override
    public void run(){
        // El thread esta en ejecucion hasta que le llegue un mensaje de FIN
        while (!terminado){
            Random r = new Random();
            /* Se pone a dormir el Thread para simular que se esta demorando en la transformacion.
            Es un tiempo aleatorio entre 5 y 500 milisengundos.*/
            int mimir = r.nextInt(500)+5;

            // Se recibe el mensaje que se desea transformar
            String retirado = buzonRecibir.remove();

            if (retirado.equals("FIN")){
                buzonEntregar.store("FIN");
                terminado = true;
            }else{
                try {
                    // Se duerme el Thread para la simulacion
                    sleep(mimir);

                    // Cuando despierta el Thread se aplica la transformacion
                    retirado += ("T" + id + nivel + " - Demora:" + mimir + "ms | ");

                    // Se entrega en el siguente buzon el mensaje transformado
                    buzonEntregar.store(retirado);

                    // No se, pero si se quita no funciona :D
                    System.out.print("");

                }catch (InterruptedException e){
                    e.printStackTrace();
                }
            }
        }
    }
}