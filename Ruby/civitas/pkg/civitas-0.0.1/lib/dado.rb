#encoding: utf­8
require_relative 'diario'

module Civitas
    class Dado
      attr_accessor :ultimo_resultado
      
    @@SalidaCarcel=5
    def initialize()
      @random=Random.new()
      @ultimo_resultado=0
      @debug=false
    end


    def tirar()
      if(@debug == true)
        @ultimo_resultado=1
        return @ultimo_resultado
      else
        numero=@random.rand(6)+1
        @ultimo_resultado=numero
        return @ultimo_resultado
      end
    end

    def salgo_de_la_carcel()
        valor = @random.rand(6)+1
        puts "El valor de la carcel es " + valor.to_s()
        if(valor >=5)
          return true
        else
          return false
        end
    end
    
    def quien_empieza(n)
      valor = @random.rand(n)
      return valor
    end
    
    def set_debug(_debug)
      @debug=_debug
      if(@debug==true)
        Diario.instance.ocurre_evento("Sale True")
      else
        Diario.instance.ocurre_evento("Sale False")    
      end
    end

  end
end
