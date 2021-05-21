package civitas;


public class Especulador extends Jugador{
    private int fianza;
    private static int factorEspeculador = 2;
    private boolean soyEspe=false;
    
    Especulador(Jugador _jugador, int _fianza){
        super(_jugador);
        fianza = _fianza;
        soyEspe=true;
        for(int i=0; i<super.getPropiedades().size(); i++){
            _jugador.getPropiedades().get(i).actualizaPropietarioPorConversion(this);
        }
    }
    
    @Override
    boolean encarcelar(int _casillaCarcel){
        boolean resul=false;
        if(super.encarcelado==false){
            if(super.tieneSalvoconducto()==false){
                if(fianza>=super.getSaldo()){
                    resul = true;
                }
                else{
                    super.paga(fianza);
                    Diario.getInstance().ocurreEvento("El jugador " + super.getNombre() + "paga la fianza");
                }
            }
            else{
                resul=false;
                super.perderSalvoconducto();
                Diario.getInstance().ocurreEvento("El jugador " + super.getNombre() + "sale de la carcel por salvoconducto");
            }
        }  
        return resul;
    }
    @Override
    public boolean soyEspeculador(){
        return true;
    }
    
    @Override
    public int getCasasMax(){
        return(super.getCasasMax()*factorEspeculador);
    }
    
    @Override
    public int getHotelesMax(){
        return(super.getHotelesMax()*factorEspeculador);
    }
    
    @Override
    public boolean pagaImpuesto(float _cantidad){
        boolean resul = false;
        if(encarcelado==false){
            resul = paga(_cantidad/2);
            Diario.getInstance().ocurreEvento("El jugador " + super.getNombre() + " paga " + Float.toString(_cantidad/2) + " por un impuesto");
        }
        return resul;
    }
    
    @Override
    public String toString(){
        String texto="";
        texto= "Jugador { Nombre: "+ super.getNombre() + " ,saldo: " + super.getSaldo();
        if(encarcelado)
            texto+=", estoy encarcelado.";
        else
            texto+=", no estoy encarcelado ";
        texto+=" y soy un Especulador";
        return texto;
    }
    
}
