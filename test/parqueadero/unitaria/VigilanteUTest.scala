package parqueadero.unitaria

import java.util.{Calendar, Date}

import dominio.{Moto, Vigilante}
import exception.ParqueaderoException
import org.scalatest.FlatSpec
import util.{ConstanteManager, EnumTipoVehiculo, Util}

class VigilanteUTest extends FlatSpec {

  "Ingresar vehiculo" should "empezar con placa A" in {
    val moto = new Moto(ConstanteManager.MOTO_PLACA_INICIA_A_TEST, EnumTipoVehiculo.MOTO, 600)
    val vigilante = new Vigilante ()

    try {
      vigilante.registrarIngresoVehiculoAParqueadero(moto, new Date())
      fail()
    } catch {
      case pe: ParqueaderoException => assert(ConstanteManager.MSJ_VEHICULO_NO_AUTORIZADO == pe.getMessage())
    }
  }

  "Tiempo vehiculo parqueado" should "mayor a cero" in {
    val calendarFechaIngreso :Calendar  = Calendar.getInstance()
    val calendarFechaSalida  :Calendar  = Calendar.getInstance()
    calendarFechaSalida.add(Calendar.DAY_OF_MONTH, 2)
    calendarFechaSalida.add(Calendar.HOUR, 4)

    assert(2 == Util.getDiasEntreDosFechas(calendarFechaIngreso.getTime, calendarFechaSalida.getTime))
    assert(4 == Util.getHorasEntreDosFechas(calendarFechaIngreso.getTime, calendarFechaSalida.getTime))

  }

  "Valor a pagar del vehiculo " should "mayor a cero" in {
    // arrange
    val vigilante = new Vigilante ()
    val moto = new Moto(ConstanteManager.MOTO_PLACA_TEST, EnumTipoVehiculo.MOTO, 500)
    val calendarFechaIngreso :Calendar  = Calendar.getInstance()
    val calendarFechaSalida  :Calendar  = Calendar.getInstance()
    calendarFechaSalida.add(Calendar.DAY_OF_MONTH, 2)
    calendarFechaSalida.add(Calendar.HOUR, 4)

    // act
    val valorPagar = vigilante.calcularCobroParqueadero(moto, calendarFechaIngreso.getTime, calendarFechaSalida.getTime)

    //assert
    assert(valorPagar == 10000.0f)

  }

  "Valor a pagar con recargo de la moto" should "mayor a cero" in {
    // arrange
    val vigilante = new Vigilante ()
    val moto = new Moto(ConstanteManager.MOTO_PLACA_TEST, EnumTipoVehiculo.MOTO, 600)
    val calendarFechaIngreso :Calendar  = Calendar.getInstance()
    val calendarFechaSalida  :Calendar  = Calendar.getInstance()
    calendarFechaSalida.add(Calendar.DAY_OF_MONTH, 2)
    calendarFechaSalida.add(Calendar.HOUR, 4)

    // act
    val valorPagar = vigilante.calcularCobroParqueadero(moto, calendarFechaIngreso.getTime, calendarFechaSalida.getTime)

    //assert
    assert(valorPagar == 12000.0f)

  }

}
