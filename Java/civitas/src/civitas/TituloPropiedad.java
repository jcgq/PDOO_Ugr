package civitas;

public class TituloPropiedad {
    private String nombre;
    private float alquilerBase;
    private float factorDeRevalorizacion;
    private float precioHipotecaBase;
    private float precioCompra;
    private float precioEdificar;
    private boolean hipotecado;
    private static float factorInteresesHipoteca = (float) 1.1;
    private int numCasas;
    private int numHoteles;
    private Jugador propietario = null;
    
    
    TituloPropiedad(String _nombre, float _alquilerBase, int _factorDeRevalorizacion, int _precioHipotecaBase, int _precioCompra, int _precioPorEdificar){
        this.propietario = null;
        nombre=_nombre;
        alquilerBase = _alquilerBase;
        factorDeRevalorizacion = _factorDeRevalorizacion;
        precioHipotecaBase = _precioHipotecaBase;
        precioCompra = _precioCompra;        
        precioEdificar = _precioPorEdificar;
        numCasas=0;
        numHoteles=0;
        hipotecado=false;
    }

    public String getNombre() {
        return nombre;
    }

    public float getAlquilerBase() {
        return alquilerBase;
    }

    public float getFactorDeRevalorizacion() {
        return factorDeRevalorizacion;
    }

    public float getPrecioHipotecaBase() {
        return precioHipotecaBase;
    }

    public float getPrecioCompra() {
        return precioCompra;
    }

    public float getPrecioEdificar() {
        return precioEdificar;
    }

    public boolean getHipotecado() {
        return hipotecado;
    }

    public float getFactorInteresesHipoteca() {
        return factorInteresesHipoteca;
    }

    public int getNumCasas() {
        return numCasas;
    }

    public int getNumHoteles() {
        return numHoteles;
    }
    
    public float getPrecioAlquiler(){
        if(hipotecado==true || propietario.isEncarcelado())
            return 0;
        else
            return this.alquilerBase;
    }
    
    public boolean propietarioEncarcelado(){
        return this.propietario.isEncarcelado();
    }
    
    public float getImporteCancelarHipoteca(){
        return this.precioHipotecaBase*factorInteresesHipoteca;
    }
    
    public boolean cancelarHipoteca(Jugador _jugador){
        if(this.esEsteElPropietario(_jugador) && this.hipotecado==true){
            _jugador.paga(this.getImporteCancelarHipoteca());
            this.hipotecado=false;
            return true;
        }
        else
            return false;
        
    }
    
    public boolean esEsteElPropietario(Jugador _jugador){
        return this.propietario==_jugador;
    }
    @Override
    public String toString(){
        String str;
        str= "\n Titulo: {" + "nombre=" + nombre + ", precio de compra= " + Float.toString(precioCompra) + ", alquiler de base= " + Float.toString(alquilerBase) + "factor de revalorización=" + factorDeRevalorizacion + ", precio hipoteca= " + Float.toString(precioHipotecaBase) + ", coste de edificar= " + Float.toString(precioEdificar) + ", numero de casas= "  + Integer.toString(numCasas) + "numero de hoteles=" + Integer.toString(numHoteles) +  "}";
    
        if(hipotecado==false)
            str=str +"Propiedad NO hipotecada }";
        else
            str= str + "Propiedad Hipotecada }";
        
        return str;
    }
            
    void actualizaPropietarioPorConversion(Jugador _jugador){
       propietario = _jugador;
   } 
   
   
   private int cantidadCasasHoteles(){
       return this.numCasas+this.numHoteles;
   }
   
   
   boolean comprar(Jugador _jugador){
       boolean result = false;
       if(this.tienePropietario()==false){
           this.actualizaPropietarioPorConversion(_jugador);
           propietario.paga(this.precioCompra);
           result = true;
       }
       return result;
   }
   
    boolean construirCasa(Jugador _jugador){
       boolean resul=false;
       if(_jugador==this.propietario){
           _jugador.paga(this.getPrecioEdificar());
           this.numCasas++;
           resul=true;
       }
       return resul;
   }
   
    boolean construirHotel(Jugador _jugador){
       boolean resul=false;
       if(_jugador==this.propietario){
           _jugador.paga(this.getPrecioEdificar());
           this.numHoteles++;
           resul=true;
       }
       return resul;
   }
   
    boolean derruirCasa(int n, Jugador _jugador){
       if(_jugador==this.propietario && this.numCasas>=n){
           this.numCasas=this.numCasas-n;
           return true;
       }
       else
           return false;
   }
   
   
   private float getPrecioVenta(){
       return (float) this.precioCompra+((this.precioEdificar*this.getNumCasas()+this.precioEdificar*this.getNumHoteles())*this.factorDeRevalorizacion);
   }
   
   protected Jugador getPropietario(){
       return this.propietario;
   }
   
   protected boolean hipotecar(Jugador _jugador){
       if(this.hipotecado==false && _jugador==this.propietario){
           _jugador.recibe(this.precioHipotecaBase);
           this.hipotecado=true;
           return true;
       }
       else
           return false;
   }
   
    boolean tienePropietario(){
       if(this.propietario==null)
           return false;
       else
           return true;
   }   
   
   protected void tramitarAlquiler(Jugador _jugador){
       if(this.tienePropietario() && _jugador!=this.propietario){
           _jugador.pagaAlquiler(alquilerBase);
           this.propietario.recibe(alquilerBase);
       }
   }
   
   protected boolean vender(Jugador _jugador){
       if(_jugador==propietario){
		_jugador.modificarSaldo(this.getPrecioVenta());
		propietario=null;
		return true;
	}
	else
		return false;
        
   }
   
   
   
}
