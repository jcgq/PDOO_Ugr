package civitas;

import java.util.ArrayList;
import java.util.Collections;


public class MazoSorpresa {
    private ArrayList<Sorpresa> sorpresas;
    private boolean barajada;
    private int usadas;
    private boolean debug;
    private ArrayList<Sorpresa> cartasEspeciales;
    private Sorpresa ultimaSorpresa;
    
    void init(){
        sorpresas = new ArrayList();
        cartasEspeciales = new ArrayList();
        barajada = false;
        usadas = 0;
    }

    public MazoSorpresa() {
        init();
        debug = false;    
    }

    public MazoSorpresa(boolean _debug) {
        this.debug = _debug;
        init();
        if(debug ==true){
            Diario.getInstance().ocurreEvento("Debug en True");
        }
    }
    
    void alMazo(Sorpresa s){
        if(barajada == false){
            sorpresas.add(s);
        }
    }
    
    Sorpresa siguiente() {
        if((barajada == false || usadas == sorpresas.size()) && debug == false){
            barajada = true;
            usadas = 0;
            Collections.shuffle(sorpresas);
        }
        
        usadas++;
        ultimaSorpresa = sorpresas.get(0);
        sorpresas.remove(0);
        sorpresas.add(sorpresas.size()-1, ultimaSorpresa);
        return ultimaSorpresa;
    }
    
    void inhabilitarCartaEspecial(Sorpresa sorpresa){
        for(int i = 0; i <sorpresas.size(); i++){
            if(sorpresas.get(i) == sorpresa){
                cartasEspeciales.add(sorpresa);
                sorpresas.remove(i);
                Diario.getInstance().ocurreEvento("Carta Sorpresa Inhabilitada For Ever");
            }
        }
    }
    void habilitarCartaEspecial(Sorpresa sorpresa){
        for(int i = 0; i <cartasEspeciales.size(); i++){
            if(cartasEspeciales.get(i) == sorpresa){
                sorpresas.add(sorpresas.size()-1, cartasEspeciales.get(i));
                cartasEspeciales.remove(i);
                Diario.getInstance().ocurreEvento("Carta Sorpresa Habilitada For Ever");
            }
        }
    }

    int size() {
        return sorpresas.size();
    }
    
    Sorpresa getUltimaSorpresa(){
        return this.ultimaSorpresa;
    }
    
    
    Sorpresa getSorpresas(int i){
        return sorpresas.get(i);
    }
    
}
