#encoding: utf­8
require_relative "sorpresa.rb"
module Civitas
class SorpresaPorJugador < Sorpresa
  attr_accessor :valor
  def initialize(_valor, _texto)
    super(_texto)
    @valor = _valor
  end
  
  def aplicar_a_jugador(_actual, _todos)
    if(jugador_correcto(_actual, _todos))
      informe(_actual, _todos)
      sorpresilla = SorpresaPagarCobrar.new(-@valor*(_todos.size()-1), "Pierdes dinero")
      recibe_ingreso = SorpresaPagarCobrar.new(@valor, "Ganas dinero")
      for i in 0..(_todos.size()-1)
        if(i == _actual)
          puts "PagarCobrar perder dinero"
          sorpresilla.aplicar_a_jugador(_actual, _todos)
        else
          puts "PagarCobrar ganar dinero"
          recibe_ingreso.aplicar_a_jugador(_actual, _todos)
        end
      end
    end
  end
  
  def to_ss()
    return @texto
  end
end
end
