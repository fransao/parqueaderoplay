package dominio

object VehiculoDto {
  import play.api.data.Forms._
  import play.api.data.Form

  case class Data (placa: String, tipoVehiculo: String, val cilindraje: Int) {}

  val vehiculoForm = Form(
    mapping(
      "placa" -> nonEmptyText(minLength = 6, maxLength = 6),
      "tipoVehiculo" -> nonEmptyText,
      "cilindraje" -> number
    )(Data.apply)(Data.unapply)
  )
}
