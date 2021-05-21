package civitas;

import java.util.ArrayList;


public class SorpresaPorJugador extends Sorpresa{
    private int valor;
    
    SorpresaPorJugador(int _valor, String _texto){
        super(_texto);
        valor = _valor;
    }
    
    @Override
    public void aplicarAJugador(int actual, ArrayList<Jugador> todos){
        if(this.jugadorCorrecto(actual, todos)){
            super.informe(actual, todos);
            SorpresaPagarCobrar sorpresilla = new SorpresaPagarCobrar(-valor*(todos.size()-1), "Pierdes " + valor + " euros para pagar a un jugador");
            SorpresaPagarCobrar recibeIngreso = new SorpresaPagarCobrar(valor, "Recibes dinero. Que suerte!");
            for(int i=0; i<todos.size(); i++){
                if(i==actual){
                    sorpresilla.aplicarAJugador(i, todos);
                }
                else{
                    recibeIngreso.aplicarAJugador(i, todos);
                }
            }
        }
    }
    
    @Override
    public String toString(){
        return "Sorpresa{ " + super.getTexto() + ", valor " + Integer.toString(valor) + " }";
                 
    }
}
