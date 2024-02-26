package asb.m07im08.espai_cultural_alexs

import java.io.Serializable
import java.time.LocalDateTime

class Esdeveniment (
    val id: Int,
    val nom: String,
    val image: String,
    val descripcio: String,
    val data: LocalDateTime,
    val preu: Float):Serializable