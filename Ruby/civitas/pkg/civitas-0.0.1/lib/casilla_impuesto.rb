
#encoding: utf­8
module Civitas
  class CasillaImpuesto < Casilla
    
    def initialize(_cantidad, _nombre)
      super(_nombre)
      @importe = _cantidad
    end
  
  def recibe_jugador_impuesto(iactual,todos)
     if(jugador_correcto(iactual,todos))
       informe(iactual, todos)
       todos.at(iactual).paga_impuesto(@importe)
     end
  end
    
  def to_sc()
    return "El importe es " + @importe.to_s()
  end
end
  end