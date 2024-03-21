package asb.m07im08.espai_cultural_alexs

object GestorEntrades {
    fun entradesPerPersona(): Int {
        var numero = -1
        TODO("funció per trobar quantes entrades té una persona")
        return numero
    }
    fun entradesDisponiblesNumero(esdeveniment: Esdeveniment): Int {
        var aforament = Esdeveniment_Manager.aforament
        var entradesReservades = esdeveniment.entrades.count()
        var entradesDisponibles = aforament - entradesReservades

        return entradesDisponibles
    }
    fun entradesDisponiblesLlistat(esdeveniment: Esdeveniment): MutableList<Int> {
        val entradesOcupades = entradesReservadesLlistat(esdeveniment)
        val entradesDisponibles = mutableListOf<Int>()
        for (i in 1..100){
           if (!entradesOcupades.contains(i)){
               entradesDisponibles.add(i)
           }
        }
        return entradesDisponibles
    }

    fun entradesReservadesNumero(esdeveniment: Esdeveniment): Int {
        var entradesReservades = esdeveniment.entrades.count()
        return entradesReservades
    }

    fun entradesReservadesLlistat(esdeveniment: Esdeveniment): MutableList<Int> {
        var llistatEntradesReservades = mutableListOf<Int>()
        for (entrada in esdeveniment.entrades)
        {
            if (entrada.id > 0 && entrada.id <= 100){
                llistatEntradesReservades.add(entrada.id)
            }
        }
        return llistatEntradesReservades
    }
}