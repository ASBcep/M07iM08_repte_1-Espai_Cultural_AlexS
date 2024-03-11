package asb.m07im08.espai_cultural_alexs

import android.os.Build
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import java.util.*

object ConversorDateTime {
    fun convertir(localDateTime: LocalDateTime): String {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val formatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM)
            localDateTime.format(formatter)
        } else {
            val formatter = SimpleDateFormat.getDateTimeInstance(SimpleDateFormat.MEDIUM, SimpleDateFormat.MEDIUM, Locale.getDefault())
            formatter.format(Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant()))
        }
    }
    fun crearLocalDateTime(any: Int, mes: Int, dia: Int, hora: Int, minuts: Int): LocalDateTime {//només 1 funció, no fa falta usar-lo
        return LocalDateTime.of(any, mes, dia, hora, minuts)
    }
}