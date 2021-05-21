package civitas;

import java.util.ArrayList;

//@author JuanCarlos Gonzalez Quesada
//EXAMEN

public class CasillaCalle extends Casilla{
    private TituloPropiedad titulo;
    private float importe;
    
    CasillaCalle(TituloPropiedad _titulo){
        super(_titulo.getNombre());
        titulo= _titulo;
        importe=_titulo.getPrecioCompra();
    }
        
    @Override
    public void recibeJugador(int iActual, ArrayList<Jugador> todos){
        System.out.println("Estoy en recibe jugador de CasillaCalle");
        if(super.jugadorCorrecto(iActual, todos)){
            super.informe(iActual, todos);
            if(!titulo.tienePropietario()){
                todos.get(iActual).puedeComprarCasilla();
                todos.get(iActual).setPuedeComprar(true);
            }
            else{
                titulo.tramitarAlquiler(todos.get(iActual));
                todos.get(iActual).setPuedeComprar(false);
            }
        }
    }
    
    public TituloPropiedad getTituloPropiedad(){
        return titulo;
    }
    
    @Override
    public String toString(){
        return "\nHas caido en " + super.getNombre() +"\n El importe es: " + Float.toString(importe) ;
    }
}
