package civitas;

import GUI.Dado;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;

public class CivitasJuego {
    private int indiceJugadorActual;
    private EstadosJuego estado;
    private GestorEstados gestorEstados;
    private Tablero tablero;
    private ArrayList<Jugador> jugadores;
    private MazoSorpresa mazo;
    
    private TituloPropiedad titulo1 = new TituloPropiedad("Despacho de Nuria", (float) 100, 1, 100, 500, 20);
    private TituloPropiedad titulo2 = new TituloPropiedad("Despacho de Kawtar", (float) 120, 1, 150, 520, 25);
    
    
    public CivitasJuego(ArrayList<String> _nombres){
        jugadores = new ArrayList<>();
        for(int i=0; i<_nombres.size(); i++){
            Jugador _jugador = new Jugador(_nombres.get(i));
            jugadores.add(_jugador);
        }
        gestorEstados = new GestorEstados();
        mazo = new MazoSorpresa();
        tablero = new Tablero(5);
        this.inicializarTablero(mazo);
        this.inicializarMazoSorpresas(tablero);
        estado = gestorEstados.estadoInicial();;
        this.indiceJugadorActual = Dado.getInstance().quienEmpieza(jugadores.size());
        
    }
    
    void actualizarInfo(){
        jugadores.get(this.indiceJugadorActual).toString();
        if(jugadores.get(this.indiceJugadorActual).enBancarrota()){
            ArrayList<Jugador> _jugadores = new ArrayList<>();
            _jugadores = this.ranking();
            System.out.println("El Ranking es ");
            for(int i=0; i<_jugadores.size(); i++){
                System.out.println(_jugadores.get(i).toString());
            }
        }
    }
    private void avanzaJugador(){
        Jugador jugadorActual = this.getJugadorActual();
        int posicionActual = jugadorActual.getNumCasillaActual();
        int tirada = Dado.getInstance().tirar();
        int posicionNueva = tablero.nuevaPosicion(posicionActual, tirada);
        Casilla casilla = tablero.getCasilla(posicionNueva);
        this.contabilizarPasosPorSalida(jugadorActual);
        jugadorActual.moverACasilla(posicionNueva);
        casilla.recibeJugador(indiceJugadorActual, jugadores);
        Diario.getInstance().ocurreEvento("La casilla es " + (this.tablero.getCasilla(jugadorActual.getNumCasillaActual())).toString());
        this.contabilizarPasosPorSalida(jugadorActual);
    }
    
    public boolean cancelarHipoteca(int _ip){
        Jugador jugadorA = this.jugadores.get(indiceJugadorActual);
        return jugadorA.cancelarHipoteca(_ip);
    }
    
    public boolean comprar(){
        Jugador jugadorActual=jugadores.get(indiceJugadorActual);
        int numCasillaActual = jugadorActual.getNumCasillaActual();
        Casilla casilla = tablero.getCasilla(numCasillaActual);
        TituloPropiedad titulo =((CasillaCalle) casilla).getTituloPropiedad();
        boolean res = jugadorActual.comprar(titulo);
        return res;
    }
    
    public boolean construirCasa(int _ip){
        Jugador jugadorA = this.jugadores.get(indiceJugadorActual);
        return jugadorA.construirCasa(_ip);
    }
    
    public boolean construirHotel(int _ip){
        Jugador jugadorA = this.jugadores.get(indiceJugadorActual);
        return jugadorA.construirHotel(_ip);
    }
    
    private void contabilizarPasosPorSalida(Jugador _jugadorActual){
        if(tablero.getPorSalida()>0)
            _jugadorActual.pasaPorSalida();
    }
    
    public boolean finalDelJuego(){
        boolean fin=false;
        for(Jugador j: jugadores){
            if(j.enBancarrota())
                fin=true;
        }
        return fin;
    }
    
    public Casilla getCasillaActual(){
        int indice = jugadores.get(indiceJugadorActual).getNumCasillaActual();
        return tablero.getCasilla(indice);
    }
    
    public Jugador getJugadorActual(){
        return jugadores.get(indiceJugadorActual);
    }
    
    public boolean hipotecar(int _ip){
        Jugador jugadorA = this.jugadores.get(indiceJugadorActual);
        return jugadorA.hipotecar(_ip);
    }
    
    public String infoJugadorTexto(){
        Jugador jugadorA = this.jugadores.get(indiceJugadorActual);
        return jugadorA.toString();
    }
    
    public void inicializarMazoSorpresas(Tablero _tablero){
        Sorpresa sorpresa1 = new SorpresaPorCasaHotel(-120, "Tienes que pagar 120 euros para arreglar el megáfono de Mancia");
        Sorpresa sorpresa2 = new SorpresaIraCarcel(_tablero);
        Sorpresa sorpresa3 = new SorpresaIraCasilla(_tablero, -150, "Pagas 150 y vas a una casilla");
        Sorpresa sorpresa4 = new SorpresaSalirCarcel(this.mazo);
        Sorpresa sorpresa5 = new SorpresaPorJugador(500, "Te ha pilado copiándote Nacho (DDSI) paga a tus compañeros para que no te delanten");
        Sorpresa sorpresa6 = new SorpresaPagarCobrar(1000, "Has ganado un pulso a Extremera, recibe 1000 euros de un Proxeneta");
        Sorpresa sorpresa7 = new SorpresaEspeculador(1000);
        
        Sorpresa sorpresa8 = new SorpresaConvierteCalle(1,"Convertir y robar", ((CasillaCalle)_tablero.getCasilla(1)).getTituloPropiedad() , titulo1, _tablero);
        Sorpresa sorpresa9 = new SorpresaConvierteCalle(1,"Convertir y robar", ((CasillaCalle)_tablero.getCasilla(8)).getTituloPropiedad() , titulo2, _tablero);
        mazo.alMazo(sorpresa8);
        mazo.alMazo(sorpresa9);
        mazo.alMazo(sorpresa8);
        mazo.alMazo(sorpresa9);
        mazo.alMazo(sorpresa8);
        mazo.alMazo(sorpresa9);
        mazo.alMazo(sorpresa8);
    }
    
    public void inicializarTablero(MazoSorpresa _mazoSorpresas){
        TituloPropiedad titulo1 = new TituloPropiedad("Despacho de Lastra", (float) 100, 1, 100, 500, 20);
        TituloPropiedad titulo2 = new TituloPropiedad("Despacho de Cabrerizo", (float) 120, 1, 150, 520, 25);
        TituloPropiedad titulo3 = new TituloPropiedad("Despacho de Baldomero", (float) 160, 2, 200, 540, 40);
        TituloPropiedad titulo4 = new TituloPropiedad("Despacho de Capel", (float) 180, 2, 250, 560, 45);
        TituloPropiedad titulo5 = new TituloPropiedad("Despacho de Oresti", (float) 220, 2, 300, 580, 60);
        TituloPropiedad titulo6 = new TituloPropiedad("Despacho de Anguita", (float) 260, 3, 300, 600, 65);
        TituloPropiedad titulo7 = new TituloPropiedad("Despacho de Melero", (float) 280, 3, 400, 620, 70);
        TituloPropiedad titulo8 = new TituloPropiedad("Despacho de La'Patri", (float) 300, 3, 450, 640, 75);
        TituloPropiedad titulo9 = new TituloPropiedad("Despacho de Extremera", (float) 350, 4, 500, 660, 85);
        TituloPropiedad titulo10 = new TituloPropiedad("Despacho de Sevilla", (float) 400, 4, 600, 680, 100);
        TituloPropiedad titulo11 = new TituloPropiedad("Despacho de Urbano", (float) 450, 5, 700, 750, 200);
        TituloPropiedad titulo12 = new TituloPropiedad("Despacho de Mancia", (float) 500, 5, 750, 800, 300);
                
        Casilla casilla1 = new CasillaCalle(titulo1);
        Casilla casilla2 = new CasillaSorpresa(_mazoSorpresas, "Examen Sorpresa de Manuel Capel: PON EL CÓDIGO: ExamenUbu16");    
        Casilla casilla3 = new CasillaCalle(titulo2);     
        Casilla casilla4 = new Casilla("Has entrado en el despacho de Lozano. Tómate un descanso");    
        Casilla casilla5 = new CasillaCalle(titulo3);
        Casilla casilla6 = new CasillaCalle(titulo4);
        Casilla casilla7 = new CasillaCalle(titulo5);
        Casilla casilla8 = new CasillaSorpresa(_mazoSorpresas, "Te has topado con Pedro Smith, por guardar su maletin te da una SORPRESA");
        Casilla casilla9 = new CasillaCalle(titulo6);
        Casilla casilla10 = new CasillaCalle(titulo7);
        Casilla casilla11 = new CasillaImpuesto( (int) -120.0, "Control de alcoholemia. Sevilla y tú habeís sido parados por la poli despues de la fiesta");
        Casilla casilla12 = new CasillaCalle(titulo8);
        Casilla casilla13 = new CasillaCalle(titulo9);
        Casilla casilla14 = new CasillaCalle(titulo10);
        Casilla casilla15 = new CasillaSorpresa(_mazoSorpresas, "SORPRESA! Te la las pácticas Nawja! Coge una sorpesa");
        Casilla casilla16 = new CasillaCalle(titulo11);
        Casilla casilla17 = new CasillaCalle(titulo12);
        
        tablero.aniadeCasilla(casilla1);
        tablero.aniadeCasilla(casilla3);
        tablero.aniadeCasilla(casilla2);
        tablero.aniadeCasilla(casilla8);
        tablero.aniadeCasilla(casilla4);
        tablero.aniadeCasilla(casilla5);
        tablero.aniadeCasilla(casilla6);
        tablero.aniadeCasilla(casilla7);
        tablero.aniadeCasilla(casilla9);
        tablero.aniadeJuez();
        tablero.aniadeCasilla(casilla10);
        tablero.aniadeCasilla(casilla11);
        tablero.aniadeCasilla(casilla12);
        tablero.aniadeCasilla(casilla13);
        tablero.aniadeCasilla(casilla14);
        tablero.aniadeCasilla(casilla15);
        tablero.aniadeCasilla(casilla16);
        tablero.aniadeCasilla(casilla17);
    }
    
    public void pasarTurno(){
        this.indiceJugadorActual=(this.indiceJugadorActual+1)%this.jugadores.size();
    }
    
    public ArrayList<Jugador> ranking(){
        ArrayList<Jugador> _jugadores = new ArrayList<>();
        Collections.sort(jugadores);
        _jugadores=jugadores;
        return _jugadores;
    }
    
    public boolean salirCarcelPagando(){
        Jugador jugadorA = this.jugadores.get(indiceJugadorActual);
        return jugadorA.salirCarcelPagando();
    }
    
    public boolean salirCarcelTirando(){
        Jugador jugadorA = this.jugadores.get(indiceJugadorActual);
        return jugadorA.salirCarcelTirando();
    }
    
    public OperacionesJuego siguientePaso(){
        Jugador jugadorActual = jugadores.get(indiceJugadorActual);
        OperacionesJuego operacion = gestorEstados.operacionesPermitidas(jugadorActual, estado);
        if(operacion == OperacionesJuego.PASAR_TURNO){
            pasarTurno();
            siguientePasoCompletado(operacion);
        }
        else 
            if(operacion == OperacionesJuego.AVANZAR){
                avanzaJugador();
                siguientePasoCompletado(operacion);
        }
        return operacion;
    }
    
    public void siguientePasoCompletado(OperacionesJuego operacion){
        estado = gestorEstados.siguienteEstado(jugadores.get(indiceJugadorActual), estado, operacion);
    }
    
    public boolean vender(int _ip){
        Jugador jugadorA = this.jugadores.get(indiceJugadorActual);
        return jugadorA.vender(_ip);
    } 
    
    
    
    
    //EXAMEN
    public boolean robarPropiedad(){
        System.out.println("Estoy en robarPropiedad de Civitas");
        Jugador jugadorActual = jugadores.get(this.indiceJugadorActual);
        Casilla casilla = tablero.getCasilla(jugadorActual.getNumCasillaActual());
        TituloPropiedad tituloARobar = ((SorpresaCalle) casilla).getTituloPropiedadARobar();
        jugadorActual.robarPropiedad(tituloARobar);
        return true;
    }
}
