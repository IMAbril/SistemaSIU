package aed;

import java.util.ArrayList;

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
        for (int i=0; i < infoMaterias.length; i++){
            IMateria imateria = new IMateria();  //O(1)

            ParCarreraMateria[] paresCarreraMateria = infoMaterias[i].getParesCarreraMateria();
            for (int j=0; j<paresCarreraMateria.length;j++){
            
                if (imateria.carrerasAsociadas.pertenece(paresCarreraMateria[j].nombreMateria) == false){
                    imateria.carrerasAsociadas.definir(paresCarreraMateria[j].nombreMateria, new ListaEnlazada<ICarrera>());
                }
                if (carreras.pertenece(paresCarreraMateria[j].carrera)){  //O(|c|)
                    ICarrera icarrera = carreras.obtener(paresCarreraMateria[j].carrera);  //O(|c|)
                    icarrera.materias.definir(paresCarreraMateria[j].nombreMateria, imateria);
                    imateria.carrerasAsociadas.obtener(paresCarreraMateria[j].nombreMateria).agregarAtras(icarrera);
                } else {
                    ICarrera icarrera = new ICarrera();
                    carreras.definir(paresCarreraMateria[j].carrera, icarrera);
                    icarrera.materias.definir(paresCarreraMateria[j].nombreMateria, imateria);
                    imateria.carrerasAsociadas.obtener(paresCarreraMateria[j].nombreMateria).agregarAtras(icarrera);
                }
            }
        }
        for (int i= 0; i<libretasUniversitarias.length; i++){ // O(E)
            estudiantes.definir(libretasUniversitarias[i],0);  // O(E)
        }
    }

    public void inscribir(String estudiante, String carrera, String materia){  // O(|c| + |m|)
        ICarrera icarrera = carreras.obtener(carrera); // O(|c|)
        IMateria imateria = icarrera.materias.obtener(materia); // O(|m|)
        imateria.inscriptos.agregarAdelante(estudiante); // O(1)
        int inscripciones = estudiantes.obtener(estudiante); // O(1) porque esta acotado
        inscripciones ++; //O(1)
        estudiantes.definir(estudiante, inscripciones); // O(1)
    }

    public void agregarDocente(CargoDocente cargo, String carrera, String materia){ // O(|c| + |m|)
        ICarrera icarrera = carreras.obtener(carrera); // O(|c|)
        IMateria imateria = icarrera.materias.obtener(materia); // O(|m|)
        imateria.docentes[cargo.ordinal()] ++; // O(1)
    }

    public int[] plantelDocente(String materia, String carrera){ // O(|c| + |m|)
        ICarrera icarrera = carreras.obtener(carrera); // O(|c|)
        IMateria imateria = icarrera.materias.obtener(materia); // O(|m|)
        return imateria.docentes; // O(1)
    }

    public void cerrarMateria(String materia, String carrera){
        ICarrera icarrera = carreras.obtener(carrera);
        IMateria imateria = icarrera.materias.obtener(materia);
        ListaEnlazada<String> EstudiantesInscriptos = imateria.inscriptos;
        Iterador<String> it = EstudiantesInscriptos.iterador();
        for(int i = 0; i<EstudiantesInscriptos.longitud(); i++){
            String estudiante = it.siguiente(); // recorro los estudiantes con el iterador
            int inscripcionActualizada = estudiantes.obtener(estudiante); // le resto 1 a las inscripciones del estudiante
            inscripcionActualizada --;
            estudiantes.definir(estudiante, inscripcionActualizada); // redefino con el nuevo valor
        }

        String[] nombresMateria = imateria.carrerasAsociadas.clavesOrdenadas(); // pongo en un array todos los nombres alternativos de la materia para acceder al dict
        for(String nombremateria : nombresMateria ){
            ListaEnlazada<ICarrera> carrerasAsociadaANombre = imateria.carrerasAsociadas.obtener(nombremateria);
            Iterador<ICarrera> iterador = carrerasAsociadaANombre.iterador();
            for (int i=0; i<carrerasAsociadaANombre.longitud();i++){
                ICarrera carreraAsociada = iterador.siguiente();
                carreraAsociada.materias.borrar(nombremateria); // borro la materia en su carrera
            }
        }
    }

    public int inscriptos(String materia, String carrera){ // O(|c| + |m|)
        ICarrera icarrera = carreras.obtener(carrera); // O(|c|)
        IMateria imateria = icarrera.materias.obtener(materia); // O(|m|)
        return imateria.inscriptos.longitud(); //O(1)
    }

    public boolean excedeCupo(String materia, String carrera){ // O(|c| + |m|)
        int[] docentes = plantelDocente(materia, carrera); // O(|c| + |m|)
        int cantAlumnos = inscriptos(materia, carrera); // O(|c| + |m|)
        return min(250 * docentes[CargoDocente.PROF.ordinal()], 100*docentes[CargoDocente.JTP.ordinal()], 20*docentes[CargoDocente.AY1.ordinal()], 30*docentes[CargoDocente.AY2.ordinal()] ) < cantAlumnos ; //O(1)
    }

    public int min(int PROF, int JTP, int AY1, int AY2){
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
    }

    public String[] carreras(){
        return carreras.clavesOrdenadas();
    }

    public String[] materias(String carrera){
        ICarrera icarrera = carreras.obtener(carrera); //O(|c|)
        return icarrera.materias.clavesOrdenadas();
    }

    public int materiasInscriptas(String estudiante){
        return estudiantes.obtener(estudiante); //O(1) pq estudiante esta acotado
    }
}
