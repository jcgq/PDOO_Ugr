#encoding: utf­8

require_relative "sorpresa.rb"
module Civitas
class SorpresaSalirCarcel < Sorpresa
  def initialize(_mazo)
    super("Quedas librfe de la carcel")
    @mazo = _mazo
  end
  
  def aplicar_a_jugador(actual, todos)
    if(jugador_correcto(actual, todos))
      nadie_la_tiene=true
      for i in 0..(todos.size()-1)
        if(todos.at(i).tiene_salvoconducto)
          nadie_la_tiene=false
        end
      end
      if(nadie_la_tiene)
        salir_carcel = SorpresaSalirCarcel.new(@mazo)
        todos.at(actual).obtener_salvoconducto(salir_carcel)
        salir_del_mazo()
      end
    end
  end
  
  def salir_del_mazo()
    @mazo.inhabilitar_carta_especial(self)
  end
  
  def usada()
    @mazo.habilitar_carta_especial(self)
  end
  
  def to_ss()
    return @texto
  end
end
end
