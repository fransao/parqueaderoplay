package dominio

import util.EnumTipoVehiculo
import util.EnumTiempo

class Tarifa(val tipoVehiculo:EnumTipoVehiculo.Value, val tiempo:EnumTiempo.Value, val valor:Float) {

}
