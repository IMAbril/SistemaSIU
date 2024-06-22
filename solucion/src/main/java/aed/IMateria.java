package aed;

import java.lang.reflect.Array;
import java.util.ArrayList;

//INVARIANTE DE REPRESENTACIÓN IMateria
//Cada materia tiene al menos una carrera asociada (carrerasAsociadas.tamanio > 0)
//Cada carrera en el significado de la clave (nombreMateria) en carrerasAsociadas tiene una única materia con el mismo nombre, los mismos inscriptos y los mismos docentes.
//Para todo estudiante en la lista inscriptos, la longitud de inscripciones es mayor a 0 (vinculado con el invRep de SistemaSIU)  
//Todo elemento de docentes es mayor o igual a 0

public class IMateria {
    ListaEnlazada<String> inscriptos ;
    int[] docentes;
    DiccionarioDigital<String, ListaEnlazada<ICarrera>> carrerasAsociadas;

    IMateria(){
        inscriptos = new ListaEnlazada<>();
        docentes = new int[]{0,0,0,0};
        carrerasAsociadas = new DiccionarioDigital<>();
    }
}
