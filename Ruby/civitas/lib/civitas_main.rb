#encoding: utf­8

require_relative 'casilla'
require_relative 'dado'
require_relative 'diario'
require_relative 'tablero'
require_relative 'sorpresa'
require_relative 'mazo_sorpresa'
require_relative 'civitasjuego'
require_relative 'jugador'
require_relative 'controlador'
require_relative 'vista_textual'

module Civitas
  class CivitasMain
       def self.main()
         @vista=Vista_textual.new()
         jugador1 = Jugador.new("JuanCarlos")
         jugador2 = Jugador.new("Pedro")
         jugador3 = Jugador.new("Luis")
         jugadoresnombre = Array.new()
         
         jugadoresnombre << jugador1.nombre
         jugadoresnombre << jugador2.nombre
         #jugadoresnombre << jugador3.nombre
         
         @civitas = Civitasjuego.new(jugadoresnombre)
         @controlador  = Controlador.new(@civitas, @vista)
         @controlador.juega()
       end
       CivitasMain.main
  end
end

