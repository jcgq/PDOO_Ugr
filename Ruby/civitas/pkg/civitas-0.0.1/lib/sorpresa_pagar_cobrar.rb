#encoding: utf­8
require_relative "sorpresa.rb"
module Civitas
class SorpresaPagarCobrar < Sorpresa
  attr_accessor :valor
  def initialize(_valor, _texto)
    super(_texto)
    @valor = _valor
  end
  
  def aplicar_a_jugador(_actual, _todos)
    if(jugador_correcto(_actual, _todos))
      informe(_actual, _todos)
      _todos.at(_actual).modificar_saldo(@valor)
    end
  end
  
  def to_ss()
    return @texto
  end
end
end
