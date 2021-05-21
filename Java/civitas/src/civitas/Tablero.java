package civitas;
import java.util.ArrayList;

public class Tablero {
    private int numCasillaCarcel;
    private ArrayList<Casilla> casillas = new ArrayList<>();
    private int porSalida;
    private boolean tieneJuez;
    
    
    Tablero(int casillaCarcel){
        if(casillaCarcel < 1)
            numCasillaCarcel = 1;
        else
            numCasillaCarcel = casillaCarcel;
        
        Casilla salida = new Casilla("Salida");
        casillas.add(salida);
        porSalida = 0;
        tieneJuez = false;
    }


    private boolean correcto(){
        if((casillas.size() > numCasillaCarcel) && tieneJuez == true){
            return true;
        }
        else{
            return false;
        }
            
    }
    
    private boolean correcto(int numCasilla){
        if(correcto() && numCasilla >= 0 && numCasilla < 20)
            return true;
        else
            return false;
    }
    int size(){
        return casillas.size();
    }
    public int getCarcel() {
        return numCasillaCarcel;
    }

    public Casilla getCasilla(int numCasilla) {
        if(correcto(numCasilla))
            return casillas.get(numCasilla);
        else
            return null;
    }

    public int getPorSalida() {
        if(porSalida > 0){
            int v1 = porSalida;
            porSalida--;
            return v1;
        }
        else
            return porSalida;
    }

    public boolean isTieneJuez() {
        return tieneJuez;
    }
    
    public void aniadeCasilla(Casilla casilla){
        if(casillas.size() == numCasillaCarcel){
            casillas.add(new Casilla("Carcel"));
            casillas.add(casilla);
        }
        else
            casillas.add(casilla);
                    
    }
    
    public void aniadeJuez(){
        if(tieneJuez == false){
            Casilla juez = new CasillaJuez(5, "Juez");
            casillas.add(juez);
            tieneJuez=true;
        }
    }
    
    public int nuevaPosicion(int actual, int tirada){
        if(!correcto())
            return -1;
        else{
            int suma = actual+tirada;
            int sumaF=suma%20;
            if(sumaF != suma){
                porSalida++;
                System.out.println(porSalida);
            }
            return sumaF;
        }
    }
    
    public int calcularTirada(int origen, int destino){
        int tirada = destino-origen;
        if(tirada >= 0)
            return tirada;
        else{
            tirada = tirada + 20;
            return tirada;
       }
            
                    
        
    }
   
}
