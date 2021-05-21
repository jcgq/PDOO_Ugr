package civitas;

import java.util.ArrayList;

public class CasillaJuez extends Casilla{
    private static int carcel;
    
    CasillaJuez(int numCasilla, String _nombre){
        super(_nombre);
        carcel = numCasilla;
    }
    
    @Override
    public void recibeJugador(int iActual, ArrayList<Jugador> todos){
        if(super.jugadorCorrecto(iActual, todos)){
            super.informe(iActual, todos);
            todos.get(iActual).encarcelar(carcel);
        }
    }
    
    @Override
    public String toString(){
        return "Te han pillado involucrado en los ERES vas la carcel " + Integer.toString(carcel);
    }
}
