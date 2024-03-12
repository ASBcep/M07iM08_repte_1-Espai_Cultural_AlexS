package asb.m07im08.espai_cultural_alexs

import java.io.Serializable
import java.time.LocalDateTime

class Esdeveniment (
    val id: Int,
    val nom: String,
    val imatge: String,
    val descripcio: String,
    val data: LocalDateTime,
    val preu: Double,
    val numerat: Boolean,
    val tipus: String,
    val entrades: MutableList<Entrada>,
    val especific1: String,
    val especific2: String,
    val especific3: String,
    val especific4: MutableList<String>,
):Serializable