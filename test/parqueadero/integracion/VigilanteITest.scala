package parqueadero.integracion

import java.time.LocalDateTime

import dominio.{Carro, Vehiculo, Vigilante}
import modelo.ParqueoVehiculo
import org.scalatest.FlatSpec
import sql.ParqueoDatabase
import util.EnumTipoVehiculo

class VigilanteITest extends FlatSpec {

  val database = new ParqueoDatabase

  "Ingresar carro" should " retornar true" in {
    // arrange
    val vehiculo: Vehiculo = new Carro("SRA985", EnumTipoVehiculo.CARRO)
    val parqueoVehiculo = new ParqueoVehiculo(vehiculo.placa, vehiculo.tipoVehiculo.id, fechaIngreso = LocalDateTime.now(), null, 0)
    val vigilante = new Vigilante(database)

    // act
    vigilante.registrarIngresoVehiculoAParqueadero(vehiculo, parqueoVehiculo.fechaIngreso)

    // assert
    assert(vigilante.estaVehiculoIngresado(vehiculo)== true)
  }

  "Salida carro" should "tener fecha de salida" in {
    // arrange
    val vigilante = new Vigilante(database)
    val vehiculo: Vehiculo = new Carro("SRA985", EnumTipoVehiculo.CARRO)
    var parqueoVehiculo = vigilante.obtenerVehiculoIngresado(vehiculo)
    if (parqueoVehiculo == null) {
      parqueoVehiculo = vigilante.registrarIngresoVehiculoAParqueadero(vehiculo, LocalDateTime.now())
    }
    var salidaVehiculo = new ParqueoVehiculo(parqueoVehiculo.placa, parqueoVehiculo.tipoVehiculo, parqueoVehiculo.fechaIngreso, LocalDateTime.now(), 0)

    // act
    vigilante.registrarSalidaVehiculoParqueadero(vehiculo, salidaVehiculo.fechaSalida)

    // assert
    assert(vigilante.estaVehiculoIngresado(vehiculo) == false)
  }

  "Mostrar lista de vehiculos parqueados" should "no ser vacia" in {
    // arrange
    val vigilante = new Vigilante(database)

    assert(vigilante.consultarVehiculosParqueados().size > 0)
  }

}
