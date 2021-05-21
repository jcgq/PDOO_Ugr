# To change this license header, choose License Headers in Project Properties.
# To change this template file, choose Tools | Templates
# and open the template in the editor.
require_relative 'civitasjuego'
require_relative 'vista_textual'
require_relative 'operaciones_juego'
require_relative 'operaciones_inmobiliarias'
require_relative 'salidas_carcel'
require_relative 'respuestas'
 module Civitas
  class Controlador
    def initialize(_juego,_vista)
        @juego = _juego
        @vista = _vista
    end
    
    def juega()
      @vista.setCivitasJuego(@juego)
      while(@juego.final_del_juego()==false)
        @vista.actualizarVista()
        @vista.pausa()
        siguiente = @juego.siguiente_paso()
        @vista.mostrarSiguienteOperacion(siguiente)
        
        if(siguiente != Operaciones_juego::PASAR_TURNO)
          @vista.mostrarEventos()
          puts "No es pasar Turno"
        end
        fin = @juego.final_del_juego()
        if(fin==false)
          puts "fin==false"
          if(siguiente == Operaciones_juego::COMPRAR)
            puts "Siguiente operacion igual a false"
              respuesta = @vista.comprar()
              if(respuesta == Respuestas::SI)
                puts "respuesta es SI"
                @juego.comprar()
                @juego.siguiente_paso_completado(Operaciones_juego::COMPRAR)
              else
                puts "Respuesta es NO"
                @juego.siguiente_paso_completado(Operaciones_juego::COMPRAR)
              end
          end
            
          if(siguiente ==Operaciones_juego::GESTIONAR)
            puts "If de GESTIONAR"
            @vista.gestionar()
            
            operacioninmo = Operaciones_inmobiliarias.new(@vista.get_gestion(), @vista.get_propiedad())
            if(Gestiones_inmobiliarias::CANCELAR_HIPOTECA == @vista.get_gestion())
              @juego.cancelar_hipoteca(@vista.get_propiedad())
              
            end
            if(Gestiones_inmobiliarias::CONSTRUIR_CASA==@vista.get_gestion())
              @juego.construir_casa(@vista.get_propiedad())
              
            end
            if(Gestiones_inmobiliarias::CONSTRUIR_HOTEL==@vista.get_gestion())
              @juego.construir_hotel(@vista.get_propiedad())
              
            end
            if(Gestiones_inmobiliarias::HIPOTECAR==@vista.get_gestion())
              @juego.hipotecar(@vista.get_propiedad())
              
            end
            if(Gestiones_inmobiliarias::VENDER==@vista.get_gestion())
              @juego.vender(@vista.get_propiedad())
              
            end
            if(Gestiones_inmobiliarias::TERMINAR==@vista.get_gestion())
              @juego.siguiente_paso_completado(Operaciones_juego::GESTIONAR)
              
            end
          end
          if(siguiente == Operaciones_juego::SALIR_CARCEL)
            salida = @vista.salir_carcel()
            if(salida == Salidas_carcel::PAGANDO)
              @juego.salir_carcel_pagando()
            else
              @juego.salir_carcel_tirando()
            end
            @juego.siguiente_paso_completado(Operaciones_juego::SALIR_CARCEL)
          end
        end
        puts "SIGUIENTE VALE " + siguiente.to_s()
        puts "*******************************************************************************"
      end
      puts "Voy a ir a ranking"
      @juego.ranking()
    end
  end
end