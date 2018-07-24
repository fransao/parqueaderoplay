package modelo

import java.time.LocalDateTime

import play.api.libs.json.{JsResult, JsValue, Json, Reads}

case class ParqueoVehiculo(/*vehiculo: Vehiculo,*/ placa:String, tipoVehiculo:Int, fechaIngreso: LocalDateTime, fechaSalida: LocalDateTime, valor:Double) {
  var fechaIngresoV = fechaIngreso
  var fechaSalidaV  = fechaSalida
  var valorV        = valor
}

object ParqueoVehiculo {

  /*implicit object VehiculoReads extends Reads[Vehiculo] {
    def reads(jsValue: JsValue): JsResult[Vehiculo] = {
      (jsValue \ "placa").validate[Int].map(fromString)
    }
  } */
  implicit val parqueoFormat = Json.format[ParqueoVehiculo]

  var list: List[ParqueoVehiculo] = {
    List(ParqueoVehiculo("XTS456", 1, LocalDateTime.now(), LocalDateTime.now(), 11000),
         ParqueoVehiculo("UIO741", 1, LocalDateTime.now(), LocalDateTime.now(), 12000)
    )
  }

}
