require_relative 'gestiones_inmobiliarias'
module Civitas
  attr_accessor :num_propiedad, :gestiones_inmobiliarias
  class Operaciones_inmobiliarias
    def initialize(_gestion, _num)
      @num_propiedad = _num
      @gestiones_inmobiliarias = _gestion
    end
  end
end