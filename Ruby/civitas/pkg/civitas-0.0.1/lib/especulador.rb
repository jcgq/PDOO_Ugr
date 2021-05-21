# To change this license header, choose License Headers in Project Properties.
# To change this template file, choose Tools | Templates
# and open the template in the editor.

class Especulador < Jugador
  @@factor_especulador = 2
  def initialize(_jugador, _fianza)
    super(_jugador)
    @fianza=_fianza
    @soy_espe=true
    for i in (super.propiedades.size()-1)
      _jugador.propiedades.at(i).actualizar_propietario_por_converision(self)
    end
  end
  
  def encarcelar(_casilla)
    resul = false
    if(super.encarcelado==false)
      if(super.tiene_salvoconducto==false)
        if(@fianza>=super.saldo)
          resul=true
        else
          super.paga(@fianza)
        end
      else
        resul=false
        super.perder_salvoconducto()
        Diario.Instance.ocurre_evento("Jugador pierde salvo")
      end
    end
    return resul
  end
  
  def soy_especulador()
    return @soy_espe
  end
  
  def get_casas_max()
    return (super.casas_max*@@factor_especulador)
  end
  
  def get_hoteles_max()
    return (super.hoteles_max*@@factor_especulador)
  end
  
  def pagar_impuesto(_cantidad)
    resul = false
    if(super.encarcelado==false)
      resul = super.paga(_cantidad/2)
      Diario.Instance.ocurre_evento("Pagas impuesto")
    end
    return resul
  end
  
  def to_sj()
      texto = "Jugador { Nombre: #{super.nombre}, saldo: #{super.saldo} "
      if(super.encarcelado)
        texto+=" y estoy encarcelado"
      else
        texto+=" y no estoy encarcelado"
      end
      texto+=" y SOY un especulador "
      
    
      return texto
    end
end
