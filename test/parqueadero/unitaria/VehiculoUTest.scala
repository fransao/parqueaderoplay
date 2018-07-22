package parqueadero.unitaria

import dominio.{Carro, Moto, Vehiculo}
import org.scalatest.FlatSpec
import util.{ConstanteManager, EnumTipoVehiculo}

class VehiculoUTest extends FlatSpec {

  "Placa del vehiculo" should ConstanteManager.VEHICULO_PLACA_TEST in {
    val vehiculo = new Vehiculo(ConstanteManager.VEHICULO_PLACA_TEST, EnumTipoVehiculo.CARRO)
    assert(ConstanteManager.VEHICULO_PLACA_TEST == vehiculo.placa)
  }

  "Placa del carro" should ConstanteManager.CARRO_PLACA_TEST in {
    val carro = new Carro(ConstanteManager.CARRO_PLACA_TEST, EnumTipoVehiculo.CARRO)
    assert(ConstanteManager.CARRO_PLACA_TEST == carro.placa)
  }

  "Placa de la moto" should ConstanteManager.MOTO_PLACA_TEST in {
    val moto = new Moto(ConstanteManager.MOTO_PLACA_TEST, EnumTipoVehiculo.MOTO, 600)
    assert(ConstanteManager.MOTO_PLACA_TEST == moto.placa)
  }

}
