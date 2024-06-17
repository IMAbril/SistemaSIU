package aed;

public class SistemaSIU {
    DiccionarioDigital<String, ICarrera> carreras;
    DiccionarioDigital<String, Integer> estudiantes;

    enum CargoDocente{
        PROF,
        JTP,
        AY1,
        AY2, 
    }

    public SistemaSIU(InfoMateria[] infoMaterias, String[] libretasUniversitarias){
        carreras = new DiccionarioDigital<>();
        estudiantes = new DiccionarioDigital<>();
        for (int i=0; i < infoMaterias.length; i++){
            IMateria imateria = new IMateria();
            ParCarreraMateria[] paresCarreraMateria = infoMaterias[i].getParesCarreraMateria(); 
            for (int j=0; j<paresCarreraMateria.length;j++){
                if (carreras.pertenece(paresCarreraMateria[j].carrera)){
                    ICarrera icarrera = carreras.obtener(paresCarreraMateria[j].carrera);
                    icarrera.materias.definir(paresCarreraMateria[j].nombreMateria, imateria);
                    imateria.carrerasAsociadas.definir(paresCarreraMateria[j].nombreMateria, icarrera);
                } else {
                    ICarrera icarrera = new ICarrera();
                    carreras.definir(paresCarreraMateria[j].carrera, icarrera);
                    icarrera.materias.definir(paresCarreraMateria[j].nombreMateria, imateria);
                    imateria.carrerasAsociadas.definir(paresCarreraMateria[j].nombreMateria, icarrera);
                }
            }
        }
        for (int i= 0; i<libretasUniversitarias.length; i++){
            estudiantes.definir(libretasUniversitarias[i],0);
        }
    }

    public void inscribir(String estudiante, String carrera, String materia){
        ICarrera icarrera = carreras.obtener(carrera);
        IMateria imateria = icarrera.materias.obtener(materia);
        imateria.inscriptos.agregarAdelante(estudiante);
        int inscripciones = estudiantes.obtener(estudiante);
        estudiantes.definir(estudiante, inscripciones++);
    }

    public void agregarDocente(CargoDocente cargo, String carrera, String materia){
        ICarrera icarrera = carreras.obtener(carrera);
        IMateria imateria = icarrera.materias.obtener(materia);
        imateria.docentes[cargo.ordinal()] ++; 
    }

    public int[] plantelDocente(String materia, String carrera){
        ICarrera icarrera = carreras.obtener(carrera);
        IMateria imateria = icarrera.materias.obtener(materia);
        return imateria.docentes;
    }

    public void cerrarMateria(String materia, String carrera){
        throw new UnsupportedOperationException("Método no implementado aún");	    
    }

    public int inscriptos(String materia, String carrera){
        ICarrera icarrera = carreras.obtener(carrera);
        IMateria imateria = icarrera.materias.obtener(materia);
        return imateria.inscriptos.longitud();
    }

    public boolean excedeCupo(String materia, String carrera){
        int[] docentes = plantelDocente(materia, carrera);
        int cantAlumnos = inscriptos(materia, carrera);
        return 250 * docentes[CargoDocente.PROF.ordinal()] < cantAlumnos &&
            100*docentes[CargoDocente.JTP.ordinal()]< cantAlumnos &&
            20*docentes[CargoDocente.AY1.ordinal()]< cantAlumnos &&
            30*docentes[CargoDocente.AY2.ordinal()]< cantAlumnos ;
    }

    public String[] carreras(){
        return carreras.clavesOrdenadas();
    }

    public String[] materias(String carrera){
        ICarrera icarrera = carreras.obtener(carrera);
        return icarrera.materias.clavesOrdenadas(); 
    }

    public int materiasInscriptas(String estudiante){
        return estudiantes.obtener(estudiante);
    }
}
