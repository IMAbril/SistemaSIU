package aed;

//INVARIANTE DE REPRESENTACIÓN ICarrera
//materias tiene como claves los nombres de todas las materias asociadas a la carrera.
public class ICarrera {
    DiccionarioDigital<String, IMateria> materias;

    ICarrera(){
        materias = new DiccionarioDigital<>();
    }
}
