#encoding:utf-8
require_relative 'operaciones_juego'
require 'io/console'
require_relative 'civitasjuego'
require_relative 'tablero'
require_relative 'salidas_carcel'
require_relative 'respuestas'
require_relative 'gestiones_inmobiliarias'


module Civitas

  class Vista_textual

    attr_accessor :juegoModel
    def mostrar_estado(estado)
      puts estado
    end

    
    def pausa
      print "Pulsa una tecla"
      STDIN.getch
      print "\n"
    end

    def lee_entero(max,msg1,msg2)
      ok = false
      begin
        print msg1
        cadena = gets.chomp
        begin
          if (cadena =~ /\A\d+\Z/)
            numero = cadena.to_i
            ok = true
          else
            raise IOError
          end
        rescue IOError
          puts msg2
        end
        if (ok)
          if (numero >= max)
            ok = false
          end
        end
      end while (!ok)

      return numero
    end



    def menu(titulo,lista)
      tab = "  "
      puts titulo
      index = 0
      lista.each { |l|
        puts tab+index.to_s+"-"+l
        index += 1
      }

      opcion = lee_entero(lista.length,
                          "\n"+tab+"Elige una opción: ",
                          tab+"Valor erróneo")
      return opcion
    end

    
    def comprar()
      opciones = Array.new()
      respuesta = [Respuestas::SI,Respuestas::NO]
      opciones << "SI"
      opciones << "NO"
      opcion = menu("Elige si comprar o no la propiedad", opciones)
      return respuesta[opcion]
    end

    def gestionar()
      gestiones=[Gestiones_inmobiliarias::VENDER,Gestiones_inmobiliarias::HIPOTECAR,Gestiones_inmobiliarias::CANCELAR_HIPOTECA, 
       Gestiones_inmobiliarias::CONSTRUIR_CASA,Gestiones_inmobiliarias::CONSTRUIR_HOTEL,Gestiones_inmobiliarias::TERMINAR]
      opciones = Array.new()
      opciones << "VENDER"
      opciones << "HIPOTECAR"
      opciones << "CANCELAR_HIPOTECA"
      opciones << "CONSTRUIR_CASA"
      opciones << "CONSTRUIR_HOTEL"
      opciones << "TERMINAR"
       opcion = menu("Elige la acccion que desea realizar", opciones)
      propiedades = Array.new()
      if(5==opcion)
        @gestion = gestiones[opcion]
      else
        for i in 0..(@juegoModel.jugadores.at(@juegoModel.indice_jugador_actual).propiedades.size()-1)
          propiedades << @juegoModel.jugadores.at(@juegoModel.indice_jugador_actual).propiedades.at(i).nombre
        end
        opcion2 = menu("Elige la propiedad sobre la que realizar la operacion", propiedades)
        @gestion = gestiones[opcion]
        @propiedad=opcion2
      end
      
      
    end

    def get_gestion()
      return @gestion
    end

    def get_propiedad
     return @propiedad
    end

    def mostrarSiguienteOperacion(operacion)
      puts "La siguiente operacion que se va a realizar es " + operacion.to_s()
    end

    def mostrarEventos
      while(Diario.instance.eventos_pendientes())
        puts Diario.instance.leer_evento()
      end
    end

    def setCivitasJuego(civitas)
         @juegoModel=civitas
    end
    
    def salir_carcel()
      opciones = Array.new()
      salida = [Salidas_carcel::PAGANDO,Salidas_carcel::TIRANDO]
      opciones << "Pagando"
      opciones << "Tirando el dado"
      opcion = menu("Elige forma de salir de la carcel", opciones)
      return salida[opcion]
    end
    
    

    def actualizarVista()
      jugadoractual = @juegoModel.jugadores.at(@juegoModel.indice_jugador_actual)
      puts jugadoractual.to_sj()
      puts @juegoModel.get_casilla_actual().to_sc()
      
    end

    
  end

end
