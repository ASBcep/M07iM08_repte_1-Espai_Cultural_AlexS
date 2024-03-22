package asb.m07im08.espai_cultural_alexs

object GestorEntrades {
    //retorna quantes entrades té algú
    fun entradesPerPersona(): Int {
        var numero = -1
        TODO("funció per trobar quantes entrades té una persona")
        return numero
    }
    // retorna el número d'entrades que no s'han reservat
    fun entradesDisponiblesNumero(esdeveniment: Esdeveniment): Int {
        var entradesReservades = esdeveniment.entrades.count()
        var entradesDisponibles = Esdeveniment_Manager.aforament - entradesReservades

        return entradesDisponibles
    }
    //retorna el llistat d'entrades disponibles
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
    //retorna el número d'entrades reservades
    fun entradesReservadesNumero(esdeveniment: Esdeveniment): Int {
        var entradesReservades = esdeveniment.entrades.count()
        return entradesReservades
    }
    //retorna el llistat d'entrades reservades
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
    fun trobarIdsDisponibles(entrades: MutableList<Entrada>, entradesAReservar: Int, aforament: Int): MutableList<Int> {
        val idsDisponibles = mutableListOf<Int>()
        var idsOcupades = trobarIdsOcupades(entrades,aforament)

        for (seient in 1..aforament) {//recorro cada seient de l'aforament
            if (!idsOcupades.contains(seient)) {//si el seient no està ocupat
                if (idsDisponibles.count() < entradesAReservar){//si encara no hem trobat el total de seients que volem assignar
                    idsDisponibles.add(seient)
                }
            }
        }
        return idsDisponibles
    }
    fun trobarIdsOcupades(entrades: MutableList<Entrada>, aforament: Int): MutableList<Int> {
        val idsOcupades = mutableListOf<Int>()
        for (entrada in entrades) {
            if (entrada.id in 1..aforament) {
                idsOcupades.add(entrada.id)
            }
        }
        return idsOcupades
    }
    fun assignarEntrada(esdevenimentAReservar: Esdeveniment, entrada: Entrada){
        esdevenimentAReservar.entrades.add(entrada)
        //no es desa al json!!
    }
    fun assignarEntrades(esdevenimentAReservar: Esdeveniment, entrades: MutableList<Entrada>){
        entrades.forEachIndexed{ index, value, ->
            assignarEntrada(esdevenimentAReservar,value)
        }
    }
}