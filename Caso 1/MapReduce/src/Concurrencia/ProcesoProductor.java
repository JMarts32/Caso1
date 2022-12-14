package Concurrencia;

/*
Esta es la clase que modela el primer proceso del MapReduce
Solo crea los mensajes y los distribuye al primer buzon que
los mandara a los procesos para ser transformados.
 */
public class ProcesoProductor extends Thread{

    //Este es el buzon por el que se comunican los productores y consumidores
    private final Buzon buzon;

    // Es es la cantidad de mensajes que se van a producir
    private final int porProducir;

    // Variable para saber si ya se producieron todos los mensajes
    private int producidos;

    // Constructor del trhead
    public ProcesoProductor(Buzon b, int pPorProducir) {
        this.buzon = b;
        this.porProducir = pPorProducir;
        this.producidos = 0;
    }

    @Override
    public void run(){

        // Toca almacenar el mensaje que se va a transformar durante la ejecucion
        while (producidos < porProducir){
            if(buzon.isFull())
                Thread.yield();
            producidos ++;
            // Se crean los mensjes M + el numero del mensaje producido
            buzon.store(("M" + producidos+"= "));
        }

        // Se generan FIN cuando ya se acabo la creacion de los mensajes
        for (int i = 0; i<3; i ++){
            // Se crean 3 veces porque se reparten solo 3 threads
            buzon.store("FIN");
        }
    }
}
