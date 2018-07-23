package modelo

import java.time.LocalDateTime

import play.api.libs.json.Json

case class ParqueoVehiculo(placa: String, tipoVehiculo: Int, fechaIngreso: LocalDateTime, fechaSalida: LocalDateTime, valor:Double)

object ParqueoVehiculo {
  implicit val cofffeFormat = Json.format[ParqueoVehiculo]
}
