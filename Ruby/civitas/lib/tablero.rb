#encoding: utf­8

require_relative 'casilla'
require_relative 'casilla_juez'
module Civitas
  class Tablero
    attr_accessor :numCasillaCarcel, :porSalida, :tieneJuez, :casillas, :casilla
    
    def initialize(casillaCarcel)
        if(casillaCarcel < 1)
          @numCasillaCarcel = 1
        else
          @numCasillaCarcel=casillaCarcel          
        end
          @casillas=Array.new
          casilla = Casilla.new("Salida")
          @casillas << casilla
          @porSalida=0
          @tieneJuez=false
        

      end
      
      private
      def correcto1()
        if((@casillas.size() > @numCasillaCarcel) && @tieneJuez == true)
          return true
        else
          return false
        end
      end
      
      def correcto(numCasilla)
        if(correcto1() && numCasilla>=0 && numCasilla<20)
          return true
        else
          return false
        end
      end
      
      public
      def tamanio()
        return @casillas.size()
      end
      
      def get_casilla(numCasilla)
        if(correcto(numCasilla))
          return @casillas.at(numCasilla)
        else
          return nil
        end
      end
      
      def get_por_salida()
        if(@porSalida > 0)
          v1=@porSalida
          @porSalida=@porSalida-1
          return v1
        else
          return @porSalida
        end
      end
      
      def aniade_casilla(casilla)
        if(@casillas.size() == @numCasillaCarcel)
          casill = Casilla.new("Carcel")
          @casillas << casill
        end
        
        @casillas << casilla
      end
      
      def aniade_juez
        if(@tieneJuez == false)
          casilla = CasillaJuez.new(5, "Juez")
         @casillas << casilla
          @tieneJuez = true
        end
      end
      
      def nueva_posicion(actual, tirada)
        if(!correcto1())
          return -1
        else
          suma = actual+tirada
          sumaF=suma%20
          if(suma!=sumaF)
            @porSalida=@porSalida+1
            puts @porSalida
          end
        end
        return sumaF
      end
      
      def calcular_tirada(origen, destino)
        tirada = destino - origen
        if(tirada >=0)
          return tirada
        else
          tirada = tirada + 20
          return tirada
        end
      end
      
  end
end
