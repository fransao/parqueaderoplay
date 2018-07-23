package util

import java.time.LocalDateTime
import java.time.temporal.ChronoUnit

object Util {

  def getDiasEntreDosFechas(fechaInicial: LocalDateTime, fechaFinal: LocalDateTime): Int = {
      fechaInicial.until( fechaFinal, ChronoUnit.DAYS).toInt
  }

  def getHorasEntreDosFechas(fechaInicial: LocalDateTime, fechaFinal: LocalDateTime): Int = {
    (fechaInicial.until( fechaFinal, ChronoUnit.HOURS) % 24).toInt
  }

  /*def getDiasEntreDosFechas2(fechaInicial: LocalDateTime, fechaFinal: LocalDateTime): Int = {

    val diferencia = ((fechaFinal - fechaInicial) / 1000).toInt
    var dias = 0

    if (diferencia > 86400) {
      dias = (diferencia / 86400).toInt
    }
    return dias
  }

  def getHorasEntreDosFechas2(fechaInicial: LocalDateTime, fechaFinal: LocalDateTime): Int = {

    var diferencia = ((fechaFinal - fechaInicial) / 1000).toInt

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
  */
}
