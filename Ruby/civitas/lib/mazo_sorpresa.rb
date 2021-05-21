#encoding: utf­8
require_relative 'diario'
require_relative 'sorpresa'
module Civitas
  class MazoSorpresa
    attr_accessor :sorpresas, :ultima_sorpresa
    
    def initialize(_debug)
      @sorpresas = Array.new()
      @barajada = false
      @usadas= 0
      @debug=_debug
      @cartas_especiales=Array.new()
      @ultima_sorpresa
      if(_debug==true)
        Diario.instance.ocurre_evento("Debug activado")
      end 
    end
    
    def self.new_mazo_sorpresa()
      new(false)
    end
    
    
    def al_mazo(sor)
      if(@barajada==false)
        @sorpresas << sor
      end
    end
    
    
    def siguiente()
      if(@barajada == false || @usadas == @sorpresas.size())
        @barajada = true
        @usadas=0
        @sorpresas.shuffle!
      end
      @usadas=@usadas+1
      @ultima_sorpresa=@sorpresas.at(0)
      @sorpresas.delete_at(0)
      @sorpresas << @ultima_sorpresa
      return @ultima_sorpresa
    end
    
    def inhabilitar_carta_especial(_sorpresa)
      for i in 0..(@sorpresas.size()-1)
        if(@sorpresas[i] == _sorpresa)
          @cartas_especiales << _sorpresa
          @sorpresas.delete_at(i)
          Diario.instance.ocurre_evento("Carta Sorpresa INHABILITADA")
        end
      end
    end
    
    def habilitar_carta_especial(_sorpresa)
      for i in 0..(@cartas_especiales.size()-1)
        if(@cartas_especiales[i] == _sorpresa)
          @sorpresas << _sorpresa
          @cartas_especiales.delete_at(i)
          Diario.instance.ocurre_evento("Carta Sorpresa HABILITADA")
        end
      end
    end
    
    def size()
      return @sorpresas.size()
    end
    
    def get_ultima_sorpresa()
      return @ultima_sorpresa
    end
    
    
  end
end

