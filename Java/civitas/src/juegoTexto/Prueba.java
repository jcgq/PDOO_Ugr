package juegoTexto;

import civitas.CivitasJuego;
import GUI.Dado;
import civitas.Jugador;
import java.util.ArrayList;

public class Prueba {
        private static VistaTextual vista = new VistaTextual();
        private static CivitasJuego civitas;
        private static Controlador controlador;
    
    public static void main(String[] args) {
        
        System.out.println("BIENVENIDOS AL MONOPOLY DE LA ETSIIT (II)");
        Jugador jugador1 = new Jugador("JuanCarlos");
        Jugador jugador2 = new Jugador("Pedro");
        ArrayList<String> jugadores = new ArrayList<>();
        jugadores.add(jugador1.getNombre());
        jugadores.add(jugador2.getNombre());
        civitas = new CivitasJuego(jugadores);
        controlador = new Controlador(civitas, vista);
        Dado.getInstance().setDebug(true);
        controlador.juega();    
    }
    
}
