package parqueadero.integracion

import org.scalatest.FlatSpec
import play.api.db.{Database, Databases}

class ConexionDataBaseITest extends FlatSpec {


  def withMyDatabase[T](block: Database => T) = {
    Databases.withDatabase(
      driver = "com.mysql.jdbc.Driver", // "com.mysql.cj.jdbc.Driver"
      url = "jdbc:mysql://localhost:3306/parqueaderodb",
      name = "parqueaderodb",
      config = Map(
        "username" -> "root",
        "password" -> "root"
      )
    )(block)
  }

  withMyDatabase { database =>
    val connection = database.getConnection()
    val resultset = connection.prepareStatement("select * from parqueaderodb.parqueo_vehiculo where consecutivo = 1 ").executeQuery()

    println(resultset)

    connection.prepareStatement("INSERT INTO parqueaderodb.parqueo_vehiculo (`PLACA`, `TIPO_VEHICULO`, `FECHA_INGRESO`, `FECHA_SALIDA`, `TOTAL`) VALUES ('ZZZ111', 1, '2018-07-23 09:21:00', NULL, '0')").execute()

    assert(connection.prepareStatement("select * from parqueaderodb.parqueo_vehiculo where consecutivo = 2").executeQuery().next() == true)

  }

  /*
  Databases.withDatabase(

    driver = "com.mysql.jdbc.Driver",
    url = "jdbc:mysql://localhost:3306/parqueaderodb"
  ) { database =>
    val connection = database.getConnection()

    connection.prepareStatement("insert into parqueo_vehiculo values ('2', 'XTZ666', '1', '2018-07-23 09:21:00', NULL, '0')").execute()

    assert(connection.prepareStatement("select * from parqueo_vehiculo where consecutivo = 1").executeQuery().next() == true)

    println(connection)
  }
*/

}
