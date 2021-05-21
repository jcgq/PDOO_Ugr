
#encoding: utf­8
require_relative "casilla.rb"
require_relative 'sorpresa_ir_a_carcel'
require_relative 'sorpresa_ir_a_casilla'
require_relative 'sorpresa_pagar_cobrar'
require_relative 'sorpresa_por_casa_hotel'
require_relative 'sorpresa_por_jugador'
require_relative 'sorpresa_salir_carcel'
require_relative 'sorpresa_calle'
require_relative 'sorpresa_convierte_calle'
module Civitas
  class CasillaSorpresa < Casilla
    
    def initialize(_mazo, _nombre)
      super(_nombre)
      @mazo = _mazo
      @sorpresa = Sorpresa.new("")
    end
  
  def recibe_jugador(iactual,todos)
    puts "En recibe jugador de sorpresa"
      if(jugador_correcto(iactual, todos))
        @sorpresa = @mazo.siguiente()
        informe(iactual, todos)
        puts @sorpresa.to_ss()
        @sorpresa.aplicar_a_jugador(iactual, todos)
      end
  end
    
  def to_sc()
    return "Sorpresa " + @nombre
  end
end
  end