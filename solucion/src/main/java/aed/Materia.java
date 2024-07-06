package aed;

import java.lang.reflect.Array;
import java.util.ArrayList;

//INVARIANTE DE REPRESENTACIÓN IMateria
//Cada materia tiene al menos una carrera asociada (carrerasAsociadas.tamanio > 0)
//Cada carrera en el significado de la clave (nombreMateria) en carrerasAsociadas tiene una única materia con el mismo nombre, los mismos inscriptos y los mismos docentes.
//Para todo estudiante en la lista inscriptos, la longitud de inscripciones es mayor a 0 (vinculado con el invRep de SistemaSIU)  
//Todo elemento de docentes es mayor o igual a 0

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
