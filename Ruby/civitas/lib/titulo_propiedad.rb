#encoding: utf­8

require_relative 'jugador'

module Civitas
  class TituloPropiedad
    @@factor_intereses_hipoteca=1,1
    
    attr_accessor :hipotecado, :nombre, :num_casas, :num_hoteles, :precio_compra, :precio_edificar, :hipoteca_base, :alquiler_base , :propietario
    
    def initialize(_nombre, _ab, _fr, _hb, _pc, _pe)
      @alquiler_base=_ab
      @factor_revalorizacion=_fr
      @hipoteca_base=_hb
      @hipotecado=false
      @nombre=_nombre
      @num_casas=0
      @num_hoteles=0
      @precio_compra=_pc
      @precio_edificar=_pe
      @propietario = nil
    end
    
    def actualizar_propietario_por_converision(_jugador)
      @propietario=_jugador
    end
    
    def cancelar_hipoteca(_jugador)
      if(self.es_este_el_propietario(_jugador)&& @hipotecado == true)
        _jugador.paga(self.get_importe_cancelar_hipoteca())
        @hipotecado =false
        return true
      else
        return false
      end
      
    end
    
    def cantidad_casahoteles()
      return @num_casas+@num_hoteles
    end
    
    def comprarT(_jugador)
      if(self.tiene_propietario()==true)
        puts "TIENE PROPIETARIO"
        return false
      else
        if(_jugador.paga(@precio_compra))
            actualizar_propietario_por_converision(_jugador)
            return true
        end
      end
    end
    
    def construir_casa(_jugador)
      if(_jugador == @propietario)
        _jugador.paga(@precio_edificar)
        @num_casas = @num_casas + 1
        return true
      else
        return false
      end
    end
    
    def construir_hotel(_jugador)
      if(_jugador == @propietario)
        _jugador.paga(@precio_edificar)
        @num_hoteles = @num_hoteles + 1
        return true
      else
        return false
      end
    end
    
    def derruir_casa(_n, _jugador)
      if(_jugador == @propietario and @num_casas >= _n.to_f)
        @num_casas = 0
        return true
      else
        return false
      end
    end
    
    def es_este_el_propietario(_jugador)
      return @propietario== _jugador
    end
    
    def get_importe_cancelar_hipoteca()
      cantidad = @hipoteca_base*(@num_casas + @num_hoteles)
      return cantidad
    end
    
    def get_precio_venta()
      return @precio_compra + ((@precio_edificar * @num_casas + @precio_edificar * @num_hoteles)*@factor_revalorizacion)
    end
    
    
    
    def hipotecarT(_jugador)
      puts "HIPOTECART"
      puts @propietario.inspect
      if(@hipotecado == false && _jugador == @propietario)
        puts "IF HIPOTECART"
        _jugador.recibe(@hipoteca_base)
        @hipotecado = true
        return true
      else
        return false
      end
    end
    
    def propietario_encarcelado()
      return @propietario.encarcelado
    end
    
    def tiene_propietario()
      if(@propietario == nil)
        return false
      else
        return true
      end
    end
    
    def get_num_casas()
      return @num_casas
    end
    
    def get_num_hoteles()
      return @num_hoteles
    end
    
     @Override
     def to_st()
       final = "Titulo: Nombre #{@nombre} Compra #{@precio_compra.to_s()} alquilerBase #{@alquier_base.to_s()} FactorRevalorizacion #{@factor_revalorizacion.to_s()} hipotecaBase #{@hipoteca_base.to_s()} precioCompra #{@precio_compra.to_s()} precioEdificar #{@precio_edificar.to_s()} numero de Casas #{@num_casas.to_s()} numero de Hoteles #{@num_hoteles.to_s() }"
       
       if(@hipotecado == false)
         final += "Propiedad no hipotecada"
       else
         final += "Propiedad hipotecada"
       end
     end
    
     
     def tramitar_alquiler(_jugador)
       if(self.tiene_propietario() && _jugador!=@propietario)
         _jugador.paga_alquiler(@alquier_base)
         @propietario.recibe(@alquiler_base)
       end
     end
     
     def venderT(_jugador)
       if(@propietario==_jugador)
         puts "IF DE VENDERT"
         _jugador.modificar_saldo(get_precio_venta())
         @propietario=nil
         return true
       else
         return false
       end
     end
      
  end

end
