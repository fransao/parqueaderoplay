package util

object ConstanteManager {

  // TEST
  val VEHICULO_PLACA_TEST = "XTZ123"
  val CARRO_PLACA_TEST    = "CAR888"
  val MOTO_PLACA_TEST     = "MOT333"
  val MOTO_PLACA_INICIA_A_TEST   = "ABC444"

  val MSJ_VEHICULO_NO_AUTORIZADO = "El vehiculo no esta autorizado para ingresar (puede ingresar el domingo o lunes)"

  // DATABASE
  val DB_INGRESAR_VEHICULO    = "INSERT INTO parqueaderodb.parqueo_vehiculo (`PLACA`, `TIPO_VEHICULO`, `FECHA_INGRESO`) VALUES (?,?,?)"
  val DB_SALIDA_VEHICULO      = "UPDATE parqueaderodb.parqueo_vehiculo SET FECHA_SALIDA= ?, TOTAL=? WHERE PLACA=? AND FECHA_SALIDA IS NULL"
  val DB_VEHICULOS_PARQUEADOS = "SELECT * FROM parqueaderodb.parqueo_vehiculo WHERE FECHA_SALIDA IS NULL"
  val DB_CONSULTAR_VEHICULO   = "SELECT * FROM parqueaderodb.parqueo_vehiculo WHERE FECHA_SALIDA IS NULL AND PLACA=?"

 }
