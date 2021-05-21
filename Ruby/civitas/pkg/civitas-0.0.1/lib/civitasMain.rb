#encoding: utf­8

require_relative 'casilla'
require_relative 'dado'
require_relative 'diario'
require_relative 'tablero'
require_relative 'sorpresa'
require_relative 'tipo_sorpresa'
require_relative 'mazo_sorpresa'

module Civitas
  class CivitasMain
    @dado = Dado.new
    @mazillo = MazoSorpresa.new
    @tablerillo = Tablero.new(5)
      
      def self.main
#        for i in 0..10
#          puts @dado.quienEmpieza(4)
#        end
#        for i in 0..1  
#          puts "Tiro dado " + @dado.tirar().to_s
#          @dado.set_debug(true)
#          puts "Tiro dado " + @dado.tirar().to_s
#          @dado.set_debug(false)
#        end
#      puts "El ultimo resultado es " + @dado.ultimoResultado().to_s()
#      puts @dado.salgoDeLaCarcel()
#      
#        puts TipoSorpresa::IRCARCEL
#        @sor1 = Sorpresa.new("Carapen")
#        @mazillo.alMazo(@sor1)
#        @sor2 = Sorpresa.new("Caracangrejo")
#        @mazillo.alMazo(@sor2)
#        puts @mazillo.size().to_s()
#        puts @mazillo.sorpresas.at(0).to_s()
#        @mazillo.inhabilitarCartaEspecial(@sor1)
#        puts @mazillo.size().to_s()
#        puts @mazillo.sorpresas.at(0).to_s()
#        @mazillo.habilitarCartaEspecial(@sor1)
#        puts @mazillo.size().to_s()
#        puts @mazillo.sorpresas.at(1).to_s()

        casillita = Casilla.new("uni")
        @tablerillo.aniadeCasilla(casillita)
        puts @tablerillo.tamanio().to_s()
        valor = 0
        @tablerillo.aniadeJuez()
        for i in 0..19
          casillita = Casilla.new("uni")
          @tablerillo.aniadeCasilla(casillita)
          puts @tablerillo.casillas.at(i).nombre
          valor = valor +1
        end
       puts "Valor vale " + valor.to_s()
        puts "Tiradilla " + @tablerillo.calcularTirada(5, 2).to_s()
        puts @tablerillo.getCasilla(5).to_sC()
        puts @tablerillo.nuevaPosicion(20, 25)
        
      end    
      CivitasMain.main
   
  end
end

