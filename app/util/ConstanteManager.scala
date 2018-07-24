package util

object ConstanteManager {

  // TEST
  val VEHICULO_PLACA_TEST = "XTZ123"
  val CARRO_PLACA_TEST    = "CAR888"
  val MOTO_PLACA_TEST     = "MOT333"
  val MOTO_PLACA_INICIA_A_TEST   = "ABC444"

  val MSJ_VEHICULO_NO_AUTORIZADO = "El vehiculo no esta autorizado para ingresar (puede ingresar el domingo o lunes)"
  val INT_MAXIMO_MOTOS           = 10
  val INT_MAXIMO_CARROS          = 20
  val MSJ_MAXIMO_MOTOS_PARQUEADOOS   = "El parqueadero solo puede tener máximo " + INT_MAXIMO_MOTOS + " motos"
  val MSJ_MAXIMO_CARROS_PARQUEADOOS  = "El parqueadero solo puede tener máximo " + INT_MAXIMO_CARROS + " carros"
  val MSJ_VEHICULO_YA_ESTA_INGRESADO = "Ya hay un vehiculo ingresado con esa placa"
  val MSJ_PARQUEO_SOLO_VEHICULOS     = "El parqueadero solo permite ingresar Carros y Motos"

  // DATABASE
  val DB_REGISTRAR_PLACA_VEHICULO    = "INSERT INTO parqueaderodb.vehiculo (`PLACA`, `TIPO_VEHICULO`, `CILINDRAJE`) VALUES (?,?,?)"
  val DB_INGRESAR_VEHICULO    = "INSERT INTO parqueaderodb.parqueo_vehiculo (`PLACA`, `TIPO_VEHICULO`, `FECHA_INGRESO`) VALUES (?,?,?)"
  val DB_SALIDA_VEHICULO      = "UPDATE parqueaderodb.parqueo_vehiculo SET FECHA_SALIDA= ?, TOTAL=? WHERE PLACA=? AND FECHA_SALIDA IS NULL"
  val DB_VEHICULOS_PARQUEADOS = "SELECT * FROM parqueaderodb.parqueo_vehiculo WHERE FECHA_SALIDA IS NULL"
  val DB_VEHICULOS_PARQUEADOS_BY_TYPE = "SELECT * FROM parqueaderodb.parqueo_vehiculo WHERE FECHA_SALIDA IS NULL AND TIPO_VEHICULO=?"
  val DB_CONSULTAR_VEHICULO   = "SELECT * FROM parqueaderodb.parqueo_vehiculo WHERE FECHA_SALIDA IS NULL AND PLACA=?"

 }
