package modelo

import java.time.LocalDateTime

import com.fasterxml.jackson.annotation.JsonFormat
import play.api.libs.functional.syntax._
import play.api.libs.json._

case class ParqueoVehiculo(/*vehiculo: Vehiculo,*/ placa:String, tipoVehiculo:Int, fechaIngreso: LocalDateTime, fechaSalida: LocalDateTime, valor:Double) {
  @JsonFormat(pattern="yyyyMMdd HH:mm:ss.SSS", shape=JsonFormat.Shape.STRING)
  var fechaIngresoV = fechaIngreso
  @JsonFormat(pattern="yyyyMMdd HH:mm:ss.SSS", shape=JsonFormat.Shape.STRING)
  var fechaSalidaV  = fechaSalida
  var valorV        = valor


  def this(placa:String, tipoVehiculo:Int) {
    this(placa, tipoVehiculo, LocalDateTime.now(), LocalDateTime.now(), 0.0d);
  }

}

object ParqueoVehiculo {

  implicit val vehiculoWrites: Writes[ParqueoVehiculo] = (
    (JsPath \ "placa").write[String] and
    (JsPath \ "tipovehiculo").write[Int] and
    (JsPath \ "fechaingreso").write[LocalDateTime] and
    (JsPath \ "fechasalida").write[LocalDateTime] and
    (JsPath \ "valor").write[Double]
  )(unlift(ParqueoVehiculo.unapply))

  implicit val vehiculoReads: Reads[ParqueoVehiculo] = (
    (JsPath \ "placa").read[String] and
    (JsPath \ "tipovehiculo").read[Int] and
    (JsPath \ "fechaingreso").read[LocalDateTime] and
    (JsPath \ "fechasalida").read[LocalDateTime] and
    (JsPath \ "valor").read[Double]
  )(ParqueoVehiculo.apply _)

  var list: List[ParqueoVehiculo] = {
    List(ParqueoVehiculo("XTS456", 1, LocalDateTime.now(), LocalDateTime.now(), 11000),
         ParqueoVehiculo("UIO741", 1, LocalDateTime.now(), LocalDateTime.now(), 12000)
    )
  }

  def save(parqueoVehiculo: ParqueoVehiculo) = {
    list = list ::: List(parqueoVehiculo)
  }

}
