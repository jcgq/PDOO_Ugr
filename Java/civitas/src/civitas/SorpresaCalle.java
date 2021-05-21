package civitas;

import java.util.ArrayList;
//@author JuanCarlos Gonzalez Quesada
//EXAMEN
public class SorpresaCalle extends CasillaCalle{
    
    TituloPropiedad tituloRobado;
    
    public SorpresaCalle(TituloPropiedad _titulo1, TituloPropiedad _titulo2) {
        super(_titulo1);
        tituloRobado = _titulo2;
    }
    
    public TituloPropiedad getTituloPropiedadARobar(){
        return tituloRobado;
    }
    
    @Override
    public void recibeJugador(int iActual, ArrayList<Jugador> todos){
        System.out.println("Estoy en recibeJugador de SorpresaCalle");
        if(super.jugadorCorrecto(iActual, todos)){
            super.informe(iActual, todos);
            if(!tituloRobado.tienePropietario()){
                todos.get(iActual).puedeComprarCasilla();
                todos.get(iActual).setPuedeComprar(true);
                todos.get(iActual).robarPropiedad(tituloRobado);
                Diario.getInstance().ocurreEvento("Se va a robar el titulo " + tituloRobado.toString());
            }
            else{
                tituloRobado.tramitarAlquiler(todos.get(iActual));
                todos.get(iActual).setPuedeComprar(false);
            }
        }
    }
    
    
}
