package sql

import java.sql.Timestamp

import scala.collection.mutable.ListBuffer
import modelo.ParqueoVehiculo
import play.api.db.{Database, Databases}
import util.ConstanteManager

class ParqueoDatabase {

  def withMyDatabase[T](block: Database => T) = {
    Databases.withDatabase(
      driver = "com.mysql.jdbc.Driver",
      url = "jdbc:mysql://localhost:3306/parqueaderodb",
      name = "parqueaderodb",
      config = Map(
        "username" -> "root",
        "password" -> "root"
      )
    )(block)
  }

  def ingresarVehiculo(vehiculo: ParqueoVehiculo): Boolean = {

    withMyDatabase { database =>
      val connection = database.getConnection()
      var ps = connection.prepareStatement(ConstanteManager.DB_INGRESAR_VEHICULO)
      ps.setString(1, vehiculo.placa)
      ps.setInt(2, vehiculo.tipoVehiculo)
      ps.setTimestamp(3, Timestamp.valueOf(vehiculo.fechaIngreso))
      ps.execute()
    }

  }

  def salidaVehiculoParqueado(vehiculo: ParqueoVehiculo): Unit = {

    withMyDatabase { database =>
      val connection = database.getConnection()
      var ps = connection.prepareStatement(ConstanteManager.DB_SALIDA_VEHICULO)
      ps.setTimestamp(1, Timestamp.valueOf(vehiculo.fechaSalida))
      ps.setDouble(2, vehiculo.valor)
      ps.setString(3, vehiculo.placa)

      ps.executeUpdate()
    }

  }

  def obtenerVehiculoParqueado(placa: String): ParqueoVehiculo = {
    var vehiculoParqueado: ParqueoVehiculo = null

    withMyDatabase { database =>
      val connection = database.getConnection()
      var ps = connection.prepareStatement(ConstanteManager.DB_CONSULTAR_VEHICULO)
      ps.setString(1, placa)
      val rs = ps.executeQuery()
      if (rs.next()) {
        vehiculoParqueado = new ParqueoVehiculo(rs.getString("PLACA"), rs.getInt("TIPO_VEHICULO"), rs.getTimestamp("FECHA_INGRESO").toLocalDateTime, null, rs.getDouble("TOTAL"))
      }
    }
    vehiculoParqueado
  }

  def estaVehiculoParqueado(placa: String): Boolean = {
    var estaParqueado = false

    withMyDatabase { database =>
      val connection = database.getConnection()
      var ps = connection.prepareStatement(ConstanteManager.DB_CONSULTAR_VEHICULO)
      ps.setString(1, placa)
      val rs = ps.executeQuery()
      if (rs.next()) {
        estaParqueado = true
      }
    }
    estaParqueado
  }

  def consultarVehiculosParqueados(): List[ParqueoVehiculo] = {

    val listVehiculosParqueados = new ListBuffer[ParqueoVehiculo]()
    withMyDatabase { database =>
      val connection = database.getConnection()
      val rs = connection.prepareStatement(ConstanteManager.DB_VEHICULOS_PARQUEADOS).executeQuery()
      while (rs.next()) {
        listVehiculosParqueados += new ParqueoVehiculo(rs.getString("PLACA"), rs.getInt("TIPO_VEHICULO"), rs.getTimestamp("FECHA_INGRESO").toLocalDateTime, null, rs.getDouble("TOTAL"))
      }
    }
    listVehiculosParqueados.toList
  }

}
