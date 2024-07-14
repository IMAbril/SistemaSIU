package aed;

import java.lang.reflect.Array;
import java.util.ArrayList;

//INVARIANTE DE REPRESENTACIÓN Materia
// La longitud de docentes es 4. 
// Todos los elementos de docentes son enteros mayores o iguales a 0.
// La longitud de inscriptos está entre 0 y la cantidad de estudiantes en el sistema.
// Todos los elementos (LU) de inscriptos son estudiantes del sistema.
// La cantidad de claves en carrerasAsociadas está entre 0 y la cantidad de carreras en el sistema.
// Para toda clave nombreMateria en carrerasAsociadas:
//      La longitud del significado (que es una lista) está entre 1 y la cantidad de carreras en el sistema. 
//      Todos los elementos del significado son carreras del sistema. 

public class Materia {
    private ListaEnlazada<String> inscriptos;
    private int[] docentes;
    private DiccionarioDigital<String, ListaEnlazada<Carrera>> carrerasAsociadas;

    Materia(){
        inscriptos = new ListaEnlazada<>();
        docentes = new int[]{0,0,0,0};
        carrerasAsociadas = new DiccionarioDigital<>();
    }

    public int[] obtenerPlantelDocente(){
        return this.docentes;
    };

    public DiccionarioDigital<String, ListaEnlazada<Carrera>> obtenerCarrerasAsociadas(){
        return this.carrerasAsociadas;
    };

    public ListaEnlazada<String> obtenerInscriptos(){
        return this.inscriptos;
    };

    public void añadirCarreraAsociada(String nombreMateria, Carrera carrera){
        this.carrerasAsociadas.obtener(nombreMateria).agregarAtras(carrera);;
    }

}
