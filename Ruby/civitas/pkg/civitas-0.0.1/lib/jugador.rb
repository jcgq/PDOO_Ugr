#encoding: utf­8

require_relative 'dado'
require_relative 'diario'
require_relative 'titulo_propiedad'
require_relative 'sorpresa'
module Civitas
  class Jugador
    
    attr_accessor :casas_max, :casas_por_hotel, :salvo_conducto, :hoteles_max, :encarcelado, :paso_por_salida, :precio_libertad, :nombre, :puede_comprar, :saldo, :num_casilla_actual, :propiedades
    
    @@casas_max=4
    @@casas_por_hotel=4
    @@hoteles_max=4
    @@paso_por_salida=1000
    @@precio_libertad=20000
    
    @@saldo_inicial=7500
    
    def initialize(_nombre)
      @encarcelado=false
      @nombre=_nombre
      @num_casilla_actual=0
      @puede_comprar=false
      @saldo=7500.0
      @propiedades= Array.new()
      @salvo_conducto=nil
    end
    
    def self.new_jugador_copia(_jugador)
      jugador = new(_jugador.nombre)
      jugador.saldo=_jugador.saldo
      jugador.encarcelado=_jugador.encarcelado
      jugador.num_casilla_actual=_jugador.num_casilla_actual
      jugador.puede_comprar= _jugador.puede_comprar
      jugador.propiedades= _jugador.propiedades
      jugador.salvo_conducto=_jugador.salvo_conducto
      return jugador
    end
    

    def cancelar_hipoteca(_ip)
      result = false
      if(@encarcelado==true)
        return result
      end
      if(self.existe_la_propiedad(_ip))
        propiedad = @propiedades.at(_ip)
        cantidad = propiedad.get_importe_cancelar_hipoteca()
        _puedo_gastar = self.puedo_gastar(cantidad)
        if(_puedo_gastar)
          result = propiedad.cancelar_hipoteca(self)
          if(result)
            Diario.instance.ocurre_evento("El jugador " + self.nombre + " cancela la hipoteca de la propiedad " + _ip.to_s)
          end
        end
      end
      return result
    end
    
    def cantidad_casas_hoteles()
      nc=0
      nh=0
      for i in 0..(@propiedades.size()-1)
        nc+=@propiedades.at(i).get_num_casas()
        nh+=@propiedades.at(i).get_num_hoteles()
      end
      total=nc+nh
      return total
    end
    
    def comprar(_titulo)
      result = false
      if(@encarcelado==true)
        return result
      end
      if(@puede_comprar==true)
        precio = _titulo.precio_compra
        if(puedo_gastar(precio))
          result = _titulo.comprarT(self)
        end
        if(result)
          @propiedades << _titulo
          Diario.instance.ocurre_evento("El jugador " + self.nombre + " compra la propiedad " + _titulo.to_st());
        end
        @puede_comprar=false
      end
      return result
    end
    
    def construir_casa(_ip)
      result = false
        if(@encarcelado)
            return result
        end
        if(self.existe_la_propiedad(_ip))
            propiedad = @propiedades.at(_ip)
            puedo_edificar_casa = self.puedo_edificar_casa(propiedad)
            precio = propiedad.precio_edificar
            
            if(self.puedo_gastar(precio) && propiedad.num_casas<@@casas_max)
                puedo_edificar_casa = true
            end
            if(puedo_edificar_casa)
                result = propiedad.construir_casa(self)
                if(result)
                    Diario.instance.ocurre_evento("El jugador " + self.nombre + " construye casa en la propiedad " + _ip.to_s());
                end
                end
        end
        return result;
    end
    
    def construir_hotel(_ip)
      result = false
        if(@encarcelado)
            return result
        end
        if(self.existe_la_propiedad(_ip))
            propiedad = @propiedades.at(_ip)
            puedo_edificar_hotel = self.puedo_edificar_hotel(propiedad)
            precio = propiedad.precio_edificar
            
            if(self.puedo_gastar(precio) && propiedad.num_hoteles<@@hoteles_max && propiedad.num_casas>=@@casas_por_hotel)
                puedo_edificar_hotel =true
            end
            if(puedo_edificar_hotel)
                result = propiedad.construir_hotel(self)
                _casas_por_hotel = self.casas_por_hotel
                propiedad.derruir_casa(_casas_por_hotel, self)
                if(result)
                    Diario.instance.ocurre_evento("El jugador " + self.nombre + " construye hotel en la propiedad " + _ip.to_s);
                end
                end
        end
        return result;
    end
    
    def debe_ser_encarcelado()
      if(self.encarcelado)
        return false
      else
        if(!self.tiene_salvoconducto())
          return true
        else
          self.perder_salvoconducto()
          Diario.instance.ocurre_evento("Jugador salvado de la carcel")
          return false
        end
      end
    end
    
    def es_bancarrota()
      if(@saldo < 0)
        return true
      else
        return false
      end
    end
    
    def encarcelar(_num_casilla_carcel)
      if(self.debe_ser_encarcelado())
        self.mover_a_casilla(_num_casilla_carcel)
        self.encarcelado=true
        Diario.instance.ocurre_evento("Jugador encarcelado")
      end
      return @encarcelado
    end
    
    def existe_la_propiedad(_ip)
      solucion=false
      for i in 0..(@propiedades.size()-1)
        if(i==_ip)
          solucion = true
        end
      end
      return solucion
    end
    
    def hipotecar(_ip)
      result = false
        if(self.encarcelado)
            return result
        end
        if(self.existe_la_propiedad(_ip))
            propiedad = @propiedades.at(_ip)
            result = propiedad.hipotecarT(self)
        end
        if(result)
            Diario.instance.ocurre_evento("El jugador " + self.nombre + " hipoteca la propiedad " + _ip.to_s())
        end
        return result
    end
    
    
    def modificar_saldo(_cantidad)
      puts "En modifica Saldo " + @saldo.inspect
      @saldo =  @saldo + _cantidad.to_f()
      Diario.instance.ocurre_evento("Se modifica el saldo")
      return true
    end
    
    def mover_a_casilla(_num_casilla)
      if(@encarcelado==true)
        return false
      else
        @num_casilla_actual=_num_casilla
        @puede_comprar=false
        Diario.instance.ocurre_evento("Se ha movido a la casilla" + @num_casilla_actual.to_s())
        return true
      end
    end
    
    def obtener_salvoconducto(_sorpresa)
      if(self.encarcelado)
        return false
      else
        @salvoconducto=_sorpresa
        return true
      end
    end
    
    def paga(_cantidad)
      return modificar_saldo(-_cantidad)
    end
    
    def paga_alquiler(_cantidad)
      if(@encarcelado)
        return false
      else
        return self.paga(_cantidad)
      end
    end
    
    def paga_impuesto(_cantidad)
      if(@encarcelado)
        return false
      else
        return self.paga(_cantidad)
      end
    end
    
    def pasa_por_salida()
      self.modificar_saldo(@@paso_por_salida)
      Diario.instance.ocurre_evento("Pasaste por la casilla de Salida")
      return true
    end
    
    def perder_salvoconducto()
      @salvoconducto.usada()
      @salvoconducto=nil
    end
    
    def puede_comprar_casilla()
      if(@encarcelado)
        @puede_comprar=false
      else
        @puede_comprar=true
      end
      return @puede_comprar
    end
    
    def puede_salir_carcel_pagando()
      if(@saldo>=@@precio_libertad)
        return true
      else
        return true
      end
    end
    
    def puedo_edificar_casa(_propiedad)
      if(@saldo>=_propiedad.precio_edificar && @nombre.eql?(_propiedad.propietario.nombre))
        if(_propiedad.num_casas<4)
          return true
        else
          return false
        end
      else
        return false
      end
    end
    
    def puedo_edificar_hotel(_propiedad)
      if(@saldo>=_propiedad.precio_edificar && @nombre.eql?(_propiedad.propietario.nombre))
        if(_propiedad.num_casas==4 && _propiedad.num_hoteles<4)
          return true
        else
          return false
        end
      else
        return false
      end
    end
    
    def puedo_gastar(_precio)
      if(@encarcelado)
        return false
      else
        if(@saldo>=_precio)
          return true
        else
          return false
        end
      end
    end
    
    def recibe(_cantidad)
      if(@encarcelado==true)
        return false
      else
        self.modificar_saldo(_cantidad)
        return true;
      end
    end
    
    def salir_carcel_pagando()
      if(@encarcelado && @saldo>=@@precio_libertad)
        self.paga(@@precio_libertad)
        @encarcelado=false
        Diario.instance.ocurre_evento("Sale de la carcel pagando")
        return true
      else
        return false
      end
    end
    
    def salir_carcel_tirando()
      puedo_salir=Dado.new.salgo_de_la_carcel()
      if(puedo_salir==true)
        @encarcelado=false
        Diario.instance.ocurre_evento("Sale de la carcel tirando")
      end
      return puedo_salir
      
    end
    
    def tiene_algo_que_gestionar()
      if(@propiedades.length() == 0)
          return false
      else
        return true
      end
    end
    
    def tiene_salvoconducto()
      if(@salvoconducto==nil)
        return false
      else
        return true
      end
    end
    
    def to_sj()
      texto = "Jugador { Nombre: #{@nombre}, saldo: #{@saldo} "
      if(self.encarcelado)
        texto+=" y estoy encarcelado"
      else
        texto+=" y no estoy encarcelado"
      end
      texto+=" Mis propiedades son: "
      if(@propiedades1!=nil)
        texto+=" y no tengo propiedades"
      else
        for i in @propiedades
        texto+=i.to_st()
        end
      end
      return texto
    end
    
    def vender(_ip)
      if(@encarcelado==true)
        return false
      else
        if(self.existe_la_propiedad(_ip))
          if(@propiedades.at(_ip).hipotecado==false)
            resul = @propiedades.at(_ip).venderT(self)
            @propiedades.delete_at(_ip)
            Diario.instance.ocurre_evento("Se vendio una propiedad")
            return resul
          else
            return false          
          end
        end
        return false
      end
    end
    
    def set_puede_comprar(_puedecomprar)
      @puede_comprar = _puedecomprar
    end
    
    def compare_to(otro)
      return @saldo <=> otro.saldo
    end
    
    
  end
end

