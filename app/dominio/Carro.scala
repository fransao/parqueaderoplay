package dominio

import util.EnumTipoVehiculo

class Carro(placa: String, tipoVehiculo: EnumTipoVehiculo.Value) extends Vehiculo(placa, tipoVehiculo) {

}
