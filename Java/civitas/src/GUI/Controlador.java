package GUI;
import civitas.CivitasJuego;
import civitas.GestionesInmobiliarias;
import civitas.OperacionInmobiliaria;
import civitas.Jugador;
import civitas.OperacionesJuego;
import civitas.SalidasCarcel;
import java.util.ArrayList;

public class Controlador {
    private CivitasJuego juego;
    private CivitasView vista;
    
    public Controlador(CivitasJuego juego, CivitasView vista) {
        this.juego = juego;
        this.vista = vista;
    }
    
    void juega(){
        vista.setCivitasJuego(juego);
        while(juego.finalDelJuego()==false){
            vista.actualizarVista();
            OperacionesJuego siguiente = juego.siguientePaso();
            if(siguiente != OperacionesJuego.PASAR_TURNO){
                vista.mostrarEventos();
            }
            boolean fin = juego.finalDelJuego();
            if(fin == false){
                if(siguiente==OperacionesJuego.COMPRAR){
                        Respuestas respuesta = vista.comprar();
                        if(respuesta == Respuestas.SI){
                            juego.comprar();
                            juego.siguientePasoCompletado(OperacionesJuego.COMPRAR);
                        }
                        else{
                            juego.siguientePasoCompletado(OperacionesJuego.COMPRAR);
                        }
                }
                if(siguiente==OperacionesJuego.GESTIONAR){
                    vista.gestionar();
                    int ges = vista.getGestion();
                    int pro = vista.getPropiedad();
                    OperacionInmobiliaria operacionInmo = new OperacionInmobiliaria(pro, GestionesInmobiliarias.values()[ges]);
                    if(GestionesInmobiliarias.CANCELAR_HIPOTECA == operacionInmo.getGestion()){
                        juego.cancelarHipoteca(operacionInmo.getNumPropiedad());
                    }
                    if(GestionesInmobiliarias.CONSTRUIR_CASA == operacionInmo.getGestion()){
                        juego.construirCasa(operacionInmo.getNumPropiedad());
                    }
                    if(GestionesInmobiliarias.CONSTRUIR_HOTEL == operacionInmo.getGestion()){
                        juego.construirHotel(operacionInmo.getNumPropiedad());
                    }
                    if(GestionesInmobiliarias.HIPOTECAR == operacionInmo.getGestion()){
                        juego.hipotecar(operacionInmo.getNumPropiedad());
                    }
                    if(GestionesInmobiliarias.VENDER == operacionInmo.getGestion()){
                        juego.vender(operacionInmo.getNumPropiedad());
                    }
                    if(GestionesInmobiliarias.TERMINAR == operacionInmo.getGestion()){
                        juego.siguientePasoCompletado(OperacionesJuego.GESTIONAR);
                    }
                }
                if(siguiente == OperacionesJuego.SALIR_CARCEL){
                    SalidasCarcel salida = vista.salirCarcel();
                    if(salida == SalidasCarcel.PAGANDO){
                        juego.salirCarcelPagando();
                    }
                    else{
                        juego.salirCarcelTirando();
                    }
                    juego.siguientePasoCompletado(OperacionesJuego.SALIR_CARCEL);
                }
            }
            System.out.println("********************************************\n");
        }
        juego.ranking();
    }
}
