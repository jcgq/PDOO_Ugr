package civitas;

import java.util.ArrayList;

public class SorpresaPorCasaHotel extends Sorpresa{
    private int valor;
    
    SorpresaPorCasaHotel(int _valor, String _texto){
        super(_texto);
        valor = _valor;
    }
    
    @Override
    public void aplicarAJugador(int actual, ArrayList<Jugador> todos){
        if(this.jugadorCorrecto(actual, todos)){
            informe(actual, todos);
            todos.get(actual).modificarSaldo(this.valor*todos.get(actual).cantidadCasasHoteles());
        }
    }
    
    @Override
    public String toString(){
        return "Sorpresa{ " + super.getTexto() + ", valor " + Integer.toString(valor) + " }";
                 
    }
}
