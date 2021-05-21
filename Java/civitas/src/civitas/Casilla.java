
package civitas;

import java.util.ArrayList;

public class Casilla {
    private String nombre;//ATRIBUTO DE INSTANCIA
    
    Casilla(String _nombre){
        nombre=_nombre;
    }
    
    public String getNombre(){
        return nombre;
    }

    public void informe(int iActual, ArrayList<Jugador> todos){
        Diario.getInstance().ocurreEvento("El jugador " + todos.get(iActual).getNombre() + " ha caido en la casilla" +this.toString());
    }
    
    public boolean jugadorCorrecto(int iActual, ArrayList<Jugador> todos){
        if(iActual>=0 && iActual<todos.size())
            return true;
        else
            return false;
    }

    void recibeJugador(int iActual, ArrayList<Jugador> todos){
        this.informe(iActual, todos);
    }
    
    @Override
    public String toString(){
        return "\n"+nombre;
    }  
}
