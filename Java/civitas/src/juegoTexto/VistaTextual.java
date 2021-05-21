package juegoTexto;

import civitas.CivitasJuego;
import civitas.Diario;
import civitas.OperacionesJuego;
import civitas.SalidasCarcel;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import civitas.Casilla;
import civitas.CivitasJuego;
import civitas.Jugador;
import civitas.OperacionesJuego;
import civitas.Respuestas;
import civitas.SalidasCarcel;
import civitas.TituloPropiedad;

class VistaTextual {
  
  CivitasJuego juegoModel; 
  int iGestion=-1;
  int iPropiedad=-1;
  private static String separador = "=====================";
  
  private Scanner in;
  
  VistaTextual () {
    in = new Scanner (System.in);
  }
  
  void mostrarEstado(String estado) {
    System.out.println (estado);
  }
              
  void pausa() {
    System.out.print ("Pulsa la tecla ENTER para avanzar");
    in.nextLine();
  }

  int leeEntero (int max, String msg1, String msg2) {
    Boolean ok;
    String cadena;
    int numero = -1;
    do {
      System.out.print (msg1);
      cadena = in.nextLine();
      try {  
        numero = Integer.parseInt(cadena);
        ok = true;
      } catch (NumberFormatException e) { // No se ha introducido un entero
        System.out.println (msg2);
        ok = false;  
      }
      if (ok && (numero < 0 || numero >= max)) {
        System.out.println (msg2);
        ok = false;
      }
    } while (!ok);

    return numero;
  }

  int menu (String titulo, ArrayList<String> lista) {
    String tab = "  ";
    int opcion;
    System.out.println (titulo);
    for (int i = 0; i < lista.size(); i++) {
      System.out.println (tab+i+"-"+lista.get(i));
    }

    opcion = leeEntero(lista.size(),
                          "\n"+tab+"Elige una opción: ",
                          tab+"Valor erróneo");
    return opcion;
  }

  SalidasCarcel salirCarcel() {
    int opcion = menu ("Elige la forma para intentar salir de la carcel",
    new ArrayList<> (Arrays.asList("Pagando","Tirando el dado")));
    return (SalidasCarcel.values()[opcion]);
  }

  Respuestas comprar() {
    int opcion = menu ("Elige si desea comprar la calle en la que ha caido",
    new ArrayList<> (Arrays.asList("NO","SI")));
    return (Respuestas.values()[opcion]);
  }

  void gestionar () {
      int opcion2 = 0;
      int opcion = menu ("Elige la accion que desea realizar", 
      new ArrayList<> (Arrays.asList("VENDER", "HIPOTECAR", "CANCELAR_HIPOTECA", "CONSTRUIR_CASA", "CONSTRUIR_HOTEL", "TERMINAR")));
      ArrayList<String> propiedades = new ArrayList<>();
      if(5 == opcion){
        iGestion=5;
      } 
      else {
        for(int i=0; i<juegoModel.getJugadorActual().getPropiedades().size(); i++){
            propiedades.add(juegoModel.getJugadorActual().getPropiedades().get(i).getNombre());
        }
        opcion2 = menu ("Elige la propiedad sobre la que realizar la operacion", propiedades);
        iGestion = opcion;
        iPropiedad = opcion2; 
      }
      
      
  }

    public int getiGestion() {
        return iGestion;
    }

    public int getiPropiedad() {
        return iPropiedad;
    }


  void mostrarSiguienteOperacion(OperacionesJuego operacion) {
      System.out.println("La siguiente operacion que se va a realizar es " + operacion.toString());
  }


  void mostrarEventos() {
      while(Diario.getInstance().eventosPendientes()){
          System.out.println(Diario.getInstance().leerEvento());
      }
  }
  
  public void setCivitasJuego(CivitasJuego civitas){ 
        juegoModel=civitas;

    }
  
  void actualizarVista(){
      System.out.println(juegoModel.getJugadorActual().toString());
      System.out.println(juegoModel.getCasillaActual());
  } 
}
