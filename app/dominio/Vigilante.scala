package dominio

import java.util.{Calendar, Date}

import exception.ParqueaderoException
import util.{ConstanteManager, EnumTiempo, EnumTipoVehiculo, Util}

class Vigilante {

  def loadTarifa(): List[Tarifa] = {
    List(
                       new Tarifa(EnumTipoVehiculo.CARRO, EnumTiempo.HORA, 1000.0f),
                       new Tarifa(EnumTipoVehiculo.CARRO, EnumTiempo.DIA,  8000.0f),
                       new Tarifa(EnumTipoVehiculo.MOTO, EnumTiempo.HORA,  500.0f),
                       new Tarifa(EnumTipoVehiculo.MOTO, EnumTiempo.DIA,   4000.0f)
    )
  }

  def registrarIngresoVehiculoAParqueadero(vehiculo:Vehiculo, fechaIngreso:Date): Unit = {
    if (placaIniciaA(vehiculo.placa)) {
      validarDiaDomingoLunes(fechaIngreso)
    }
  }

  def placaIniciaA(placa:String):Boolean = {
    placa.startsWith("a") || placa.startsWith("A")
  }

  def validarDiaDomingoLunes(fechaIngreso:Date): Unit = {
    val calendario = Calendar.getInstance()
    calendario.setTime(fechaIngreso)

    val diasemana = calendario.get(Calendar.DAY_OF_WEEK)

    if (Calendar.SUNDAY != diasemana && diasemana != Calendar.MONDAY) {
      throw new ParqueaderoException(ConstanteManager.MSJ_VEHICULO_NO_AUTORIZADO);
    }
  }

  def calcularCobroParqueadero(vehiculo: Vehiculo, fechaIngreso:Date, fechaSalida:Date): Float = {
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

}
