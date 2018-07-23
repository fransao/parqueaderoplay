package dominio

import util.EnumTipoVehiculo

class RequestVehiculo(placa: String, tipoVehiculo: EnumTipoVehiculo.Value, val cilindraje: Int) extends Vehiculo(placa, tipoVehiculo) {

}
