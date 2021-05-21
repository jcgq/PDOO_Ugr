#encoding: utf­8

require_relative 'titulo_propiedad'
require_relative 'mazo_sorpresa'
require_relative 'sorpresa'
require_relative 'operaciones_juego'

module Civitas
  class Casilla
    attr_accessor :nombre
    
    def initialize(_nombre)
      @nombre=_nombre
    end
        
   def informe(iactual, todos)#METODO DE INSTANCIA
     Diario.instance.ocurre_evento("El jugador" + todos.at(iactual).to_sj() + " ha caido en la casilla" + self.to_sc())
   end
   
   def jugador_correcto(iactual,todos)
      if(iactual >=0 && todos.size() > iactual)
        return true
      else
        return false
      end
    end

    def recibe_jugador(iactual,todos)
      self.informe(iactual, todos)
    end
   
    def to_sc()
      return "El nombre de la casilla es " + @nombre.to_s()
    end
    

  end
end


