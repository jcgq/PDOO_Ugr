package juegoTexto;
import civitas.CivitasJuego;
import civitas.GestionesInmobiliarias;
import civitas.OperacionInmobiliaria;
import civitas.OperacionesJuego;
import civitas.Respuestas;
import civitas.SalidasCarcel;
import civitas.Casilla;
import java.util.Scanner;

public class Controlador {
    private CivitasJuego juego;
    private VistaTextual vista;
    private OperacionesJuego OperacionesJuego;
    private Scanner in;
    
    public Controlador(CivitasJuego juego, VistaTextual vista) {
        this.juego = juego;
        this.vista = vista;
    }
    
    void juega(){
        vista.setCivitasJuego(juego);
        while(juego.finalDelJuego()==false){
            vista.actualizarVista();
            vista.pausa();
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
                    int ges = vista.getiGestion();
                    int pro = vista.getiPropiedad();
                    OperacionInmobiliaria operacionInmo = new OperacionInmobiliaria(pro, GestionesInmobiliarias.values()[ges]);
                    if(GestionesInmobiliarias.CANCELAR_HIPOTECA == operacionInmo.getGestion()){
                        vista.juegoModel.cancelarHipoteca(operacionInmo.getNumPropiedad());
                    }
                    if(GestionesInmobiliarias.CONSTRUIR_CASA == operacionInmo.getGestion()){
                        vista.juegoModel.construirCasa(operacionInmo.getNumPropiedad());
                    }
                    if(GestionesInmobiliarias.CONSTRUIR_HOTEL == operacionInmo.getGestion()){
                        vista.juegoModel.construirHotel(operacionInmo.getNumPropiedad());
                    }
                    if(GestionesInmobiliarias.HIPOTECAR == operacionInmo.getGestion()){
                        vista.juegoModel.hipotecar(operacionInmo.getNumPropiedad());
                    }
                    if(GestionesInmobiliarias.VENDER == operacionInmo.getGestion()){
                        vista.juegoModel.vender(operacionInmo.getNumPropiedad());
                    }
                    if(GestionesInmobiliarias.TERMINAR == operacionInmo.getGestion()){
                        vista.juegoModel.siguientePasoCompletado(OperacionesJuego.GESTIONAR);
                    }
                }
                if(siguiente == OperacionesJuego.SALIR_CARCEL){
                    SalidasCarcel salida = vista.salirCarcel();
                    if(salida == SalidasCarcel.PAGANDO){
                        vista.juegoModel.salirCarcelPagando();
                    }
                    else{
                        vista.juegoModel.salirCarcelTirando();
                    }
                    vista.juegoModel.siguientePasoCompletado(OperacionesJuego.SALIR_CARCEL);
                }
            }
            System.out.println("********************************************\n");
        }
        vista.juegoModel.ranking();
    }
}
