#encoding: utf­8
require_relative "sorpresa.rb"
module Civitas
class SorpresaIrACarcel < Sorpresa
  def initialize(_tablero)
    super("Ir a carcel")
    @tablero = _tablero
  end
  
  def aplicar_a_jugador(_actual, _todos)
    if(jugador_correcto(_actual, _todos))
      informe(_actual, _todos)
      _todos.at(_actual).encarcelar(@tablero.numCasillaCarcel)
    end
  end
  
  
  def to_ss()
    return @texto
  end
end
end