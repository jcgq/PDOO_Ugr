#encoding: utf­8
require_relative "casilla.rb"
module Civitas
  
  class CasillaJuez < Casilla
    
    def initialize(carcel,_nombre)
       super(_nombre)
      @@carcel = carcel
     
    end
    
    def recibe_jugador(iactual,todos)
      if(jugador_correcto(iactual, todos))
        informe(iactual, todos)
        todos.at(iactual).encarcelar(@@carcel)
      end
    end
    
    def to_s
      return "Te han pillado involucrado en los ERES vas a la carcel"
    end
  end
end
