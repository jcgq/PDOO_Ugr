# To change this license header, choose License Headers in Project Properties.
# To change this template file, choose Tools | Templates
# and open the template in the editor.

#@author JuanCarlos Gonzalez Quesada
#EXAMEN
module Civitas
  require_relative "casilla_calle.rb"
  class SorpresaCalle < CasillaCalle
    def initialize(_titulo1, _titulo2)
        super(_titulo1)
        @titulo_robado = _titulo2
    end
    
    def get_titulo_propiedad_a_robar()
      return @titulo_robado
    end
    def recibe_jugador(iactual,todos)
      if(jugador_correcto(iactual, todos))
        informe(iactual, todos)
        if(!@titulo_robado.tiene_propietario())
          todos.at(iactual).puede_comprar_casilla();
          todos.at(iactual).set_puede_comprar(true);
          todos.at(iactual).robar_propiedad(@titulo_robado)
          Diario.instance.ocurre_evento("Se va a robar el titulo")
        else
          @titulo_robado.tramitar_alquiler(todos.at(iactual))
          todos.at(iactual).set_puede_comprar(false)
        end
      end  
end
    def to_ss()
    return @texto
  end
    
  end
end

