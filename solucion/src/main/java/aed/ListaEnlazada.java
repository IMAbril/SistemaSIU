package aed;

import java.util.*;

public class ListaEnlazada<T> implements Secuencia<T> {
    // Completar atributos privados
    private int longitud;
    private Nodo primero;
    private Nodo ultimo;
    private class Nodo {
        private T valor;
        private Nodo ant;
        private Nodo sig;
        
        Nodo(T v, Nodo sig, Nodo ant) {
            this.valor = v;
            this.ant = ant;
            this.sig = sig;
        }
    
    }

    public ListaEnlazada() {
        primero = null;
        ultimo = null;
        longitud = 0;
    }

    public int longitud() {
        return longitud;
    }

    public void agregarAdelante(T elem) {
        if (primero == null){
            Nodo nuevo = new Nodo(elem, null, null);
            primero = nuevo;
            ultimo = nuevo;    
        } else {
            Nodo nuevo = new Nodo(elem, primero, null);
            primero.ant = nuevo;
            primero = nuevo;
        }
        longitud++;
    }

    public void agregarAtras(T elem) {
        if (primero == null){
            Nodo nuevo = new Nodo(elem, null, null);
            primero = nuevo;
            ultimo = nuevo;    
        } else {
            Nodo nuevo = new Nodo(elem, null, ultimo);
            ultimo.sig = nuevo;
            ultimo = nuevo;
        }
        longitud++;
    }

    public T obtener(int i) {
        Nodo nodo_j = primero;
        int j = 0;
        while (j<i) {
            nodo_j = nodo_j.sig;
            j++;
        } 

        return nodo_j.valor;
    }

    public void eliminar(int i) {
        Nodo actual = primero;
        for (int j=0;j<i;j++){
            actual = actual.sig;
        }
        if (actual.ant==null){
            primero = actual.sig;
        }
        if (actual.sig==null){
            ultimo = actual.ant;
        }
        if (actual.ant != null && actual.sig!=null){
            actual.ant.sig = actual.sig;
            actual.sig.ant = actual.ant;     
        }
        longitud--;
    }

    public void modificarPosicion(int indice, T elem) {
        
        Nodo nodo_j = primero;
        for (int j = 0; j < indice; j++) {
            nodo_j = nodo_j.sig;
        }
        nodo_j.valor = elem;
        
    }

    public ListaEnlazada<T> copiar() {
        Nodo actual = primero;
        ListaEnlazada<T> copia = new ListaEnlazada<T>();
        while (actual != null){
            copia.agregarAtras(actual.valor);
            actual = actual.sig;
        }
        return copia;
    }

    public ListaEnlazada(ListaEnlazada<T> lista) {
        ListaEnlazada<T> copia = lista.copiar();
        longitud = copia.longitud;
        primero = copia.primero;
        ultimo = copia.ultimo;
    }
    
    @Override
    public String toString() {
        Iterador<T> iterador = iterador();
        String impresion = "[";

        while (iterador.haySiguiente()) {
            impresion += iterador.siguiente();
            if (iterador.haySiguiente()){
                impresion += ", ";
            }
        }

        impresion += "]";
        return impresion;
    }

    private class ListaIterador implements Iterador<T> {
    	// Completar atributos privados
        Nodo actual = primero;
        
        public boolean haySiguiente() {
            if (primero == null || actual==null){
                return false;
            }
            if (actual == ultimo){
                return true;
            }

	        return actual.sig != null;
        }
        
        public boolean hayAnterior() {
            if (primero == null){
                return false;
            }
            if (actual == null){
                return true;
            }
            return actual != primero.ant && actual!= primero;    
        }

        public T siguiente() {
            T valor = actual.valor;
            actual = actual.sig;
            return valor;
        }
        

        public T anterior() {
            if (actual == null){
                actual = ultimo; 
                return actual.valor;
            }
            actual = actual.ant;
            return actual.valor;
        }
    }

    public Iterador<T> iterador() {
        return new ListaIterador();
    }

}
