#encoding: utf­8
require_relative 'gestor_estados'
require_relative 'jugador'
require_relative 'dado'
require_relative 'estados_juego'
require_relative 'tablero'
require_relative 'mazo_sorpresa'
require_relative 'casilla'
require_relative 'operaciones_juego'
require_relative 'casilla_calle'
require_relative 'casilla_sorpresa'
require_relative 'casilla_impuesto'
require_relative 'casilla_juez'
require_relative 'sorpresa_ir_a_carcel'
require_relative 'sorpresa_ir_a_casilla'
require_relative 'sorpresa_pagar_cobrar'
require_relative 'sorpresa_por_casa_hotel'
require_relative 'sorpresa_por_jugador'
require_relative 'sorpresa_salir_carcel'
require_relative 'sorpresa_calle'

module Civitas
  class Civitasjuego
    
    
    @titulo1 = TituloPropiedad.new("ETSIIT", 100, 1, 100, 500, 20)
    
    def initialize(_nombres)
      @gestor_estados = Gestor_estados.new()
      @gestor_estados.estado_inicial()
      @estado = Civitas::Estados_juego::INICIO_TURNO
      @jugadores=Array.new()
      for i in 0..(_nombres.size()-1)
        @jugadores << Jugador.new(_nombres.at(i))
      end
      @indice_jugador_actual= Dado.new.quien_empieza(@jugadores.size()-1)
      @tablero = Tablero.new(5)
      @mazo = MazoSorpresa.new(false)
      self.inicializar_tablero(@mazo)
      self.inicializar_mazosorpresas(@tablero)
    end
    
    attr_accessor :indice_jugador_actual, :estado, :gestor_estados, :tablero, :jugadores, :mazo
    
    
    
    def actualizar_info()
      @jugadores.at(self.indice_jugador_actual).to_sj()
      puts "La casilla actual es " + @jugadores.at(self.indice_jugador_actual).num_casilla_actual.to_s()
      if(@jugadores.at(self.indice_jugador_actual).es_bancarrota())
        _jugadores = Array.new()
        _jugadores = self.ranking()
        puts "El ranking es"
        for i in 0.._jugadores.size()
          puts _jugadores.at(i).to_sj()
        end
      end
    end
    
    def avanza_jugador()
      jugador_actual = @jugadores.at(@indice_jugador_actual)
      posicion_actual = jugador_actual.num_casilla_actual
      tirada = Dado.new.tirar()
      posicion_nueva = @tablero.nueva_posicion(posicion_actual, tirada)
      casilla =  @tablero.get_casilla(posicion_nueva)
      self.contabilizar_pasos_por_salida(jugador_actual)
      jugador_actual.mover_a_casilla(posicion_nueva)
      puts "Has caido en la casilla " + casilla.to_sc()
      casilla.recibe_jugador(@indice_jugador_actual, @jugadores)
      puts "Despues de recibe jugador"
      self.contabilizar_pasos_por_salida(jugador_actual)
    end
    
    def cancelar_hipoteca(_ip)
       
      return @jugadores.at(@indice_jugador_actual).cancelar_hipoteca(_ip)
    end
    
    def comprar()
      jugador_actual = @jugadores.at(@indice_jugador_actual)
      num_casilla_actual = jugador_actual.num_casilla_actual
      casilla = @tablero.get_casilla(num_casilla_actual)
      titulo=casilla.titulo
      res = jugador_actual.comprar(titulo)
      return res
    end
    
    def construir_casa(_ip)
      jugador_a = @jugadores.at(@indice_jugador_actual)
      return jugador_a.construir_casa(_ip)
    end
    
    def construir_hotel(_ip)
      jugador_a = @jugadores.at(@indice_jugador_actual)
      return jugador_a.construir_hotel(_ip)
    end
    
    def contabilizar_pasos_por_salida(_jugador)
      if(@tablero.porSalida()>0)
        _jugador.pasa_por_salida()
      end
    end
    
    def final_del_juego()
      fin = false
      for i in @jugadores
        if(i.es_bancarrota())
          fin = true
        end
      end
      return fin
    end
    
    def hipotecar(_ip)
      jugador_a = @jugadores.at(@indice_jugador_actual)
      return jugador_a.hipotecar(_ip)
    end
    
    def info_jugador_texto()
      jugador_a = @jugadores.at(@indice_jugador_actual)
      return jugador_a.to_sj()
    end
   
    def inicializar_mazosorpresas(_tablero)
      sorpresa1 = SorpresaPorCasaHotel.new(-120, "Te quito por cada casa y hotel 120 euros")
      sorpresa2 = SorpresaPorJugador.new(500, "Paga a los jugadores 500 euros")
      sorpresa3 = SorpresaPagarCobrar.new(1000, "Beneficio de 1000 eurazos")
      sorpresa4 = SorpresaIrACarcel.new(_tablero)
      sorpresa5 = SorpresaSalirCarcel.new(@mazo)
      sorpresa6 = SorpresaIrACasilla.new(_tablero, -150, "Vas a una casilla")
      sorpresa8 = SorpresaCalle.new(_tablero.get_casilla(1).titulo, @titulo1)
      @mazo.al_mazo(sorpresa8)
      @mazo.al_mazo(sorpresa8)
      @mazo.al_mazo(sorpresa8)
      @mazo.al_mazo(sorpresa8)
      @mazo.al_mazo(sorpresa8)
      @mazo.al_mazo(sorpresa8)
    end
    
    def inicializar_tablero(_mazo)
      titulo1 = TituloPropiedad.new("Granada", 100, 1, 100, 500, 20)
      titulo2 = TituloPropiedad.new("Tarragona", 120, 1, 150, 520, 25)
      titulo3 = TituloPropiedad.new("Melilla", 160, 2, 200, 540, 40)
      titulo4 = TituloPropiedad.new("Cadiz", 180, 2, 250, 560, 45)
      titulo5 = TituloPropiedad.new("Bilbao", 220, 2, 300, 580, 60)
      titulo6 = TituloPropiedad.new("Soria", 260, 3, 300, 600, 65)
      titulo7 = TituloPropiedad.new("Vigo", 280, 3, 400, 620, 70)
      titulo8 = TituloPropiedad.new("Baleares", 300, 3, 450, 640, 75)
      titulo9 = TituloPropiedad.new("Las Palmas", 350, 4, 500, 660, 85)
      titulo10 = TituloPropiedad.new("Asturias", 400, 4, 600, 680, 100)
      titulo11 = TituloPropiedad.new("Valencia", 450, 5, 700, 750, 200)
      titulo12 = TituloPropiedad.new("Guadalajara", 500, 5, 750, 800, 300)
      
      
     casilla1= CasillaCalle.new(titulo1)
     casilla2= CasillaSorpresa.new(_mazo, "Sorpresa 1: Nunca sabes lo que te va a tocar")
     casilla3= CasillaCalle.new(titulo2)
     casilla4= Casilla.new("Area de Descanso")
     casilla5= CasillaCalle.new(titulo3)
     casilla7= CasillaCalle.new(titulo4)
     casilla9= CasillaCalle.new(titulo5)
     casilla8= CasillaSorpresa.new(_mazo, "Sorpresa2: Ahi va una caja de comunidad")
     casilla10= CasillaCalle.new(titulo6)
     casilla11= CasillaCalle.new(titulo7)
     casilla6 = CasillaImpuesto.new(-250.0, "Control de alcoholemia")
     casilla12= CasillaCalle.new(titulo8)
     casilla13= CasillaCalle.new(titulo9)
     casilla15= CasillaCalle.new(titulo10)
     casilla14= CasillaSorpresa.new(_mazo, "Sorpresa3")
     casilla16= CasillaCalle.new(titulo11)
     casilla17 = CasillaCalle.new(titulo12)

      @tablero.aniade_casilla(casilla1)
      @tablero.aniade_casilla(casilla3)
      @tablero.aniade_casilla(casilla2)
      @tablero.aniade_casilla(casilla8)
      @tablero.aniade_casilla(casilla5)
      @tablero.aniade_casilla(casilla6)
      @tablero.aniade_casilla(casilla7)
      @tablero.aniade_casilla(casilla4)
      @tablero.aniade_casilla(casilla9)
      @tablero.aniade_juez()
      @tablero.aniade_casilla(casilla10)
      @tablero.aniade_casilla(casilla11)
      @tablero.aniade_casilla(casilla12)
      @tablero.aniade_casilla(casilla13)
      @tablero.aniade_casilla(casilla14)
      @tablero.aniade_casilla(casilla15)
      @tablero.aniade_casilla(casilla16)
      @tablero.aniade_casilla(casilla17)
    
    end
    
    def pasar_turno()
      puts "Pasar turno primero vale " + @indice_jugador_actual.to_s()
      @indice_jugador_actual = (@indice_jugador_actual+1)%@jugadores.length
      puts "Pasar turno despues vale " + @indice_jugador_actual.to_s()
    end
    
    def ranking()
      puts "estoy en ranking"
      _jugadores=Array.new()
      for i in 0..(@jugadores.length()-1)
        _jugadores << @jugadores.at(i)
      end
      for i in 0..(_jugadores.length()-2)
        for j in i+1..(_jugadores.length()-1)
          if(_jugadores.at(i).compare_to(_jugadores.at(j)))
            aux = Jugador.new(_jugadores.at(i))
            _jugadores.insert(i, _jugadores.at(j))
            _jugadores.insert(j, aux)
          end
        end
      end
      for i in 0..(_jugadores.length-1)
        puts _jugadores.at(i).to_sj()
      end
    end
    
    def salir_carcel_pagando()
      jugador_a = @jugadores.at(@indice_jugador_actual)
      return jugador_a.salir_carcel_pagando()
    end
    
    def salir_carcel_tirando()
      jugador_a = @jugadores.at(@indice_jugador_actual)
      return jugador_a.salir_carcel_tirando()
    end
    
    def siguiente_paso()
      jugador_actual = @jugadores.at(@indice_jugador_actual)
      operacion = @gestor_estados.operaciones_permitidas(jugador_actual, @estado)
      if(operacion == Civitas::Operaciones_juego::PASAR_TURNO)
        self.pasar_turno()
        self.siguiente_paso_completado(operacion)
      else
        if(operacion == Civitas::Operaciones_juego::AVANZAR)
          self.avanza_jugador()
          self.siguiente_paso_completado(operacion)
        end
      end
      return operacion
    end
    
    def siguiente_paso_completado(_operacion)
      @estado = @gestor_estados.siguiente_estado(@jugadores.at(@indice_jugador_actual), @estado, _operacion)
    end
    
    def vender(_ip)
      jugador_a= @jugadores.at(@indice_jugador_actual)
      return jugador_a.vender(_ip)
    end
      
    def get_casilla_actual()
      indice = @jugadores.at(@indice_jugador_actual).num_casilla_actual
      return @tablero.get_casilla(indice)
    end
    
    def mostar_tan()
      for i in 0..(@tablero.tamanio()-1)
        puts "El tablero " + @tablero.casillas.at(i).to_sc()
      end
    end
      
    
    #@author JuanCarlos Gonzalez Quesada
    #EXAMEN
    def robar_propiedad()
      jugador_actual = @jugadores.at(@indice_jugador_actual)
      casilla = @tablero.get_casilla(jugador_actual.numCasillaActual)
      titulo_robado = casilla.get_titulo_propiedad_a_robar()
      jugador_actual.robar_propiedad(titulo_robado)
    end
    
  end
end

