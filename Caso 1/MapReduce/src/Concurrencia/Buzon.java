package Concurrencia;

import java.util.*;

/* Esta en la clase que actua como el buffer, es decir,
recibe los resultados de los procesos hasta integrarlos.
Todos los métodos deben ser synchronized debido a que
son consultados para lectura y escritura concurrentemente.*/
public class Buzon {

    // Aca se almacenaran la cantidad de elementos que pueden entrar como máximo
    private List buzon = new ArrayList<Integer>();

    // Es el limite de elementos que pueden entrar en el buzon
    private int limit;

    // Variable para saber si el buzon esta lleno
    private boolean lleno;

    // Variable para saber si el buzon esta vacio
    private boolean vacio;

    // Variable para saber si ya se terminaron los mensajes
    private boolean corriendo;

    // Varibale para saber si ya se enviaron todos los mensajes de fin
    private int cantidad;

    // Getter para saber si el buzon esta lleno
    public synchronized boolean isLleno() {
        return lleno;
    }

    // Getter para saber si el buzon esta vacio
    public synchronized boolean isVacio() {
        return vacio;
    }

    public synchronized boolean isCorriendo(){
        return corriendo;
    }

    // Se hace el constructor que contiene el limite de datos que pueden entrar
    public Buzon (int plimit){
        this.limit = plimit;
        this.lleno = false;
        this.vacio =true;
        this.corriendo = true;
        this.cantidad = 0;
    }

    // Metodo que agrega elementos al buzon
    public synchronized void store(int elemento){
        // Dado que el buzon está lleno no se puede agregar más elementos
        while (buzon.size() == limit){
            try{
                lleno = true;
                this.wait();
            } catch (InterruptedException e){
                e.printStackTrace();
            }
        }

        // Se agrega el elemento si es posible
        buzon.add(elemento);
        // Si se esta agregando algo a la lista implica quee no esta vacio
        vacio = false;
        this.notify();

        // Si el elemento que se esta almacenando es -1 implica que ya se acabaron los mensajes
        if (elemento == -1){
            cantidad ++;
            if (cantidad == 3){
                corriendo = false;
            }
        }
    }

    //Metodo que quita elementos del buzon
    public synchronized Integer remove(){
        // Dado que no hay elementos dentro del buzon no se pueden sacar
        while (buzon.size() == 0){
            try{
                vacio = true;
                wait();
            } catch (InterruptedException e){
                e.printStackTrace();
            }
        }

        // Se quita el mensaje de la lista
        int quitar = (Integer) buzon.remove(0);
        notify();
        // Si es está quitando un elemento implica que la lista no esta llena
        lleno = false;
        return quitar;
    }

}
