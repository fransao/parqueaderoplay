package parqueadero.integracion

import java.time.LocalDateTime

import modelo.ParqueoVehiculo
import org.scalatest.FlatSpec
import sql.ParqueoDatabase

class VigilanteITest extends FlatSpec {

  "Ingresar vehiculo" should " retornar true" in {
    // arrange
    val parqueoVehiculo = new ParqueoVehiculo("SRA985", tipoVehiculo = 1, fechaIngreso = LocalDateTime.now(), null, 0)
    var database = new ParqueoDatabase

    // act
    database.ingresarVehiculo(parqueoVehiculo)

    // assert
    assert(database.estaVehiculoParqueado(parqueoVehiculo.placa)== true)
  }

  "Salida vehiculo" should "tener fecha de salida" in {
    // arrange
    var database = new ParqueoDatabase
    var parqueoVehiculo = database.obtenerVehiculoParqueado("SRA985")
    var salidaVehiculo = new ParqueoVehiculo(parqueoVehiculo.placa, parqueoVehiculo.tipoVehiculo,parqueoVehiculo.fechaIngreso, LocalDateTime.now(), 20)

    // act
    database.salidaVehiculoParqueado(salidaVehiculo)

    // assert
    assert(database.estaVehiculoParqueado("SRA985") == false)
  }

}
