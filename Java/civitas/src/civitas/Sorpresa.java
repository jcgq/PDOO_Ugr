package civitas;

import java.util.ArrayList;
public abstract class Sorpresa{
    private String texto;
    
    Sorpresa(String _texto){
        texto = _texto;
    }
    
    public abstract void aplicarAJugador(int actual, ArrayList<Jugador> todos);
   
    
    public void informe(int actual, ArrayList<Jugador> todos){
        Diario.getInstance().ocurreEvento("Se aplica sorpresa a " + todos.get(actual).getNombre()+
                "\nLa informacion de la sorpresa es " + this.toString());
    }
    
    public boolean jugadorCorrecto(int actual, ArrayList<Jugador> todos){
        if(actual>=0 && actual<todos.size())
            return true;
        else
            return false;
    }

    public String getTexto() {
        return texto;
    }
    
    
    
    @Override
    public String toString(){
        return "Sorpresa { " + " texto= " + texto + " ";
    }
    
}
