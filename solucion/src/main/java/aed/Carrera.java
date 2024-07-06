package aed;

//INVARIANTE DE REPRESENTACIÓN ICarrera
//materias tiene como claves los nombres de todas las materias asociadas a la carrera.
public class Carrera {
    private DiccionarioDigital<String, Materia> materias;

    Carrera(){
        materias = new DiccionarioDigital<>();
    };

    public void añadirMateria(String nombreMateria, Materia imateria){
        materias.definir(nombreMateria, imateria);
    };

    public DiccionarioDigital<String, Materia> obtenerMaterias(){
        return this.materias;
    };

    


}
