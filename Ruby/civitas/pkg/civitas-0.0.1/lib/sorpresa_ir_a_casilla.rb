#encoding: utf­8
require_relative "sorpresa.rb"
module Civitas
class SorpresaIrACasilla < Sorpresa
  def initialize(_tablero, _valor, _texto)
    super(_texto)
    @tablero = _tablero
    @valor = _valor
  end
  
  def aplicar_a_jugador(actual,todos)
      if(jugador_correcto(actual, todos))
        informe(actual, todos)
        casilla = @tablero.get_casilla(todos.at(actual).num_casilla_actual)
        num_dado = Dado.new.tirar()
        tirada = @tablero.calcular_tirada(todos.at(actual).num_casilla_actual, num_dado)
        casilla_final = @tablero.nueva_posicion(todos.at(actual).num_casilla_actual, tirada)
        todos.at(actual).mover_a_casilla(casilla_final)
        casilla.recibe_jugador(actual, todos)
      end
    end
    
 def to_ss()
    return @texto
  end
end
end
