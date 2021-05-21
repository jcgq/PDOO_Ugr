package civitas;

import java.util.ArrayList;

public class SorpresaSalirCarcel extends Sorpresa{
    private MazoSorpresa mazo;
    
    SorpresaSalirCarcel(MazoSorpresa _mazo){
        super("Quedas libre de la carcel");
        mazo = _mazo;
    }    
    
    @Override
    public void aplicarAJugador(int actual, ArrayList<Jugador> todos){
        if(super.jugadorCorrecto(actual, todos)){
            boolean nadieLaTiene=true;
            for(int i=0; i<todos.size(); i++){
                if(todos.get(i).tieneSalvoconducto())
                    nadieLaTiene=false;
            }
            if(nadieLaTiene){
                SorpresaSalirCarcel salirCarcel=new SorpresaSalirCarcel(this.mazo);
                todos.get(actual).obtenerSalvoconducto(salirCarcel);
                salirDelMazo();
            }
            
        }
    }
    
    void salirDelMazo(){
        mazo.inhabilitarCartaEspecial(this);
    }
    
    void usada(){
        mazo.habilitarCartaEspecial(this);
    }
    
    @Override
    public String toString(){
        return "Sorpresa{ " + super.getTexto() + " }";
                 
    }
    
}
