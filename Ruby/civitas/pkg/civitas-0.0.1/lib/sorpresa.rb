#encoding: utf­8

require_relative 'mazo_sorpresa'
require_relative 'tablero'
module Civitas
  class Sorpresa
    
    attr_accessor :texto
    
    def initialize(_texto)#Para enviar a la carcel
      @texto = _texto
    end
    
    def informe(actual,todos)
      Diario.instance.ocurre_evento("Se aplica sorpresa a " + todos.at(actual).nombre)
    end
    
    def jugador_correcto(actual,todos)
      if(actual >= 0 && actual < todos.size())
        return true
      else
        return false
      end
    end
    
    def salir_del_mazo()
      if(@tipo == Civitas::TipoSorpresa::SALIRCARCEL)
        @mazo.inhabilitar_carta_especial(@mazo.ultima_sorpresa)
      end
    end  
    

    def to_ss()
      frase = "Sorpresa texto:  #{@texto}"
      return frase
    end
  
  end
 
end

