package GUI;

import civitas.CivitasJuego;
import java.util.ArrayList;

public class TestP5 {

    
    
    public static void main(String[] args) {
        CivitasView civitasview = new CivitasView();
        Dado.createInstance(civitasview);
        Dado.getInstance().setDebug(Boolean.TRUE);
        
        CapturaNombres capturas = new CapturaNombres(civitasview, true);
        
        ArrayList<String> nombres = new ArrayList<>();
        nombres = capturas.getNombres();
        
        CivitasJuego civitas = new CivitasJuego(nombres);
        Controlador controlador = new Controlador(civitas, civitasview);
        civitasview.setCivitasJuego(civitas);
        controlador.juega();
    }
    
}
