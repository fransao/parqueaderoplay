package controllers

import java.time.LocalDateTime

import dominio._
import javax.inject._
import modelo.ParqueoVehiculo
import play.api.{Logger, mvc}
import play.api.mvc.{request, _}
import sql.ParqueoDatabase
import util.EnumTipoVehiculo
import play.api.i18n._
import play.mvc.Http

// import views.html.errors._

@Singleton
class ParkingController @Inject()( cc: ControllerComponents)(implicit assetsFinder: AssetsFinder) extends AbstractController(cc) with I18nSupport {
  import VehiculoDto._

  val database = new ParqueoDatabase

  def index = Action {
    Logger.info("Vehiculos parqueados.")

    val vigilante = new Vigilante(database)
    Ok(views.html.parking.index(vigilante.consultarVehiculosParqueados()))
  }

  def create = Action { implicit request =>
    // Logger.info("Crear vehiculo.")

    if (vehiculoForm.hasErrors) {

      // implicit val flash = ("danger" -> "Por favor ingrese los campos obligatorios")
      // flashing("danger" -> "Por favor ingrese los campos obligatorios")
      BadRequest(views.html.parking.create(vehiculoForm))
    } else {
      // flashing("success" -> "Vehiculo ingresado correctamente")
      Ok(views.html.parking.create(vehiculoForm))
    }
    /*
    vehiculoForm.bindFromRequest.fold(
      formWithErrors => {
        // binding failure, you retrieve the form containing errors:
        BadRequest(views.html.parking.create.render(formWithErrors))
      },
      vehiculoData => {
        Ok(views.html.parking.create.render(vehiculoForm))
      }
    )
    */
  }

  def show (placa: String) = Action {
    Logger.info("Mostrar vehiculo.")

    val vigilante = new Vigilante(database)

    val vehiculo = vigilante.obtenerVehiculoIngresado(new Vehiculo(placa, EnumTipoVehiculo.CARRO))
    //val anyData = Map("placa" -> vehiculo.placa, "tipoVehiculo" -> vehiculo.tipoVehiculo.toString, "cilindraje" -> "")

    Ok(views.html.parking.show(vehiculo))

  }

  def edit (placa: String) = Action { implicit request =>
    Logger.info("Editar vehiculo.")

    val vigilante = new Vigilante(database)

    val vehiculo = vigilante.obtenerVehiculoIngresado(new Vehiculo(placa, EnumTipoVehiculo.CARRO))
    val anyData = Map("placa" -> vehiculo.placa, "tipoVehiculo" -> vehiculo.tipoVehiculo.toString, "cilindraje" -> "")

    Ok(views.html.parking.edit(vehiculoForm.bind(anyData)))
  }

  def update = Action { implicit request =>
    Logger.info("Salida vehiculo.")

    vehiculoForm.bindFromRequest.fold(
      formWithErrors => {
        println("Error: " + formWithErrors)
        // binding failure, you retrieve the form containing errors:
        BadRequest(views.html.parking.edit(formWithErrors))
      },
      vehiculoData => {
        println("Data: " + vehiculoData)

        var parqueoVehiculo = new ParqueoVehiculo(vehiculoData.placa, vehiculoData.tipoVehiculo.toInt, fechaIngreso = LocalDateTime.now(), null, 0)
        val vigilante = new Vigilante(database)
        var valorPagar: Double = 0.0

        if (vehiculoData.tipoVehiculo.toInt == 1) {
          valorPagar = vigilante.registrarSalidaVehiculoParqueadero(new Carro(vehiculoData.placa, EnumTipoVehiculo.CARRO), parqueoVehiculo.fechaIngreso)
        } else if (vehiculoData.tipoVehiculo.toInt == 2) {
          valorPagar = vigilante.registrarSalidaVehiculoParqueadero(new Moto(vehiculoData.placa, EnumTipoVehiculo.MOTO, vehiculoData.cilindraje), parqueoVehiculo.fechaIngreso)
        }
        Ok(views.html.parking.index2(s"Vehiculo ${vehiculoData.placa}, valor a pagar: $valorPagar"))
      })
  }

  def save  = Action { implicit request =>
    Logger.info("Ingresar vehiculo.")

    vehiculoForm.bindFromRequest.fold(
      formWithErrors => {
        println("Error: " + formWithErrors)
        // binding failure, you retrieve the form containing errors:
        BadRequest(views.html.parking.create(formWithErrors))
      },
      vehiculoData => {
        println("Data: " + vehiculoData)

        var parqueoVehiculo = new ParqueoVehiculo(vehiculoData.placa, vehiculoData.tipoVehiculo.toInt, fechaIngreso = LocalDateTime.now(), null, 0)
        val vigilante = new Vigilante(database)

        if (vehiculoData.tipoVehiculo.toInt == 1) {
          parqueoVehiculo = vigilante.registrarIngresoVehiculoAParqueadero(new Carro(vehiculoData.placa, EnumTipoVehiculo.CARRO), parqueoVehiculo.fechaIngreso)
        } else if (vehiculoData.tipoVehiculo.toInt == 2) {
          parqueoVehiculo = vigilante.registrarIngresoVehiculoAParqueadero(new Moto(vehiculoData.placa, EnumTipoVehiculo.MOTO, vehiculoData.cilindraje), parqueoVehiculo.fechaIngreso)
        } else {
          parqueoVehiculo = vigilante.registrarIngresoVehiculoAParqueadero(new Vehiculo(vehiculoData.placa, EnumTipoVehiculo.CARRO), parqueoVehiculo.fechaIngreso)
        }

        if (parqueoVehiculo == null) {
          Ok(views.html.parking.create(vehiculoForm))
        } else {
          Redirect(routes.ParkingController.index)
        }

      }
    )
/*
    val vehiculoDto = form.get
    println("Data: " + vehiculoDto)

    val vehiculo: Vehiculo = new Carro(vehiculoDto.placa, EnumTipoVehiculo.CARRO)
    val parqueoVehiculo = new ParqueoVehiculo(vehiculo.placa, vehiculo.tipoVehiculo.id, fechaIngreso = LocalDateTime.now(), null, 0)
    val vigilante = new Vigilante(database)

    vigilante.registrarIngresoVehiculoAParqueadero(vehiculo, parqueoVehiculo.fechaIngreso)

    Redirect(routes.ParkingController.index)
*/
  }

  def destroy (placa: String) = Action {
    Logger.info("Destroy.")

    val vigilante = new Vigilante(database)

    vigilante.deleteVehiculoIngresado(placa)

    Redirect(routes.ParkingController.index)
  }

}
