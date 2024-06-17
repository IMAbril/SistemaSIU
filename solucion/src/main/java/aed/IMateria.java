package aed;

import java.lang.reflect.Array;

public class IMateria {
    ListaEnlazada<String> inscriptos ;
    int[] docentes;
    DiccionarioDigital<String, ICarrera> carrerasAsociadas;

    IMateria(){
        inscriptos = new ListaEnlazada<>();
        docentes = new int[]{0,0,0,0};
        carrerasAsociadas = new DiccionarioDigital<>();
    }
}
