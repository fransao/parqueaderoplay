package dominio

import util.EnumTipoVehiculo

case class VehiculoData(placa: String, tipoVehiculo: EnumTipoVehiculo.Value, val cilindraje: Int) {

}

