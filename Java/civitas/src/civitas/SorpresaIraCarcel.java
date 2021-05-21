package civitas;

import java.util.ArrayList;

public class SorpresaIraCarcel extends Sorpresa{
    private Tablero tablero;
    SorpresaIraCarcel(Tablero _tablero){
        super("Ir a Carcel");
        tablero = _tablero;
    }
    
    @Override
    public void aplicarAJugador(int actual, ArrayList<Jugador> todos){
        if(this.jugadorCorrecto(actual, todos)){
            this.informe(actual, todos);
            todos.get(actual).encarcelar(tablero.getCarcel());
        }
    }
    
    @Override
    public String toString(){
        return "Sorpresa{ " + super.getTexto() + " }";            
    }
}
