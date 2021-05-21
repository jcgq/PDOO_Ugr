package civitas;

import GUI.Dado;
import java.util.ArrayList;
import java.lang.Comparable;

public class Jugador implements Comparable<Jugador>{
    private String nombre;
    protected boolean encarcelado=false;
    protected static final int casasMax = 4;
    protected static final int casasPorHotel = 4;
    protected static final int hotelesMax = 4;
    private int numCasillaActual = 0;
    protected static final float pasoPorSalida = (float) 1000.0;
    protected static final float precioLibertad = (float) 200.0;
    private boolean puedeComprar;
    private float saldo = saldoInicial;
    private static final float saldoInicial = (float) 7500.0;
    private ArrayList<TituloPropiedad> propiedades;
    private SorpresaSalirCarcel salvoConducto = null;
    
    
    public Jugador(String _nombre){
        this.nombre=_nombre;
        propiedades = new ArrayList<>();
        saldo = saldoInicial;
        numCasillaActual=0;
        puedeComprar = false;
    } 
    
    Jugador(Jugador _jugador){
        this.nombre=_jugador.getNombre();
        this.encarcelado=_jugador.isEncarcelado();
        this.numCasillaActual=_jugador.getNumCasillaActual();
        this.puedeComprar=_jugador.getPuedeComprar();
        this.saldo=_jugador.getSaldo();
        this.propiedades=_jugador.getPropiedades();
        this.salvoConducto=_jugador.salvoConducto;
    }

    public String getNombre() {
        return nombre;
    }

    public boolean isEncarcelado() {
        return encarcelado;
    }

    public int getCasasMax() {
        return casasMax;
    }

    public int getCasasPorHotel() {
        return casasPorHotel;
    }

    public int getHotelesMax() {
        return hotelesMax;
    }

    public int getNumCasillaActual() {
        return numCasillaActual;
    }

    public static float getPasoPorSalida() {
        return pasoPorSalida;
    }

    public static float getPrecioLibertad() {
        return precioLibertad;
    }

    public boolean getPuedeComprar() {
        return puedeComprar;
    }

    public float getSaldo() {
        return saldo;
    }

    public static float getSaldoInicial() {
        return saldoInicial;
    }

    public ArrayList<TituloPropiedad> getPropiedades() {
        return propiedades;
    }
    
    public boolean tieneAlgoQueGestionar(){
        if(propiedades.isEmpty())
            return false;
        else
            return true;
    }
    
    
    public boolean paga(float cantidad){
        if(cantidad<0)
            return this.modificarSaldo(cantidad);
        else
            return this.modificarSaldo(cantidad*(-1));
    }
    public boolean modificarSaldo(float cantidad){
        this.saldo+=cantidad;
        Diario.getInstance().ocurreEvento("Se modifica el saldo");
        return true;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    

    protected boolean cancelarHipoteca(int _ip){
        boolean result = false;
        if(this.encarcelado){
            return result;
        }
        if(this.existeLaPropiedad(_ip)){
            TituloPropiedad propiedad=propiedades.get(_ip);
            float cantidad = propiedad.getImporteCancelarHipoteca();
            boolean _puedoGastar=this.puedoGastar(cantidad);
            if(_puedoGastar){
                result = propiedad.cancelarHipoteca(this);
                if(result){
                    Diario.getInstance().ocurreEvento("El jugador " + this.nombre + " cancela la hipoteca de la propiedad " + _ip);
                }
            }
        }
        return result;
    }

    public void setPuedeComprar(boolean puedeComprar) {
        this.puedeComprar = puedeComprar;
    }
    
    
    
    protected int cantidadCasasHoteles(){
        int nC=0, nH=0, total;
        for(int i=0; i<this.propiedades.size(); i++){
            nC+=this.propiedades.get(i).getNumCasas();
            nH+=this.propiedades.get(i).getNumHoteles();
        }
        total=nC+nH;
        return total;
    }
    @Override
    public int compareTo(Jugador otroJugador){
        int saldoOtro=(int) ((Jugador) otroJugador).getSaldo();
        return (int) (saldoOtro-this.getSaldo());    
    }
    

    protected boolean comprar(TituloPropiedad titulo){
        boolean result = false;
        if(encarcelado==false && titulo.tienePropietario()==false){
            if(puedeComprar){
                float precio = titulo.getPrecioCompra();
                if(puedoGastar(precio)){
                    result = titulo.comprar(this);
                    if(result){
                        propiedades.add(titulo);
                        Diario.getInstance().ocurreEvento("El jugador " + nombre + " ha comprado " + titulo.toString());
                    }
                    puedeComprar=false;
                }
            }
        }
        return result;
    }

    protected boolean construirHotel(int _ip){
        boolean result = false;
        if(this.encarcelado){
            return result;
        }
        if(this.existeLaPropiedad(_ip)){
            TituloPropiedad propiedad = propiedades.get(_ip);
            boolean puedoEdificarHotel = this.puedoEdificarHotel(propiedad);
            float precio = propiedad.getPrecioEdificar();
            
            if(this.puedoGastar(precio) && propiedad.getNumHoteles()<this.getHotelesMax() && propiedad.getNumCasas()>=this.getCasasPorHotel()){
                puedoEdificarHotel=true;
            }
            if(puedoEdificarHotel){
                result = propiedad.construirHotel(this);
                int casasPorHotel = this.getCasasPorHotel();
                propiedad.derruirCasa(casasPorHotel, this);
                if(result){
                    Diario.getInstance().ocurreEvento("El jugador " + this.nombre + " construye hotel en la propiedad " + _ip);
                }
            }
        }
        return result;
    }

    protected boolean construirCasa(int _ip){
        boolean result = false;
        if(this.encarcelado){
            return result;
        }
        if(this.existeLaPropiedad(_ip)){
            TituloPropiedad propiedad = propiedades.get(_ip);
            boolean puedoEdificarCasa = this.puedoEdificarCasa(propiedad);
            float precio = propiedad.getPrecioEdificar();
            
            if(this.puedoGastar(precio) && propiedad.getNumCasas()<this.getCasasMax()){
                puedoEdificarCasa=true;
            }
            if(puedoEdificarCasa){
                result = propiedad.construirCasa(this);
                if(result){
                    Diario.getInstance().ocurreEvento("El jugador " + this.nombre + " construye casa en la propiedad " + _ip);
                }
            }
        }
        return result;
    }
    
    protected boolean debeSerEncarcelado(){
        if(this.isEncarcelado())
            return false;
        else{
            if(!this.tieneSalvoconducto())
                return true;
            else{
                this.perderSalvoconducto();
                Diario.getInstance().ocurreEvento("Jugador salvado de la carcel");
                return false;
            }
            
        }
    }
    
    boolean enBancarrota(){
        if(this.saldo<0)
            return true;
        else
            return false;
    }
    
    boolean encarcelar(int _numCasillaCarcel){
        if(this.debeSerEncarcelado()){
            this.moverACasilla(_numCasillaCarcel);
            this.encarcelado=true;
            Diario.getInstance().ocurreEvento("Jugador encarcelado");
        }
        return this.encarcelado;
    }
    
    private boolean existeLaPropiedad(int _ip){
        boolean solucion= false;
        for(int i=0; i<propiedades.size(); i++){
		if(_ip==i)
			solucion =  true;
		
                
	}
        return solucion;
        
    }

    boolean hipotecar(int _ip){
        boolean result = false;
        if(this.encarcelado){
            return result;
        }
        if(this.existeLaPropiedad(_ip)){
            TituloPropiedad propiedad = propiedades.get(_ip);
            result = propiedad.hipotecar(this);
        }
        if(result){
            Diario.getInstance().ocurreEvento("El jugador " + this.nombre + " hipoteca la propiedad " + _ip);
        }
        return result;
    }
    
    boolean moverACasilla(int _numCasilla){
        if(this.isEncarcelado())
            return false;
        else{
            this.numCasillaActual=_numCasilla;
            this.puedeComprar=false;
            Diario.getInstance().ocurreEvento("Se ha movido a la casilla " + _numCasilla);
            return true;
        }
    }
    
    boolean obtenerSalvoconducto(SorpresaSalirCarcel _sorpresa){
        if(this.isEncarcelado())
            return false;
        else{
            this.salvoConducto=_sorpresa;
            return true;
        }
    }
    
    boolean pagaAlquiler(float _cantidad){
        if(this.encarcelado)
            return false;
        else{
            return this.paga(_cantidad);
        }
    }
    
    boolean pagaImpuesto(float _cantidad){
        if(this.encarcelado)
            return false;
        else{
            return this.paga(_cantidad);
        }
    }
    
    boolean pasaPorSalida(){
        this.modificarSaldo(pasoPorSalida);
        Diario.getInstance().ocurreEvento("Se pasa por la casilla de salida");
        return true;
    }
    
    void perderSalvoconducto(){
        salvoConducto.usada();
        this.salvoConducto=null;
    }
    
    boolean puedeComprarCasilla(){
        if(this.encarcelado==true)
            this.puedeComprar=false;
        else
            this.puedeComprar=true;
        
        return this.puedeComprar;
    }
    
    private boolean puedeSalirCarcelPagando(){
        if(this.saldo>=precioLibertad)
            return true;
        else
            return false;
    }
    
    private boolean puedoEdificarCasa(TituloPropiedad _propiedad){
        if(saldo>=_propiedad.getPrecioEdificar() && this.nombre.equals(_propiedad.getPropietario().getNombre()))
            if(_propiedad.getNumCasas()<4)
                return true;
            else
                return false;
        else
            return false;
        
    }
    
    private boolean puedoEdificarHotel(TituloPropiedad _propiedad){
        if(saldo>=_propiedad.getPrecioEdificar() && this.nombre.equals(_propiedad.getPropietario().getNombre()))
            if(_propiedad.getNumCasas()==4 && _propiedad.getNumHoteles()<4)
                return true;
            else
                return false;
        else
            return false;
    }
    
    private boolean puedoGastar(float precio){
        if(this.isEncarcelado())
            return false;
        else{
            if(this.saldo>=precio)
                return true;
            else
                return false;
        }
    }
    
    boolean recibe(float cantidad){
         if(this.encarcelado==true)
             return false;
         else{
             this.modificarSaldo(cantidad);
             return true;
         }
     }
     
    boolean salirCarcelPagando(){
        if(this.isEncarcelado() && this.getSaldo()>=precioLibertad){
            this.paga(precioLibertad);
            this.encarcelado=false;
            Diario.getInstance().ocurreEvento("El jugador sale de la carcel pagando");
            return true;
        }
        else
            return false;
    } 
    
    boolean salirCarcelTirando(){
        boolean resultado = Dado.getInstance().salgoDeLaCarcel();
        if(resultado==true){
            this.encarcelado=false;
            Diario.getInstance().ocurreEvento("Sale de la carcel tirando");
        }
        return resultado;
            
    }
    
    boolean tieneSalvoconducto(){
        if(this.salvoConducto==null)
            return false;
        else
            return true;
    }
    
     boolean vender(int _ip){
        if(this.encarcelado==true)
             return false;
         else{
             this.existeLaPropiedad(_ip);
             if(this.propiedades.get(_ip).vender(this)){
                 this.propiedades.remove(_ip);
                 Diario.getInstance().ocurreEvento("Se ha vendido una propiedad");
                 return true;
             }
             else
                return false;
         }
    }
    
    @Override
    public String toString(){
        String texto="";
        texto= "Jugador { Nombre: "+ nombre + " ,saldo: " + saldo;
        if(encarcelado)
            texto+=", estoy encarcelado.";
        else
            texto+=", no estoy encarcelado.";
        
        if(propiedades.isEmpty()){
            texto += " No tienes propiedades... (Por ahora) ";
        }
        else{
            texto+=" Mis propiedades son: \n";
        
            for(TituloPropiedad p: propiedades){
                texto+=p.toString() + "\n";
            }
        }
        
        return texto;
    }
    
    
    public boolean soyEspeculador(){
        return false;
    }
   
       
    
    //EXAMEN
   public void robarPropiedad(TituloPropiedad _titulo){
       _titulo.actualizaPropietarioPorConversion(this);
   }
}
