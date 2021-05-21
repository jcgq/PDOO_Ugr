# To change this license header, choose License Headers in Project Properties.
# To change this template file, choose Tools | Templates
# and open the template in the editor.

#@author JuanCarlos Gonzalez Quesada
#EXAMEN
module Civitas
  require_relative "sorpresa.rb"
  class SorpresaConvierteCalle < Sorpresa
    def initialize(_v, _t, tp1, tp2, tab)
       super(_t)
       @indice = _v
       @titulo1 = tp1
       @titulo2 = tp2
       @tablero = tab
    end
    
    def aplicar_a_jugador(iactual, todos)
      if(jugador_correcto(iactual, todos))
        informe(iactual, todos)
        calle_sorpresa = SorpresaCalle.new(@titulo1, @titulo2)
        calle_sorpresa.recibe_jugador(iactual, todos)
        Diario.instance.ocurre_evento("Se va a convertir la calle")
      end
    end
    
    
  end
end

