package util

import java.time.LocalDateTime
import java.util.Date


object Util {

  def getDiasEntreDosFechas(fechaInicial: Date, fechaFinal: Date): Int = {

    val diferencia = ((fechaFinal.getTime() - fechaInicial.getTime()) / 1000).toInt
    var dias = 0

    if (diferencia > 86400) {
      dias = (diferencia / 86400).toInt
    }
    return dias
  }

  def getHorasEntreDosFechas(fechaInicial: Date, fechaFinal: Date): Int = {

    var diferencia = ((fechaFinal.getTime() - fechaInicial.getTime()) / 1000).toInt

    var dias = 0
    var horas = 0

    if (diferencia > 86400) {
      dias = (diferencia / 86400).toInt
      diferencia = (diferencia - (dias * 86400)).toInt
    }

    if (diferencia > 3600) {
      horas = (diferencia / 3600)
      diferencia = (diferencia - (horas * 3600)).toInt
    }

    if (diferencia > 0) {
      horas += 1
    }

    return horas
  }


}
