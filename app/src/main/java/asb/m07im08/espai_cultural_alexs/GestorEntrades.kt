package asb.m07im08.espai_cultural_alexs

object GestorEntrades {
    fun entradesPerPersona(): Int {
        var numero = -1
        TODO("funció per trobar quantes entrades té una persona")
        return numero
    }
    fun entradesDisponibles(esdeveniment: Esdeveniment): Int {
        var aforament = Esdeveniment_Manager.aforament
        var entradesReservades = esdeveniment.entrades.count()
        var entradesDisponibles = aforament - entradesReservades

        return entradesDisponibles
    }

    fun entradesReservades(esdeveniment: Esdeveniment): Int {
        var entradesReservades = esdeveniment.entrades.count()

        return entradesReservades
    }
}