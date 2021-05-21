package civitas;

import java.util.ArrayList;

public class SorpresaPagarCobrar extends Sorpresa {
    private int valor;
    
    SorpresaPagarCobrar(int _valor, String _texto){
        super(_texto);
        valor = _valor;
    }
    
    @Override
    public void aplicarAJugador(int actual, ArrayList<Jugador> todos){
        if(this.jugadorCorrecto(actual, todos)){
            this.informe(actual, todos);
            todos.get(actual).modificarSaldo(this.valor);
        }
    }
    
    @Override
    public String toString(){
        return "Sorpresa{ " + super.getTexto() + ", valor " + Integer.toString(valor) + " }";          
    }
}
