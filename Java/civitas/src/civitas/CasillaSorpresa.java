package civitas;

import java.util.ArrayList;

public class CasillaSorpresa extends Casilla{
    private MazoSorpresa mazo;
    private Sorpresa sorpresa;
    
    CasillaSorpresa(MazoSorpresa _mazoSorpresa, String _nombre){
        super(_nombre);
        mazo = _mazoSorpresa;
    }
    
    @Override
    public void recibeJugador(int iActual, ArrayList<Jugador> todos){
        if(super.jugadorCorrecto(iActual, todos)){
            sorpresa=mazo.siguiente();
            super.informe(iActual, todos);
            sorpresa.aplicarAJugador(iActual, todos);
        }
    }
    
    @Override
    public String toString(){
        return "Sorpresa " + sorpresa.getTexto();
    }
    
}
