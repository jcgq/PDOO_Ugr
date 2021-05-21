package civitas;

import java.util.ArrayList;

public class CasillaImpuesto extends Casilla{
    
    private float importe;
    
    CasillaImpuesto(float _cantidad, String _nombre){
        super(_nombre);
        importe = _cantidad;
    }
    
    @Override
    public void recibeJugador(int iActual, ArrayList<Jugador> todos){
        if(super.jugadorCorrecto(iActual, todos)){
            super.informe(iActual, todos);
            todos.get(iActual).pagaImpuesto(importe);
        }
    }
    
    @Override
    public String toString(){
        return "\nHas caido en "+ super.getNombre() +" y es un Impuesto de: " + Float.toString(importe);
    }
}
