package Concurrencia;

/*
Clase en la que se modelan las transformaciones internas
Como primero se hace una transformacion que se recibe de un buzon
el thread se comporta como productor y como consumidor al mismo tiempo.
De esta forma, toca poner las restricciones dentro del run.
 */
public class ProcesoProductorConsumidor extends Thread{

    //Este es el buzon por el que se comunican los productores y consumidores
    private final Buzon buzonRecibir;

    private final Buzon buzonEntregar;

    // id del thread
    private final int id;

    public ProcesoProductorConsumidor(Buzon b,Buzon b2 ,int pid) {
        this.buzonRecibir = b;
        this.id = pid;
        this.buzonEntregar = b2;
    }

    @Override
    public void run(){
        while (!buzonRecibir.isVacio() && buzonRecibir.isCorriendo() ==  true){
            int retirado = buzonRecibir.remove();
            buzonEntregar.store(retirado);
            if (retirado == -1){
                buzonEntregar.store(-1);
            }else{
                System.out.println("Se retiro algo: " + retirado + " del thread: " + id);
            }
        }
    }
}
