package civitas;
import GUI.Dado;

import java.util.ArrayList;

public class SorpresaIraCasilla extends Sorpresa{
    private Tablero tablero;
    private int valor;
    
    public SorpresaIraCasilla(Tablero _tablero, int _valor, String _texto){
        super(_texto);
        tablero = _tablero;
        valor = _valor;
    }
    
    @Override
     public void aplicarAJugador(int actual, ArrayList<Jugador> todos){
        if(jugadorCorrecto(actual, todos) == true){
            super.informe(actual, todos);
            Casilla casillaActual = tablero.getCasilla(todos.get(actual).getNumCasillaActual());
            int numDado = Dado.getInstance().tirar();
            int tirada = tablero.calcularTirada(todos.get(actual).getNumCasillaActual(), numDado);
            int casillaFinal = tablero.nuevaPosicion(todos.get(actual).getNumCasillaActual(), tirada);
            todos.get(actual).moverACasilla(casillaFinal);
            casillaActual.recibeJugador(actual, todos);
        }
    }

    @Override
    public String toString(){
        return "Sorpresa{ " + super.getTexto() + ", valor " + Integer.toString(valor) + " }";
                 
    }
}
