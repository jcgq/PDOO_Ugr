#encoding: utf­8
require_relative "sorpresa.rb"
class SorpresaEspeculador < Sorpresa
  def initialize(_valor)
    super("Soy un especulador")
    @valor = _valor
  end
  
  def aplicar_a_jugador(actual, todos)
    if(super.jugador_correcto(actual, todos))
      super.informe(actual, todos)
      especulador = Especulador.new(todos.at(actual), @valor)
      #todos.set()
    end
  end
  
  def to_ss()
    return @texto
  end
end
