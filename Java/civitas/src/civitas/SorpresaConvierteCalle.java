//EXAMEN
package civitas;

import java.util.ArrayList;

/**
 *
 * @author juancarlosgq
 */
public class SorpresaConvierteCalle extends Sorpresa{
    private int indice;
    private TituloPropiedad titulo1;
    private TituloPropiedad titulo2;
    Tablero tablero;
    

    public SorpresaConvierteCalle(int v, String t, TituloPropiedad tp1, TituloPropiedad tp2, Tablero tab){
        super(t);
        indice = v;
        titulo1 = tp1;
        titulo2 = tp2;
        tablero = tab;
    }
    @Override
    public void aplicarAJugador(int actual, ArrayList<Jugador> todos){
        System.out.println("Voy a aplicarAJugador de SorpesaConvierteCalle");
        if(super.jugadorCorrecto(actual, todos)){
            super.informe(actual, todos);
            SorpresaCalle calleSorpresa = new SorpresaCalle(titulo1, titulo2);
            calleSorpresa.recibeJugador(actual, todos);
            Diario.getInstance().ocurreEvento("Se va a convertir la calle");
        }
    }
    
}
