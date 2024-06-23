package aed;

// INVARIANTE DE REPRESENTACIÓN SistemaSIU
// la cantidad de claves en carreras se mantiene para todas las operaciones (luego de llamar al constructor).
// La cantidad de claves en estudiantes se mantiene para todas las operaciones (luego de llamar al constructor).
// Ningún estudiante puede tener mas inscripciones que la unión de materias de sus carreras (vinculado con el invRep de IMateria).
// La suma de las inscripciones de todos los estudiantes es igual a la suma de las longitudes de inscriptos para cada materia en el sistema (vinculado con el invRep de IMateria).
// Cada carrera tiene al menos una materia (vinculado con el invRep de IMateria y el de ICarrera).

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
        carreras = new DiccionarioDigital<>();  // O(1)
        estudiantes = new DiccionarioDigital<>();  //O(1)
        for (int i=0; i < infoMaterias.length; i++){ // O(|M|) 
            IMateria imateria = new IMateria();  //O(1)
            ICarrera icarrera = null; 
            ParCarreraMateria[] paresCarreraMateria = infoMaterias[i].getParesCarreraMateria(); // O(1)
            for (int j=0; j<paresCarreraMateria.length;j++){ // O(|Nm|) Itera |Nm| veces(tantas veces como nombres distintos de una materia haya)
                                                            // Consideramos que dos elementos n1,n2 ∈ Nm son distintos si están asociados a distintas carreras
                                                            // aunque dos carreras distintas puedan compartir una materia preservando su nombre.
            
                if (imateria.carrerasAsociadas.pertenece(paresCarreraMateria[j].nombreMateria) == false){ // O(|n|) (nombre de la materia)
                    imateria.carrerasAsociadas.definir(paresCarreraMateria[j].nombreMateria, new ListaEnlazada<ICarrera>()); // O(|n|)
                } // Total: O(2|n|)=O(|n|) 

                if (carreras.pertenece(paresCarreraMateria[j].carrera)){  // O(|c|)
                    icarrera = carreras.obtener(paresCarreraMateria[j].carrera);  // O(|c|)
                } else {
                    icarrera = new ICarrera(); // O(1)
                    carreras.definir(paresCarreraMateria[j].carrera, icarrera); // O(|c|)
                }// Total (es igual en ambas ramas): O(|c|) 

                icarrera.materias.definir(paresCarreraMateria[j].nombreMateria, imateria); // O(|n|)
                imateria.carrerasAsociadas.obtener(paresCarreraMateria[j].nombreMateria).agregarAtras(icarrera); // O(1+|n|)=O(|n|)
            } // O(∑_(n∈Nm)(|c| + |n|))
        } // O(∑_(m∈M)∑_(n∈Nm)(|c| + |n|)) ==> O(∑_(m∈M)∑_(n∈Nm)|c| + ∑_(m∈M)∑_(n∈Nm)|n|) ==> O(∑_(c∈C)|c|*|Mc| + ∑_(m∈M)∑_(n∈Nm)|n|)     

        for (int i= 0; i<libretasUniversitarias.length; i++){ 
            estudiantes.definir(libretasUniversitarias[i],0);  // O(1) E veces(por que las lu estan acotadas)
        } // O(E)
    } //Total: O(∑_(c∈C)|c|*|Mc| + ∑_(m∈M)∑_(n∈Nm)|n| + E)

    public void inscribir(String estudiante, String carrera, String materia){  
        ICarrera icarrera = carreras.obtener(carrera); // O(|c|)
        IMateria imateria = icarrera.materias.obtener(materia); // O(|m|)
        imateria.inscriptos.agregarAdelante(estudiante); // O(1)
        int inscripciones = estudiantes.obtener(estudiante); // O(1) porque esta acotado
        inscripciones ++; //O(1)
        estudiantes.definir(estudiante, inscripciones); // O(1)
    } //Total: O(|c| + |m|)

    public void agregarDocente(CargoDocente cargo, String carrera, String materia){ 
        ICarrera icarrera = carreras.obtener(carrera); // O(|c|)
        IMateria imateria = icarrera.materias.obtener(materia); // O(|m|)
        imateria.docentes[cargo.ordinal()] ++; // O(1)
    } //Total: O(|c| + |m|)

    public int[] plantelDocente(String materia, String carrera){
        ICarrera icarrera = carreras.obtener(carrera); // O(|c|)
        IMateria imateria = icarrera.materias.obtener(materia); // O(|m|)
        return imateria.docentes; // O(1)
    } // Total: O(|c| + |m|) 

    public void cerrarMateria(String materia, String carrera){ 
        ICarrera icarrera = carreras.obtener(carrera); // O(|c|)
        IMateria imateria = icarrera.materias.obtener(materia); // O(|m|)
        ListaEnlazada<String> EstudiantesInscriptos = imateria.inscriptos; // O(1)
        Iterador<String> it = EstudiantesInscriptos.iterador(); // O(1)
        for(int i = 0; i<EstudiantesInscriptos.longitud(); i++){ // O(Em) este ciclo se repite Em veces(cant de estudiantes de la materia)
            String estudiante = it.siguiente(); // O(1) recorro los estudiantes con el iterador
            int inscripcionActualizada = estudiantes.obtener(estudiante); // O(1) por que estudiante esta acotado. Le resto 1 a las inscripciones del estudiante
            inscripcionActualizada --; // O(1)
            estudiantes.definir(estudiante, inscripcionActualizada); // O(1). Redefino con el nuevo valor
        }

        String[] nombresMateria = imateria.carrerasAsociadas.clavesOrdenadas(); // ∑_(n∈Nm)|n| recorro imateria.carrerasAsociadas y pongo en un array todos los nombres alternativos de la materia para acceder al dict
        for(String nombremateria : nombresMateria ){
            ListaEnlazada<ICarrera> carrerasAsociadaANombre = imateria.carrerasAsociadas.obtener(nombremateria);// O(|n|)
            Iterador<ICarrera> iterador = carrerasAsociadaANombre.iterador(); // O(1)
            for (int i=0; i<carrerasAsociadaANombre.longitud();i++){
                ICarrera carreraAsociada = iterador.siguiente(); // O(1)
                carreraAsociada.materias.borrar(nombremateria); // O(|n|) borro la materia en su carrera
            }
        }
    } //Total: O(|c| + |m| + Em + ∑_(n∈Nm)|n|)

    public int inscriptos(String materia, String carrera){ 
        ICarrera icarrera = carreras.obtener(carrera); // O(|c|)
        IMateria imateria = icarrera.materias.obtener(materia); // O(|m|)
        return imateria.inscriptos.longitud(); //O(1)
    } //Total: O(|c| + |m|)

    public boolean excedeCupo(String materia, String carrera){ // O(|c| + |m|)
        int[] docentes = plantelDocente(materia, carrera); // O(|c| + |m|)
        int cantAlumnos = inscriptos(materia, carrera); // O(|c| + |m|)
        return min(250 * docentes[CargoDocente.PROF.ordinal()], 100*docentes[CargoDocente.JTP.ordinal()], 20*docentes[CargoDocente.AY1.ordinal()], 30*docentes[CargoDocente.AY2.ordinal()] ) < cantAlumnos ; //O(1)
    } //Total: O(|c|+|m|) 

    public int min(int PROF, int JTP, int AY1, int AY2){ // O(1)
        int minimo = 0;
        if (PROF <= JTP && PROF <= AY1 && PROF <= AY2) {
            minimo = PROF;
        } else {
            if (JTP <= PROF && JTP <= AY1 && JTP <= AY2) {
                minimo = JTP;
            } else {
                if (AY1 <= PROF && AY1 <= JTP && AY1 <= AY2) {
                    minimo = AY1;
                } else {
                    if (AY2 <= PROF && AY2 <= JTP && AY2 <= AY1) {
                        minimo = AY2;
                    }
                }
            }
        } return minimo;
    } // Total: O(1)

    public String[] carreras(){
        return carreras.clavesOrdenadas(); 
    } //Total: O(∑_(c∈C)|c|)

    public String[] materias(String carrera){
        ICarrera icarrera = carreras.obtener(carrera); //O(|c|)
        return icarrera.materias.clavesOrdenadas(); // O(∑_(mc∈Mc)|mc|). En particular, la complejidad de claves ordenadas es la suma de las longitudes de todas las claves en el diccionario
    } // Total: O(|c| + ∑_(mc∈Mc)|mc|)

    public int materiasInscriptas(String estudiante){
        return estudiantes.obtener(estudiante); //O(1) pq estudiante esta acotado
    } // Total: O(1)
}
