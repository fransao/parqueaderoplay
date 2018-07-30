package dominio

import play.api.data._
import play.api.data.Forms._
import util.EnumTipoVehiculo

class RequestVehiculo(placa: String, tipoVehiculo: EnumTipoVehiculo.Value, val cilindraje: Int) extends Vehiculo(placa, tipoVehiculo) {

  def getMoto(): Moto = {
    new Moto(placa, tipoVehiculo, cilindraje)
  }

  def getCarro(): Carro = {
    new Carro(placa, tipoVehiculo)
  }

}
