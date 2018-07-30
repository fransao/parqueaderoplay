package dominio

import util.EnumTipoVehiculo

case class VehiculoDto(placa: String, tipoVehiculo: String, val cilindraje: Int) {

}
