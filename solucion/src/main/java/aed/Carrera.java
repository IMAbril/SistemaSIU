package aed;

//INVARIANTE DE REPRESENTACIÓN Carrera
// Para cada clave nombreMateria en materias: 
//      el significado es una materia del sistema.
public class Carrera {
    private DiccionarioDigital<String, Materia> materias;

    Carrera(){
        materias = new DiccionarioDigital<>();
    };

    public void añadirMateria(String nombreMateria, Materia materia){
        materias.definir(nombreMateria, materia);
    };

    public DiccionarioDigital<String, Materia> obtenerMaterias(){
        return this.materias;
    };

    


}
