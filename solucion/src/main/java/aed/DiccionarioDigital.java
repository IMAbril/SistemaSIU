package aed;

import java.util.ArrayList;

public class DiccionarioDigital<K,T> {
    private Nodo<T> raiz;
    private int tamanio;
    private ArrayList<String> claves;

    private class Nodo<T> {
        ArrayList<Nodo<T>> siguientes;
        T definicion;

        Nodo(){
            definicion = null; 
            siguientes = new ArrayList<>(256);
            for (int i= 0; i<256; i++){
                siguientes.add(null);
            } 
        }
    }

    public DiccionarioDigital(){
        raiz = new Nodo<>();
        tamanio = 0; //cantidad de claves
        claves = new ArrayList<>(); 
    }

    public boolean diccionarioVacio(){
        return tamanio == 0;
    }

    public boolean pertenece(String clave){
        Nodo<T> actual = raiz;
        int i = 0;
        while (i < clave.length() && actual != null) {
            int pos = clave.charAt(i);
            actual = actual.siguientes.get(pos);
            i++;
        }
        return actual!= null  && actual.definicion != null;
    }

    public void definir(String clave, T valor){
        Nodo<T> actual = raiz;
        for (int i=0; i<clave.length(); i++){
            int pos = (int) clave.charAt(i);
            if (actual.siguientes.get(pos)==null){
                actual.siguientes.set(pos, new Nodo<T>());
            }
            actual = actual.siguientes.get(pos);
        }
        if (actual.definicion == null){
            tamanio++;
            claves.add(clave);
        }
        actual.definicion = valor;
    }

    public T obtener(String clave){
        Nodo<T> actual = raiz;
        for (int i=0; i<clave.length(); i++){
            int pos = (int) clave.charAt(i);
            actual =  actual.siguientes.get(pos);
        }
        return actual.definicion;
    }

    public void borrar(String clave){
        Nodo<T> actual = raiz;
        Nodo<T> ultimoNodoUtil = raiz;
        int ultimoIndiceUtil=0;
        for (int i= 0; i < clave.length(); i++){
            int pos = (int) clave.charAt(i);
            Nodo<T> siguiente = actual.siguientes.get(pos);

            if (siguiente.definicion != null || cantHijos(actual) > 1){
                ultimoNodoUtil = actual.siguientes.get(pos);
                ultimoIndiceUtil = i;
            }

            actual = siguiente;

        }

        // Elimino la clave
        actual.definicion = null;
        tamanio--;

        // Si nodo actual no tiene hijos, borro los descendientes de ultimoNodoUtil en la posicion ultimoIndiceUtil
        if (cantHijos(actual)==0){
    
            ultimoNodoUtil.siguientes.set(ultimoIndiceUtil, null);


        }

    }

    public int cantHijos(Nodo<T> nodo){
        int cantHijos = 0;
        for (int i=0; i<256;i++){
            if (nodo.siguientes.get(i)!= null){
                cantHijos++;
                if (cantHijos> 1){
                    return cantHijos;
                }
            }
        } 
        return 0;
    }

    public int cantClaves(){
        return tamanio;
    }

    public String[] clavesOrdenadas() {
        String[] claves = new String[tamanio];
        int[] cantClaves = new int[1];
        acumularClaves(raiz,new StringBuilder(), claves, cantClaves); 
        return claves;
    }

    private void acumularClaves(Nodo<T> nodo, StringBuilder prefijo, String[] claves, int[] cantClaves) {
        // O(1)
        if (nodo.definicion != null) {
            claves[cantClaves[0]] = prefijo.toString();
            cantClaves[0]++;
        } 
        // En el peor caso, cada nodo tiene 256 hijos no nulos, y cada llamada recursiva hace otras 256 llamadas recursivas.
        // Cada nodo se recorre una vez
        for (int i = 0; i < 256; i++) { 
            if (nodo.siguientes.get(i) != null) {
                char c = (char) i;
                prefijo.append(c);
                acumularClaves(nodo.siguientes.get(i), prefijo, claves, cantClaves);
                prefijo.deleteCharAt(prefijo.length() - 1);
            }
        }
    }
    
}
