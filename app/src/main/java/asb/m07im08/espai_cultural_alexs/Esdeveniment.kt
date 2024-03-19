package asb.m07im08.espai_cultural_alexs

import java.io.Serializable
import java.time.LocalDateTime

class Esdeveniment (
    var id: Int = -1,
    val nom: String = id.toString(),
    //val imatge: String =  id.toString(),
    val descripcio: String = id.toString(),
    val data: LocalDateTime = LocalDateTime.now(),
    val idioma: String = id.toString(),
    val preu: Double = id.toDouble(),
    val numerat: Boolean = false,
    val tipus: String = id.toString(),
    val entrades: MutableList<Entrada> = mutableListOf(),
    val especific1: String  = id.toString(),
    val especific2: String = id.toString(),
    val especific3: String = id.toString(),
    val especific4: MutableList<String> = mutableListOf(),
):Serializable