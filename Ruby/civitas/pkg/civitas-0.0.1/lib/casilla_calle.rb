#encoding: utf­8
require_relative 'jugador'
require_relative "casilla.rb"

module Civitas
  class CasillaCalle < Casilla
    attr_accessor :titulo
    def initialize(_titulo)
      super(_titulo.nombre)
      @titulo = _titulo
      @importe = _titulo.precio_compra
    end
  
def recibe_jugador(iactual,todos)
  puts "Estoy en recibe_Jugador"
      if(jugador_correcto(iactual, todos))
        puts "Soy jugador correcto"
        informe(iactual, todos)
        if(@titulo.tiene_propietario()==true)
          puts "Titulo con propietario"
          if(@titulo.propietario!=todos.at(iactual)and @titulo.hipotecado==false and @titulo.propietario.encarcelado==false)
            puts "Titulo con propietario y pagas alquiler"
            todos.at(iactual).set_puede_comprar(false)
            todos.at(iactual).paga_alquiler(@titulo.alquiler_base)
            @titulo.propietario.recibe(@titulo.alquiler_base)
          else
            todos.at(iactual).set_puede_comprar(false)
            puts "Titulo con propietario y no pagas alquiler"
          end
        else
          puts "Titulo sin propietario"
          todos.at(iactual).set_puede_comprar(true)
        end
      end
end
    
  def to_sc()
    return "La casilla es " + nombre.to_s + " y el importe es " + @importe.to_s()
  end
end

  end
