package dominio

import java.time.{DayOfWeek, LocalDateTime}

import exception.ParqueaderoException
import modelo.ParqueoVehiculo
import sql.ParqueoDatabase
import util.{ConstanteManager, EnumTiempo, EnumTipoVehiculo, Util}

class Vigilante(parqueoDatabase:ParqueoDatabase) {

  val database = parqueoDatabase

  def loadTarifa(): List[Tarifa] = {
    List(
                       new Tarifa(EnumTipoVehiculo.CARRO, EnumTiempo.HORA, 1000.0f),
                       new Tarifa(EnumTipoVehiculo.CARRO, EnumTiempo.DIA,  8000.0f),
                       new Tarifa(EnumTipoVehiculo.MOTO, EnumTiempo.HORA,  500.0f),
                       new Tarifa(EnumTipoVehiculo.MOTO, EnumTiempo.DIA,   4000.0f)
    )
  }

  def registrarIngresoVehiculoAParqueadero(vehiculo:Vehiculo, fechaIngreso:LocalDateTime): ParqueoVehiculo = {
    var parqueoVehiculo: ParqueoVehiculo = null
    //var convertToVehiculo: Vehiculo = null

    if (placaIniciaA(vehiculo.placa)) {
      validarDiaDomingoLunes(fechaIngreso)
    }

    validarDisponibilidad(vehiculo)

    if (estaVehiculoIngresado(vehiculo)) {
      throw new ParqueaderoException(ConstanteManager.MSJ_VEHICULO_YA_ESTA_INGRESADO)
    }

    /*if (vehiculo.isInstanceOf[RequestVehiculo]) {
      if (EnumTipoVehiculo.MOTO.equals(vehiculo.tipoVehiculo)) {
        convertToVehiculo = vehiculo.asInstanceOf[Moto]
      }
      else if (EnumTipoVehiculo.CARRO.equals(vehiculo.tipoVehiculo)) {
        convertToVehiculo = vehiculo.asInstanceOf[Carro]
      }
      else {
        convertToVehiculo = vehiculo.asInstanceOf[Vehiculo]
      }
    }*/


    if (vehiculo.isInstanceOf[Moto] || vehiculo.isInstanceOf[Carro]) {
      parqueoVehiculo = ingresarVehiculo(vehiculo, fechaIngreso)
    }
    else {
      throw new ParqueaderoException(ConstanteManager.MSJ_PARQUEO_SOLO_VEHICULOS)
    }
    parqueoVehiculo
  }

  def registrarSalidaVehiculoParqueadero(vehiculo: Vehiculo, fechaSalida: LocalDateTime): Unit = {
    val salidaVehiculo = database.obtenerVehiculoParqueado(vehiculo.placa)

    if (salidaVehiculo != null) {
      salidaVehiculo.fechaSalidaV = fechaSalida
      salidaVehiculo.valorV       = calcularCobroParqueadero(vehiculo, salidaVehiculo.fechaIngreso, fechaSalida)
      database.salidaVehiculoParqueado(salidaVehiculo)
    }
  }

  private[this] def ingresarVehiculo(vehiculo: Vehiculo, fechaIngreso: LocalDateTime): ParqueoVehiculo = {

    if (!database.estaPlacaVehiculoRegistrada(vehiculo.placa)) {
      database.registrarPlacaVehiculo(vehiculo)
    }

    val ingresoVehiculo = new ParqueoVehiculo(vehiculo.placa, vehiculo.tipoVehiculo.id, fechaIngreso, null, 0.0d)
    ingresoVehiculo.fechaIngresoV = fechaIngreso
    database.ingresarVehiculo(ingresoVehiculo)
    database.obtenerVehiculoParqueado(vehiculo.placa)
  }

  def placaIniciaA(placa:String):Boolean = {
    placa.startsWith("a") || placa.startsWith("A")
  }

  def validarDiaDomingoLunes(fechaIngreso:LocalDateTime): Unit = {
    val diasemana = fechaIngreso.getDayOfWeek
    if (DayOfWeek.SUNDAY != diasemana && DayOfWeek.SUNDAY != diasemana) {
      throw new ParqueaderoException(ConstanteManager.MSJ_VEHICULO_NO_AUTORIZADO);
    }
  }

  def calcularCobroParqueadero(vehiculo: Vehiculo, fechaIngreso: LocalDateTime, fechaSalida: LocalDateTime): Float = {
    var valorPagar = 0.0f
    val diasEntreDosFechas  = Util.getDiasEntreDosFechas(fechaIngreso, fechaSalida)
    val horasEntreDosFechas = Util.getHorasEntreDosFechas(fechaIngreso, fechaSalida)

    val tarifas: List[Tarifa] = loadTarifa()

    valorPagar += calcularCobroPorDia(vehiculo, diasEntreDosFechas, tarifas)
    valorPagar += calcularCobroPorHora(vehiculo, horasEntreDosFechas, tarifas)
    valorPagar += obtenerRegargo(vehiculo)

    return valorPagar
  }

  def calcularCobroPorDia(vehiculo: Vehiculo, diasEntreDosFechas: Int, tarifas: List[Tarifa]):Float = {
    var valorPagar = 0.0f
    if (diasEntreDosFechas > 0) {
      val valorTarifa: Tarifa = tarifas.filter(tarifa => tarifa.tipoVehiculo == vehiculo.tipoVehiculo && EnumTiempo.DIA == tarifa.tiempo).head
      valorPagar = valorTarifa.valor * diasEntreDosFechas
    }
    return valorPagar
  }

  def calcularCobroPorHora(vehiculo: Vehiculo, horasEntreDosFechas: Int, tarifas: List[Tarifa]):Float = {
    var valorPagar = 0.0f
    if (horasEntreDosFechas > 0) {
      val valorTarifa: Tarifa = tarifas.filter(tarifa => tarifa.tipoVehiculo == vehiculo.tipoVehiculo && EnumTiempo.HORA == tarifa.tiempo).head
      valorPagar = valorTarifa.valor * horasEntreDosFechas
    }
    return valorPagar
  }

  def obtenerRegargo(vehiculo: Vehiculo): Float = {
    var value = 0.0f
    if (vehiculo.isInstanceOf[Moto]) {
      val moto = vehiculo.asInstanceOf[Moto]
      if (moto.cilindraje > 500) {
        value = 2000
      }
    }
    return value
  }

  def validarDisponibilidad(vehiculo: Vehiculo): Unit = {
    val vehiculosParqueados = database.consultarVehiculosParqueadosByTipo(vehiculo.tipoVehiculo)
    if (EnumTipoVehiculo.MOTO.equals(vehiculo.tipoVehiculo) && vehiculosParqueados.size >= ConstanteManager.INT_MAXIMO_MOTOS) throw new ParqueaderoException(ConstanteManager.MSJ_MAXIMO_MOTOS_PARQUEADOOS)
    else if (EnumTipoVehiculo.CARRO.equals(vehiculo.tipoVehiculo) && vehiculosParqueados.size >= ConstanteManager.INT_MAXIMO_CARROS) throw new ParqueaderoException(ConstanteManager.MSJ_MAXIMO_CARROS_PARQUEADOOS)
  }

  def estaVehiculoIngresado(vehiculo: Vehiculo): Boolean = {
    database.estaVehiculoParqueado(vehiculo.placa)
  }

  def obtenerVehiculoIngresado(vehiculo: Vehiculo): ParqueoVehiculo = {
    database.obtenerVehiculoParqueado(vehiculo.placa)
  }

  def consultarVehiculosParqueados() : List[ParqueoVehiculo] = {
    database.consultarVehiculosParqueados()
  }
}
