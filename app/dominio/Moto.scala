package dominio

import util.EnumTipoVehiculo

class Moto(placa: String, tipoVehiculo: EnumTipoVehiculo.Value, val cilindraje: Int) extends Vehiculo(placa, tipoVehiculo) {

}
