
package civitas;

public class OperacionInmobiliaria {
    private int numPropiedad;
    private GestionesInmobiliarias gestion;

    public OperacionInmobiliaria(int _numPropiedad, GestionesInmobiliarias _gestion) {
        this.numPropiedad = _numPropiedad;
        this.gestion = _gestion;
    }

    public int getNumPropiedad() {
        return numPropiedad;
    }

    public GestionesInmobiliarias getGestion() {
        return gestion;
    }
    
    
}
