package controllers

import java.time.LocalDateTime

import dominio._
import javax.inject._
import modelo.ParqueoVehiculo
import play.api.data._
import play.api.data.Forms._
import play.api.Logger
import play.api.data.format.Formats._
import play.api.mvc.{request, _}
import sql.ParqueoDatabase
import util.EnumTipoVehiculo
import play.api.i18n._
import play.api.Play.current
import play.api.i18n.Messages.Implicits._
import play.i18n.Lang

@Singleton
class ParkingController @Inject()( cc: ControllerComponents)(implicit assetsFinder: AssetsFinder) extends AbstractController(cc) with I18nSupport {

  import play.api.data.validation.Constraints._

  /*implicit def matchFilterFormat: Formatter[EnumTipoVehiculo.Value] = new Formatter[EnumTipoVehiculo.Value] {
    override def bind(key: String, data: Map[String, String]) =
      data.get(key)
        .map(EnumTipoVehiculo.withName(_))
        .toRight(Seq(FormError(key, "error.required", Nil)))

    override def unbind(key: String, value: EnumTipoVehiculo.Value) = Map(key -> value.toString)
  }
*/

  val database = new ParqueoDatabase

  val vehiculoForm = Form(
    mapping(
      "placa" -> of[String],//nonEmptyText(minLength = 6, maxLength = 6),
      "tipoVehiculo" ->  of[String],//nonEmptyText, //Forms.of[EnumTipoVehiculo.Value],
      "cilindraje" -> of[Int]//number
    )(VehiculoDto.apply)(VehiculoDto.unapply)
  )

  def index = Action {
    Logger.info("Vehiculos parqueados.")

    val vigilante = new Vigilante(database)
    Ok(views.html.parqueadero.index(vigilante.consultarVehiculosParqueados()))
  }

  def create = Action { implicit request =>
    // Logger.info("Crear vehiculo.")

    Ok(views.html.parqueadero.create.render(vehiculoForm))
    /*
    vehiculoForm.bindFromRequest.fold(
      formWithErrors => {
        // binding failure, you retrieve the form containing errors:
        BadRequest(views.html.parqueadero.create.render(formWithErrors))
      },
      vehiculoData => {
        Ok(views.html.parqueadero.create.render(vehiculoForm))
      }
    )
    */
  }

  def show (placa: String) = Action {
    Logger.info("Mostrar vehiculo.")

    Ok(views.html.parqueadero.index2(s"Mostrar Vehiculo $placa"))
  }

  def edit (placa: String) = Action {
    Logger.info("Editar vehiculo.")

    Ok(views.html.parqueadero.index2(s"Editar Vehiculo $placa"))
  }

  def update = Action {
    Logger.info("Actualizar vehiculo.")

    Ok(views.html.parqueadero.index2(s"Actualizar Vehiculo "))
  }

  def save  = Action { implicit request =>
    Logger.info("Guardar vehiculo.")

    val formVehiculoDto = vehiculoForm.bindFromRequest
    println(formVehiculoDto)
    val vehiculoDto = formVehiculoDto
    println(vehiculoDto)

    val vehiculoData = request.body
    println("ingreso vehiculo" + vehiculoData)
    val vehiculo: Vehiculo = new Carro("SRA985", EnumTipoVehiculo.CARRO)
    val parqueoVehiculo = new ParqueoVehiculo(vehiculo.placa, vehiculo.tipoVehiculo.id, fechaIngreso = LocalDateTime.now(), null, 0)
    val vigilante = new Vigilante(database)

    vigilante.registrarIngresoVehiculoAParqueadero(vehiculo, parqueoVehiculo.fechaIngreso)

    Redirect(routes.ParkingController.index)

  }

  def destroy (placa: String) = Action {
    Logger.info("Destroy.")

    Ok(views.html.parqueadero.index2(s"Destroy $placa"))
  }

}
