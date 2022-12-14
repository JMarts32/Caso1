package Concurrencia;

import java.util.*;

/* Esta en la clase que actua como el buffer, es decir,
recibe los resultados de los procesos hasta integrarlos.
Todos los métodos deben ser synchronized debido a que
son consultados para lectura y escritura concurrentemente.*/
public class Buzon {

    // Aca se almacenaran la cantidad de elementos que pueden entrar como máximo
    private List<String> datos = new ArrayList<>();

    // Es el limite de elementos que pueden entrar en el buzon
    private final int limit;

    // Se hace el constructor que contiene el limite de datos que pueden entrar
    public Buzon (int plimit){
        this.limit = plimit;
    }

    // Metodo que agrega elementos al buzon
    public synchronized void store(String elemento){
        // Dado que el buzon está lleno no se puede agregar más elementos
        while (datos.size() == limit){
            try{
                this.wait();
            } catch (InterruptedException e){
                e.printStackTrace();
            }
        }

        // Se agrega el elemento si hay espacio dentro del buzon
        datos.add(elemento);
        this.notifyAll();

    }

    //Metodo que quita elementos del buzon
    public synchronized String remove(){
        // Dado que no hay elementos dentro del buzon no se pueden sacar
        while (datos.isEmpty()){
            try{
                wait();
            } catch (InterruptedException e){
                e.printStackTrace();
            }
        }

        // Se quita el mensaje de la lista si hay mensajes para quitar :D
        this.notify();
        return datos.remove(0);
    }

    public int size(){
        return datos.size();
    }

    public boolean isFull(){
        return datos.size() == limit;
    }

}