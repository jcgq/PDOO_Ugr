package civitas;

import java.util.ArrayList;

public class SorpresaEspeculador extends Sorpresa{
    private int valor;
    
    SorpresaEspeculador(int _valor){
        super("Soy Especulador");
        valor=_valor;
    }
    
    @Override
    public void aplicarAJugador(int actual, ArrayList<Jugador> todos){
        if(super.jugadorCorrecto(actual, todos)){
            super.informe(actual, todos);
            Especulador especulador = new Especulador(todos.get(actual), valor);
            todos.set(actual, especulador);
        }
    }
}
