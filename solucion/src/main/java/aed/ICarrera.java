package aed;

public class ICarrera {
    DiccionarioDigital<String, IMateria> materias;

    ICarrera(){
        materias = new DiccionarioDigital<>();
    }
}
